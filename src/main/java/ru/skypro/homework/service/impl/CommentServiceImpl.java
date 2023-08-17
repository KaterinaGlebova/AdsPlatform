package ru.skypro.homework.service.impl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.NotFoundElementException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final AdService adService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;

    public CommentServiceImpl(AdService adService, CommentRepository commentRepository,
                              CommentMapper commentMapper, UserService userService) {
        this.adService = adService;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
    }

    /**
     * get all comments by adId
     *
     * @param adId
     * @return object DTO Comments
     */
    @Override
    public Comments getCommentByAdId(int adId) {
        Ad ad = adService.getById(adId);
        return commentMapper.toComments(commentRepository.findAllByAd(ad));
    }

    /**
     * get comment by id
     *
     * @param commentId
     * @return object comment
     */
    @Override
    public Comment getCommentById(int commentId) {
        Optional<Comment> optional = commentRepository.findById(commentId);
        if (optional.isEmpty()) {
            throw new NotFoundElementException(commentId, Comment.class);
        }
        return optional.get();
    }

    /**
     * create new comment and save in database
     *
     * @param adId
     * @param createOrUpdateComment
     * @return object commentDTO
     */
    @Override
    public CommentDTO createComment(int adId, CreateOrUpdateComment createOrUpdateComment, Authentication authentication) {
        Ad ad = adService.getById(adId);
        User author = userService.getFromAuthentication(authentication);
        Comment comment = new Comment();
        comment.setText(createOrUpdateComment.getText());
        comment.setCreatedAt(new Date());
        comment.setAd(ad);
        comment.setUser(author);
        return commentMapper.commentToCommentDTO(commentRepository.save(comment));
    }


    /**
     * Delete comment by id
     *
     * @param adId
     * @param commentId
     */
    @Override
    public void deleteComment(int adId, int commentId, Authentication authentication) {
        Comment comment = getCommentById(commentId);
        adService.getById(adId);
        checkAuthor(comment, authentication);
        commentRepository.delete(comment);
    }

    /**
     * update comment by id
     *
     * @param adId
     * @param commentId
     * @param createOrUpdateComment
     * @return object commentDTO
     */
    @Override
    public CommentDTO update(int adId, int commentId, CreateOrUpdateComment createOrUpdateComment,
                             Authentication authentication) {
        Comment comment = getCommentById(commentId);
        adService.getById(adId);
        checkAuthor(comment, authentication);
        comment.setText(createOrUpdateComment.getText());
        comment.setCreatedAt(new Date());
        return commentMapper.commentToCommentDTO(commentRepository.save(comment));
    }

    private void checkAuthor(Comment comment, Authentication authentication) {
        User user = userService.getFromAuthentication(authentication);
        if (user.getRole() != Role.ADMIN && !user.equals(comment.getUser())) {
            throw new ForbiddenException(String.format("%s Вы не можете редактировать или удалить данный комментарий",
                    user.getEmail()));
        }
    }
}
