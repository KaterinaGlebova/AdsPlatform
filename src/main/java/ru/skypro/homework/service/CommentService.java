package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;

public interface CommentService {

    Comments getCommentByAdId(int adId);

    Comment getCommentById(int commentId);

    CommentDTO createComment(int adId, CreateOrUpdateComment createOrUpdateComment, Authentication authentication);

    void deleteComment(int adId, int id, Authentication authentication);

    CommentDTO update(int adId, int id, CreateOrUpdateComment createOrUpdateComment, Authentication authentication);
}
