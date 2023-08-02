package ru.skypro.homework.service.impl;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {
    private AdService adService;
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;

    public CommentServiceImpl(AdService adService, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.adService = adService;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * get all comments by adId
     *
     * @param adId
     * @return object DTO Comments
     */
    public Comments getCommentByAdId(int adId){
        Ad ad = adService.getById(adId);
        return commentMapper.toComments(commentRepository.findAllByAd(ad));
    }

    /**
     * Пget comment by id
     *
     * @param id
     * @return object comment
     */
    public Comment getCommentById(int id){
        return commentRepository.getCommentById(id);
    }

    /**
     * create new comment and save in database
     *
     * @param adId
     * @param createOrUpdateComment
     * @return object commentDTO
     */
    public CommentDTO createComment(int adId, CreateOrUpdateComment createOrUpdateComment) {
        Ad ad = adService.getById(adId);
        User user = new User();
        Comment comment = new Comment();
        comment.setText(createOrUpdateComment.getText());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAd(ad);
        comment.setUser(user);
        return commentMapper.commentToCommentDTO(commentRepository.save(comment));
    }

    /**
     * Delete comment by id
     *
     * @param adId
     * @param id
     */
    public void deleteComment(int adId, int id) {
        Comment comment = getCommentById(id);
        adService.getById(adId);
        commentRepository.delete(comment);
    }
    /**
     * update comment by id
     *
     * @param adId
     * @param id
     * @param createOrUpdateComment
     * @return object commentDTO
     */
    public CommentDTO update(int adId, int id, CreateOrUpdateComment createOrUpdateComment) {
        Comment comment = getCommentById(id);
        adService.getById(adId);
        comment.setText(createOrUpdateComment.getText());
        comment.setCreatedAt(LocalDateTime.now());
        return commentMapper.commentToCommentDTO(commentRepository.save(comment));
    }
}
