package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.entity.Ad;

@Mapper
public interface AdMapper {

    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    @Mapping(source = "author", target = "user.id")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "pk", target = "id")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "title", target = "title")
    Ad adDtoToAd(AdDTO adDTO);

    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "id", target = "pk")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "title", target = "title")
    AdDTO adToAdDTO(Ad ad);

}
