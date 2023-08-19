package ru.skypro.homework.dto;

import lombok.Data;
/**
 * DTO класс объявления
 */
@Data

public class AdDTO {

    private int author;
    private String image;
    private int pk;
    private int price;
    private String title;

}
