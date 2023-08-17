package ru.skypro.homework.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.entity.Comment;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper {

    default CommentDTO commentToCommentDTO(Comment comment) {
        CommentDTO result = new CommentDTO();
        result.setAuthor(comment.getUser().getId());
        result.setAuthorImage("/images/user/" + comment.getUser().getEmail());
        result.setAuthorFirstName(comment.getUser().getFirstName());
        result.setCreatedAt(comment.getCreatedAt().getTime());
        result.setPk(comment.getId());
        result.setText(comment.getText());
        return result;
    }
    default Comments toComments(List<Comment> comments) {
        Comments result = new Comments();
        result.setCount(comments.size());
        result.setResults(comments.stream()
                .map(this::commentToCommentDTO)
                .collect(Collectors.toList()));
        return result;
    }
}
