package ru.skypro.homework.service;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;

public interface AdService {

    Ads getAllAds();

    Ad adAd(Ad ad);

    AdDTO createAd(CreateOrUpdateAd createOrUpdateAd, Authentication authentication, String image);

    ExtendedAd getExtendedAd(int id);

    Ad getById(int id);

    void deleteAd(int id, Authentication authentication);

    AdDTO updateAd(int id, CreateOrUpdateAd createOrUpdateAd);
    Ads getAllByAuthor(Authentication authentication);
    //CreateOrUpdateAd getAdMe();
    //ResponseEntity<String> updateAdImage(int id, String image);
}