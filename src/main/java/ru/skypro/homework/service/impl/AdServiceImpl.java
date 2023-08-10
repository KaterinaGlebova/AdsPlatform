package ru.skypro.homework.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.util.List;


@Service
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final  UserService userService;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper, UserService userService) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
        this.userService = userService;
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
    public AdDTO createAd(CreateOrUpdateAd createOrUpdateAd, Authentication authentication, String image) {
        Ad ad = new Ad();
        ad.setUser(userService.getFromAuthentication(authentication));
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
    public void deleteAd (int id, Authentication authentication) {
        Ad ad = getById(id);
        authorCheck(ad, authentication);
        adRepository.delete(ad);
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
    /**
     * get all ads current user
     *
     * @param authentication
     * @return object DTO Ads
     */
    public Ads getAllByAuthor(Authentication authentication) {
        User author = userService.getFromAuthentication(authentication);
        List<Ad> ads = adRepository.findAllByAuthor(author);
        return adMapper.adToAds(ads);
    }


    private void authorCheck(Ad ad, Authentication authentication) {
        User user = userService.getFromAuthentication(authentication);
        if (user.getRole() != Role.ADMIN && !user.equals(ad.getUser())) {
            throw new ForbiddenException(String.format("%s can't edit or delete this Ad", user.getEmail()));
        }
    }

    // public CreateOrUpdateAd getAdMe(){}

    // public ResponseEntity<String> updateAdImage(int id, String image ){}
}
