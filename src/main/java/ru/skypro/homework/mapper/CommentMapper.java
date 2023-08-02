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

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "author", target = "user.id")
    @Mapping(source = "authorImage", target = "user.image")
    @Mapping(source = "authorFirstName", target = "user.firstName")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "toLocalDateTime")
    @Mapping(source = "pk", target = "id")
    Comment commentDtoToComment(CommentDTO commentDTO);

    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "user.image", target = "authorImage")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "toMillis")
    @Mapping(source = "id", target = "pk")
    CommentDTO commentToCommentDTO(Comment comment);

    @Named("toLocalDateTime")
    default LocalDateTime toLocalDateTime(long value) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
    }

    @Named("toMillis")
    default long toMillis(LocalDateTime value) {
        return value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    default Comments toComments(List<Comment> comments) {
        Comments comments1 = new Comments();
        comments1.setCount(comments.size());
        comments1.setResult(comments.stream()
                .map(this::commentToCommentDTO)
                .collect(Collectors.toList()));
        return comments1;
    }
}
