package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdsNotFoundException;
import ru.skypro.homework.mapper.AdsDtoMapper;
import ru.skypro.homework.mapper.CreateAdsDtoMapper;
import ru.skypro.homework.mapper.FullAdsDtoMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {
    private final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);
    @Value("${uploaded.max_file_size}")
    private static int SIZE_LIMIT;
    private final FileService fileService;
    private final AdsRepository adsRepository;
    private final UserServiceImpl usersService;
    private final AdsDtoMapper adsDtoMapper;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final FullAdsDtoMapper fullAdsDtoMapper;
    private final ImageService imageService;


    /**
     * Creating of new ad
     *
     * @param userLogin    - username
     * @param createAdsDto - created ad
     * @param image        - image path
     * @return ad created
     */
    @Override
    public AdsDto createAds(String userLogin, CreateAdsDto createAdsDto, MultipartFile image) {
        logger.info("Processing AdsServiceImpl:createAds()");
        User user = usersService.getUserByLogin(userLogin);
        int exist = adsRepository.countByTitleAndUserId(createAdsDto.getTitle(), user.getId());

        if (exist > 0) {
            return null;
        }
        Ads entityAds = createAdsDtoMapper.toModel(createAdsDto, user);
        Ads adsSaved = adsRepository.save(entityAds);
        Image imageForAds = imageService.createImage(image, adsSaved);

        List<Image> allImage = new ArrayList<>();
        allImage.add(imageForAds);
        entityAds.setImage(allImage);

        return adsDtoMapper.toDto(entityAds);
    }

    /**
     * Receive ad by id
     * The repository method is being used {@link AdsRepository#findById(Object)}
     *
     * @param id - ad id
     * @return ad entity
     * @throws AdsNotFoundException if no ad was found
     */
    @Override
    public Ads getAdsByPk(long id) {
        logger.info("Processing AdsServiceImpl:getAdsByPk()");
        return adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
    }

    /**
     * Receive all ads for user
     *
     * @param userLogin - username
     * @return ad list
     */
    @Override
    public ResponseWrapperAdsDto getMyAds(String userLogin) {
        logger.info("Processing AdsServiceImpl:getMyAds()");
        List<Ads> myAds = adsRepository.findByUserEmail(userLogin);
        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();
        wrapperAds.setCount(myAds.size());
        wrapperAds.setResults(
                adsDtoMapper.toAdsDtoList(myAds));

        return wrapperAds;
    }

    /**
     * Receive all Ads
     * The Service method is being used{@link AdsService#getAllAds()}
     * wherein the repository method is used to get all declarations{@link AdsRepository#findAll()}
     *
     * @return ad list
     */
    @Override
    public ResponseWrapperAdsDto getAllAds() {
        logger.info("Processing AdsServiceImpl:getAllAds()");
        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();
        List<Ads> adsList = adsRepository.findAll();
        wrapperAds.setResults(
                adsDtoMapper.toAdsDtoList(adsList));
        wrapperAds.setCount(adsList.size());
        return wrapperAds;
    }

    /**
     * Receive old ad by id, update and save
     *
     * @param userLogin     - username which should be updated
     * @param adsId - Ad id
     * @param updatedAdsDto - new ad
     * @return ad update
     */
    @Override
    public AdsDto updateAds(String userLogin, long adsId, CreateAdsDto updatedAdsDto, Authentication authentication) {
        logger.info("Processing AdsServiceImpl:updateAds()");
        User user = usersService.getUserByLogin(userLogin);
        usersService.checkUserPermission(authentication, user.getUsername());
        Optional<Ads> optionalAds = adsRepository.findByIdAndUserId(adsId, user.getId());

        optionalAds.ifPresent(adsEntity -> {
            adsEntity.setDescription(updatedAdsDto.getDescription());
            adsEntity.setTitle(updatedAdsDto.getTitle());
            adsEntity.setPrice(updatedAdsDto.getPrice());

            adsRepository.save(adsEntity);
        });

        return optionalAds
                .map(adsDtoMapper::toDto)
                .orElse(null);
    }

    /**
     * Delete ad from DB by id
     * The repository method is being used {@link AdsRepository#delete(Object)}
     *
     * @param adsId - ad id
     */
    @Override
    public void removeAds(long adsId, Authentication authentication) {
        logger.info("Processing AdsServiceImpl:removeAds()");
        Ads ads = adsRepository.findAdsById(adsId);
        usersService.checkUserPermission(authentication, ads.getUser().getUsername());
        adsRepository.delete(ads);
    }

    /**
     * Search for a full ad in DB by ID
     *
     * @param adsId - ad id
     * @return present ad
     */
    @Override
    public FullAdsDto getAds(long adsId) {
        logger.info("Processing AdsServiceImpl:getAds()");

        Optional<Ads> optionalAds = adsRepository.findById(adsId);

        return optionalAds
                .map(fullAdsDtoMapper::toDto)
                .orElse(null);
    }

    /**
     * Search for add in DB by part of title
     *
     * @param title - part of title
     * @return present ad
     */
    @Override
    public List<Ads> getAdsLike(String title) {
        return adsRepository.searchByTitle(title);
    }

}