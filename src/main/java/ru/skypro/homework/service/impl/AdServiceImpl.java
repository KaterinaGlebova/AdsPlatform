package ru.skypro.homework.service.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.NotFoundElementException;
import ru.skypro.homework.exception.ReadOrWriteException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


@Service
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final  UserService userService;
    private final String IMAGE_DIRECTORY;
    private final ImageService imageService;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper, UserService userService,
                         @Value("${path.to.ad.images}") String imageDirectory, ImageService imageService) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
        this.userService = userService;
        IMAGE_DIRECTORY = imageDirectory;
        this.imageService = imageService;
    }

    /**
     * read all ads from database
     * {@link JpaRepository#findAll()}
     *
     * @return Ads
     */
    @Override
    public Ads getAllAds() {
        return adMapper.adToAds(adRepository.findAll());
    }

    /**
     * save ad in database
     * {@link JpaRepository#save(Object)} ()}
     *
     * @return object ad
     */
    @Override
    public Ad adAd(Ad ad) {
        return adRepository.save(ad);
    }

    /**
     * Create new ad
     *
     * @param newAd
     * @param image
     * @return object AdDTO
     */
    @Override
    public AdDTO createAd(CreateOrUpdateAd newAd, MultipartFile image, Authentication authentication) {
        Ad ad = new Ad();
        ad.setUser(userService.getFromAuthentication(authentication));
        ad.setPrice(newAd.getPrice());
        ad.setTitle(newAd.getTitle());
        ad.setDescription(newAd.getDescription());
        adAd(ad);
        setImage(image, ad);
        return adMapper.adToAdDTO(adAd(ad));
    }

    /**
     * Get full information from ad
     *
     * @param id
     * @return object DTO extendedAd
     */
    @Override
    public ExtendedAd getExtendedAd(int id) {
        Ad ad = getById(id);
        return adMapper.toExtendedAd(getById(id));
    }

    /**
     * get ad from database by id
     *
     * @param id
     * @return object ad
     */
    @Override
    public Ad getById(int id) {
        Optional<Ad> optional = adRepository.findAdById(id);
        if (optional.isEmpty()) {
            throw new NotFoundElementException(id, Ad.class);
        }
        return optional.get();
    }

    /**
     * Delete ad by id
     *
     * @param id
     */
    @Override
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
    @Override
    public AdDTO updateAd(int id, CreateOrUpdateAd createOrUpdateAd,Authentication authentication ) {
        Ad ad = getById(id);
        authorCheck(ad, authentication);
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
    @Override
    public Ads getAllByAuthor(Authentication authentication) {
        User author = userService.getFromAuthentication(authentication);
        List<Ad> ads = adRepository.findAllByUser(author);
        return adMapper.adToAds(ads);
    }

    private void authorCheck(Ad ad, Authentication authentication) {
        User user = userService.getFromAuthentication(authentication);
        if (user.getRole() != Role.ADMIN && !user.equals(ad.getUser())) {
            throw new ForbiddenException(String.format("вы не можете редактировать или удалить данное объявление",
                    user.getEmail()));
        }
    }
    @Override
    public void updateImage(int id, MultipartFile image, Authentication authentication) {
        Ad ad = getById(id);
        authorCheck(ad, authentication);
        setImage(image, ad);
    }

    private void setImage(MultipartFile image, Ad ad) {
        String oldName = ad.getImage();
        String sourceName = image.getOriginalFilename();
        String fileName = ad.getId() + sourceName.substring(sourceName.lastIndexOf("."));
        Path path = Path.of(IMAGE_DIRECTORY).resolve(fileName);
        try {
            if (oldName != null) {
                Files.deleteIfExists(Path.of(oldName));
            }
            imageService.saveFile(path, image.getBytes());
        } catch (IOException e) {
            throw new ReadOrWriteException("Не удалось сохранить изображение", e);
        }
        ad.setImage(path.toString());
        adAd(ad);
    }

    @Override
    public byte[] getImage(int id) {
        String filePath = getById(id).getImage();
        try {
            return imageService.readFile(Path.of(filePath));
        } catch (IOException e) {
            throw new ReadOrWriteException("Изображение не получено", e);
        }
    }

    @Override
    public MediaType getImageType(int id) {
        String filePath = getById(id).getImage();
        String subtype = filePath.substring(filePath.lastIndexOf(".")).replace(".", "");
        return new MediaType("image", subtype);
    }
}
