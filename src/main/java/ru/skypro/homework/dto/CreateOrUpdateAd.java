package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateOrUpdateAd {

    private String description;
    private int prise;
    private String title;
}