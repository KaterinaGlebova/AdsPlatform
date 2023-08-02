package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import java.util.ArrayList;
import java.util.List;
@Component
@Mapper(componentModel = "spring")
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

    default Ads adToAds(List<Ad> adList) {
        Ads ads = new Ads();
        ads.setCount(adList.size());

        List<AdDTO> adDTOList = new ArrayList<>();
        for (Ad ad : adList) {
            AdDTO adDTO = adToAdDTO(ad);
            adDTOList.add(adDTO);
        }
        ads.setResult(adDTOList);
        return ads;
    }

    @Mapping(source = "ad.id", target = "pk")
    @Mapping(source = "ad.user.firstName", target = "authorFirstName")
    @Mapping(source = "ad.user.lastName", target = "authorLastName")
    @Mapping(source = "ad.user.email", target = "email")
    @Mapping(source = "ad.user.phone", target = "phone")
    ExtendedAd toExtendedAd(Ad ad);

}
