package ru.skypro.homework.dto;

import lombok.Data;
/**
 * DTO класс с полной информацией по объявлению
 */

@Data
public class ExtendedAd {

    private int pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private int price;
    private String title;
}
