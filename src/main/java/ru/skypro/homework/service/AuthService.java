package ru.skypro.homework.service;

import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
/**
 * Сервис для работы с авторизацией
 */
public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register, Role role);
}
