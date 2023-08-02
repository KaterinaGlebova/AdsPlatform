package ru.skypro.homework.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;


@Service
public class AdServiceImpl implements AdService {
    private AdRepository adRepository;
    private AdMapper adMapper;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    /**
     * read all ads from database
     * {@link JpaRepository#findAll()}
     *
     * @return Ads
     */
    public Ads getAllAds() {
        return adMapper.adToAds(adRepository.findAll());
    }

    /**
     * save ad in database
     * {@link JpaRepository#save(Object)} ()}
     *
     * @return object ad
     */
    public Ad adAd(Ad ad) {
        return adRepository.save(ad);
    }

    /**
     * Create new ad
     *
     * @param createOrUpdateAd
     * @param image
     * @return object AdDTO
     */
    public AdDTO createAd(CreateOrUpdateAd createOrUpdateAd, String image) {
        Ad ad = new Ad();
        ad.setPrice(createOrUpdateAd.getPrice());
        ad.setTitle(createOrUpdateAd.getTitle());
        ad.setDescription(createOrUpdateAd.getDescription());
        ad.setImage(image);
        adAd(ad);
        return adMapper.adToAdDTO(ad);
    }

    /**
     * Get full information from ad
     *
     * @param id
     * @return object DTO extendedAd
     */
    public ExtendedAd getExtendedAd(int id) {
        Ad ad = getById(id);
        return adMapper.toExtendedAd(ad);
    }

    /**
     * get ad from database by id
     *
     * @param id
     * @return object ad
     */
    public Ad getById(int id) {
        return adRepository.findAdById(id);
    }

    /**
     * Delete ad by id
     *
     * @param id
     */
    public ResponseEntity<AdDTO> deleteAd(int id) {
        adRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Update ad by id and save in database
     *
     * @param id
     * @return object AdDTO
     */
    public AdDTO updateAd(int id, CreateOrUpdateAd createOrUpdateAd) {
        Ad ad = getById(id);
        ad.setPrice(createOrUpdateAd.getPrice());
        ad.setTitle(createOrUpdateAd.getTitle());
        ad.setDescription(createOrUpdateAd.getDescription());
        return adMapper.adToAdDTO(adRepository.save(ad));
    }

    // public CreateOrUpdateAd getAdMe(){}

    // public ResponseEntity<String> updateAdImage(int id, String image ){}
}
