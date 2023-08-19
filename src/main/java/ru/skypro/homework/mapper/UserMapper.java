package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
/**
 * Интерфейс для маппинга пользователей.
 */
@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "role", target = "role")
    UserDTO userToUserDTO(User user);

    User userDtoToUser(UserDTO user);

    @Mapping(source = "register.username", target = "email")
    User fromRegister(Register register);
}
