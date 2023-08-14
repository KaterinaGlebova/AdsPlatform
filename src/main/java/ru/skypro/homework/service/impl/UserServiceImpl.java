package ru.skypro.homework.service.impl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.ReadOrWriteException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final String IMAGE_DIRECTORY;
    private final ImageService imageService;


    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository,
                           PasswordEncoder encoder, @Value("${path.to.user.images}")String imageDirectory,
                           ImageService imageService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.encoder = encoder;
        IMAGE_DIRECTORY = imageDirectory;
        this.imageService = imageService;
    }

    /**
     * save users in bd
     *
     * @param user
     * @return object user
     */
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * find be email in db
     *
     * @param email
     * @return true if email exists
     */
    @Override
    public boolean emailCheck(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * change password
     *
     * @param newPassword
     * @param authentication
     * @throws ForbiddenException if passwords not equal
     */
    @Override
    public void changePassword(NewPassword newPassword, Authentication authentication) {
        User user = getFromAuthentication(authentication);
        if (!(encoder.matches(newPassword.getCurrentPassword(), user.getPassword()))) {
            throw new ForbiddenException(String.format("Текущий пароль неверный",
                    user.getEmail()));
        }
        user.setPassword(encoder.encode(newPassword.getNewPassword()));
        saveUser(user);
    }

    /**
     * find user by email
     *
     * @param email
     * @return null if email is not found
     */
    @Override
    public User getByEmail(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        return optional.orElse(null);
    }

    /**
     * user from authentication
     *
     * @param authentication
     * @return object user from database
     */
    @Override
    public User getFromAuthentication(Authentication authentication) {
        return getByEmail(authentication.getName());
    }

    /**
     * get info about user
     *
     * @param authentication
     * @return userDTO
     */
    @Override
    public UserDTO getUserInfo(Authentication authentication) {
        User user = getFromAuthentication(authentication);
        UserDTO result = userMapper.userToUserDTO(getFromAuthentication(authentication));
        if (user.getImage() == null) {
            result.setImage(null);
        } else result.setImage("/image/user/" + user.getEmail());
        return result;
    }

    /**
     * update user
     *
     * @param update
     * @param authentication
     */
    @Override
    public void updateInfo(UpdateUser update, Authentication authentication) {
        User user = getFromAuthentication(authentication);
        user.setFirstName(update.getFirstName());
        user.setLastName(update.getLastName());
        user.setPhone(user.getPhone());
        saveUser(user);
    }
    @Override
    public void setImage(MultipartFile image, Authentication authentication) {
        User user = getFromAuthentication(authentication);
        String oldName = user.getImage();
        String sourceName = image.getOriginalFilename();//"image.jpg";//
        String fileName = user.getId() + sourceName.substring(sourceName.lastIndexOf("."));
        Path path = Path.of(IMAGE_DIRECTORY).resolve(fileName);
        try {
            if (oldName != null) {
                Files.deleteIfExists(Path.of(oldName));
            }
            imageService.saveFile(path, image.getBytes());
        } catch (IOException e) {
            throw new ReadOrWriteException("Не удалось сохранить изображение", e);
        }
        user.setImage(path.toString());
        saveUser(user);
    }

@Override
    public byte[] getImage(String email) {
        String filePath = getByEmail(email).getImage();
        try {
            return imageService.readFile(Path.of(filePath));
        } catch (IOException e) {
            throw new ReadOrWriteException("Не удалось получить изображение", e);
        }
    }

@Override
    public MediaType getImageType(String email) {
        String filePath = getByEmail(email).getImage();
        String subtype = filePath.substring(filePath.lastIndexOf(".")).replace(".", "");
        return new MediaType("image", subtype);
    }
}
