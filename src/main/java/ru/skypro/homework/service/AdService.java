package ru.skypro.homework.service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;

public interface AdService {

    Ads getAllAds();

    Ad adAd(Ad ad);

    AdDTO createAd(CreateOrUpdateAd createOrUpdateAd, String image);

    ExtendedAd getExtendedAd(int id);

    Ad getById(int id);

    ResponseEntity<AdDTO> deleteAd(int id);

    AdDTO updateAd(int id, CreateOrUpdateAd createOrUpdateAd);
    //CreateOrUpdateAd getAdMe();
    //ResponseEntity<String> updateAdImage(int id, String image);
}