package ru.skypro.homework.dto;

import lombok.Data;
/**
 * DTO класс пользователя
 */

@Data
public class UserDTO {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String image;
    private String role;
}
