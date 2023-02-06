package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdsNotFoundException;
import ru.skypro.homework.mapper.AdsDtoMapper;
import ru.skypro.homework.mapper.CreateAdsDtoMapper;
import ru.skypro.homework.mapper.FullAdsDtoMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {
    private final Logger logger = LoggerFactory.getLogger(AdsController.class);

    private final FileService fileService;
    private final AdsRepository adsRepository;
    private final UserServiceImpl usersService;
    private final CommentServiceImpl commentService;
    private final AdsDtoMapper adsDtoMapper;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final FullAdsDtoMapper fullAdsDtoMapper;

    /**
     * Creating of new ad
     *
     * @param userLogin - username
     * @param createAdsDto - created ad
     * @param image - image path
     * @return ad created
     */
    @Override
    public AdsDto createAds(String userLogin, CreateAdsDto createAdsDto, String image) {
        logger.info("Processing AdsServiceImpl:createAds()");
        User user = usersService.getUserByLogin(userLogin);
        int exist = adsRepository.countByTitleAndUserId(createAdsDto.getTitle(), user.getId());
        if (exist > 0) {
            return null;
        }

        Ads adsEntity = createAdsDtoMapper.toModel(createAdsDto, user, image);
        return adsDtoMapper.toDto(adsRepository.save(adsEntity));
    }

    /**
     * Receive ad by id
     * The repository method is being used {@link AdsRepository#findById(Object)}
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

        if (!myAds.isEmpty()) {
            wrapperAds.setCount(myAds.size());
            wrapperAds.setResults(
                    adsDtoMapper.toAdsDtoList(myAds));
        } else {
            wrapperAds.setCount(0);
            wrapperAds.setResults(Collections.emptyList());
        }

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

        if (!adsList.isEmpty()) {
            wrapperAds.setResults(
                    adsDtoMapper.toAdsDtoList(adsList));
            wrapperAds.setCount(adsList.size());
        } else {
            wrapperAds.setCount(0);
            wrapperAds.setResults(Collections.emptyList());
        }

        return wrapperAds;
    }
    /**
     * Receive old ad by id, update and save
     *
     * @param userLogin - username which should be updated
     * @param adsId
     * @param updatedAdsDto - new ad
     * @return ad update
     */
    @Override
    public AdsDto updateAds(String userLogin, long adsId, CreateAdsDto updatedAdsDto) {
        logger.info("Processing AdsServiceImpl:updateAds()");
        User user = usersService.getUserByLogin(userLogin);
        Optional<Ads> optionalAds = adsRepository.findByPkAndUserId(adsId, user.getId());

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
     * @param userLogin - user login
     * @return is present boolean method
     */
    @Override
    public boolean removeAds(long adsId, String userLogin) {
        logger.info("Processing AdsServiceImpl:removeAds()");
        Optional<Ads> optionalAds = adsRepository.findByPkAndUserEmail(adsId, userLogin);

        optionalAds.ifPresent((adsEntity -> {
            commentService.deleteComments(adsId, Math.toIntExact(adsEntity.getPk()));
            adsRepository.delete(adsEntity);
        }));

        return optionalAds.isPresent();
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
     * Search for a ad in DB by part of title
     *
     * @param title - part of title
     * @return present ad
     */
    @Override
    public List <Ads> getAdsLike(String title) {
        return adsRepository.searchByTitle(title);
    }

    /**
     * Receive old image by id, update or save
     *
     * @param adsId - ad id
     * @param userLogin - user login
     * @param filePath - file path
     * @return image updated
     */
    public boolean updateAdsImagePath(Long adsId, String userLogin, String filePath) {
        logger.info("Processing AdsServiceImpl:updateAdsImagePath()");
        Optional<Ads> optionalAds = adsRepository.findByPkAndUserEmail(adsId, userLogin);

        Ads adsEntity = optionalAds.orElseThrow(EntityNotFoundException::new);
        String oldImage = adsEntity.getImage();

        try {
            if (!oldImage.isEmpty()) {
                fileService.removeFileByPath(oldImage);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        adsEntity.setImage(filePath);

        try {
            adsRepository.save(adsEntity);
        } catch (AdsNotFoundException e) {
            logger.error("Error: " + e.getMessage());
        }
        return true;
    }
}
