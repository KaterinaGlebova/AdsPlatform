package ru.skypro.homework.service.impl;



import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.Role;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthServiceImpl(PasswordEncoder encoder,
                           UserService userService, UserMapper userMapper) {
        this.encoder = encoder;
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @Override
    public boolean login(String userName, String password) {

        User user = userService.getByEmail(userName);
        if (user == null) return false;
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public boolean register(Register register, Role role) {
//        if (manager.userExists(register.getUsername())) {
//            return false;
//        }
//        manager.createUser(
//                User.builder()
//                        .passwordEncoder(this.encoder::encode)
//                        .password(register.getPassword())
//                        .username(register.getUsername())
//        .roles(register.getRole().name())
//                        .build());
//        return true;
//    }
        if (userService.emailCheck(register.getUsername())) {
            return false;
        }
        User user = userMapper.fromRegister(register);
        user.setRole(role);
        user.setPassword(encoder.encode(user.getPassword()));
        userService.saveUser(user);
        return true;
    }
}
