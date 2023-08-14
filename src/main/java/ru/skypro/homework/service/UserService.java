package ru.skypro.homework.service;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

public interface UserService {
    User saveUser(User user);
    boolean emailCheck(String email);
    void changePassword(NewPassword newPassword, Authentication authentication);
    User getByEmail(String email);
    User getFromAuthentication(Authentication authentication);
    UserDTO getUserInfo(Authentication authentication);
    void updateInfo(UpdateUser update, Authentication authentication);
    void setImage(MultipartFile image, Authentication authentication);
    byte[] getImage(String email);
    MediaType getImageType(String email);

}
