package ru.skypro.homework.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.entity.Comment;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Mapper
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
}
