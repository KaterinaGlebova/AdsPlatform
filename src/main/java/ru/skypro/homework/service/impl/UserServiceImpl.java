package ru.skypro.homework.service.impl;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import java.util.Optional;

@Service
public class UserServiceImpl {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, PasswordEncoder encoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    /**
     * save users in bd
     *
     * @param user
     * @return object user
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * find be email in db
     *
     * @param email
     * @return true if email exists
     */
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
    public User getFromAuthentication(Authentication authentication) {
        return getByEmail(authentication.getName());
    }

    /**
     * get info about user
     *
     * @param authentication
     * @return userDTO
     */
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
    public void updateInfo(UpdateUser update, Authentication authentication) {
        User user = getFromAuthentication(authentication);
        user.setFirstName(update.getFirstName());
        user.setLastName(update.getLastName());
        user.setPhone(user.getPhone());
        saveUser(user);
    }
}
