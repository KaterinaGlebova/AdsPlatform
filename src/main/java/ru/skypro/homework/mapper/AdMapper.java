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
import java.util.stream.Collectors;
/**
 * Интерфейс для маппинга объявлений.
 */
@Component
@Mapper(componentModel = "spring")
public interface AdMapper {

    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    default AdDTO adToAdDTO(Ad ad) {
        AdDTO result = new AdDTO();
        result.setAuthor(ad.getUser().getId());
        result.setImage("/images/ad/" + ad.getId());
        result.setPk(ad.getId());
        result.setPrice(ad.getPrice());
        result.setTitle(ad.getTitle());
        return result;
    }
    default ExtendedAd toExtendedAd(Ad ad) {
        ExtendedAd result = new ExtendedAd();
        result.setPk(ad.getId());
        result.setAuthorFirstName(ad.getUser().getFirstName());
        result.setAuthorLastName(ad.getUser().getLastName());
        result.setDescription(ad.getDescription());
        result.setEmail(ad.getUser().getEmail());
        result.setImage("/images/ad/" + ad.getId());
        result.setPhone(ad.getUser().getPhone());
        result.setPrice(ad.getPrice());
        result.setTitle(ad.getTitle());
        return result;
    }

   default Ads adToAds(List<Ad> adList) {
       Ads ads = new Ads();
       ads.setCount(adList.size());
       ads.setResults(adList.stream()
               .map(this::adToAdDTO)
               .collect(Collectors.toList()));
       return ads;
   }
}
