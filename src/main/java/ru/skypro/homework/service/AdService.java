package ru.skypro.homework.service;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;

public interface AdService {

    Ads getAllAds();

    Ad adAd(Ad ad);

    AdDTO createAd(CreateOrUpdateAd createOrUpdateAd, Authentication authentication, MultipartFile image);

    ExtendedAd getExtendedAd(int id);

    Ad getById(int id);

    void deleteAd(int id, Authentication authentication);

    AdDTO updateAd(int id, CreateOrUpdateAd createOrUpdateAd);

    Ads getAllByAuthor(Authentication authentication);

    void updateImage(int id, MultipartFile image, Authentication authentication);

    byte[] getImage(int id);

    MediaType getImageType(int id);


}