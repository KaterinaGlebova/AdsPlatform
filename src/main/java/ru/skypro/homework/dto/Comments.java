package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;
/**
 * DTO класс списка комментариев
 */
@Data
public class Comments {
    private int count;
    private List<CommentDTO> results;
}
