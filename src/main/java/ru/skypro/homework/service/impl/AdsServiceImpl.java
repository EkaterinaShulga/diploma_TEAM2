package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.controller.AdsController;
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
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;


import javax.transaction.Transactional;
import java.io.IOException;
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

    @Override
    public AdsDto createAds(String userLogin, CreateAdsDto createAdsDto, MultipartFile image) throws IOException {//изменила
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


    @Override
    public Ads getAdsByPk(long id) {
        logger.info("Processing AdsServiceImpl:getAdsByPk()");
        return adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
    }

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


    @Override
    public void removeAds(long adsId) {
        logger.info("Processing AdsServiceImpl:removeAds()");
        Ads ads = adsRepository.findAdsByPk(adsId);
        adsRepository.delete(ads);


    }

    @Override
    public FullAdsDto getAds(long adsId) {
        logger.info("Processing AdsServiceImpl:getAds()");

        Optional<Ads> optionalAds = adsRepository.findById(adsId);

        return optionalAds
                .map(fullAdsDtoMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<Ads> getAdsLike(String title) {
        return adsRepository.searchByTitle(title);
    }

    /*public boolean updateAdsImagePath(Long adsId, String userLogin, String filePath) {
        logger.info("Processing AdsServiceImpl:updateAdsImagePath()");
        Optional<Ads> optionalAds = adsRepository.findByPkAndUserEmail(adsId, userLogin);

        Ads adsEntity = optionalAds.orElseThrow(EntityNotFoundException::new);
        byte[]oldImage = adsEntity.getImage();//изменила

        try {
           // if (!oldImage.isEmpty()) {
            if(oldImage == null)
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
    }*/


}