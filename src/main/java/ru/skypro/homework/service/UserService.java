package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
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

}
