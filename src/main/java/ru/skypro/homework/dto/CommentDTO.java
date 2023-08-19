package ru.skypro.homework.dto;

import lombok.Data;

/**
 * DTO класс комментария
 */
@Data
public class CommentDTO {

    private int author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    private int pk;
    private String text;
}
