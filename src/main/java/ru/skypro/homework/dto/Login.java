package ru.skypro.homework.dto;

import lombok.Data;
/**
 * DTO класс для авторизации пользователя
 */

@Data
public class Login {

    private String username;
    private String password;
}
