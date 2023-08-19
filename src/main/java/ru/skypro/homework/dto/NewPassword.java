package ru.skypro.homework.dto;

import lombok.Data;
/**
 * DTO класс для изменения пароля пользователя
 */

@Data
public class NewPassword {

    private String currentPassword;
    private String newPassword;
}

