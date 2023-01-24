package ru.skypro.homework.service.impl.ads;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.dto.Ads.*;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.User.User;
import ru.skypro.homework.exceptions.AdsNotFoundException;
import ru.skypro.homework.mapping.ads.AdsDtoMapper;
import ru.skypro.homework.mapping.ads.CreateAdsDtoMapper;
import ru.skypro.homework.mapping.ads.FullAdsDtoMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.impl.UserServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {
    private Logger logger = LoggerFactory.getLogger(AdsController.class);

    private final FileService fileService;
    private final AdsRepository adsRepository;
    private final UserServiceImpl usersService;
    private final AdsDtoMapper adsDtoMapper;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final FullAdsDtoMapper fullAdsDtoMapper;

    @Override
    public AdsDto createAds(String userLogin, CreateAdsDto createAdsDto, String image) {
        User user = usersService.getUserByLogin(userLogin);
        int exist = adsRepository.countByTitleAndUserId(createAdsDto.getTitle(), user.getId());
        if (exist > 0) {
            return null;
        }

        AdsEntity adsEntity = createAdsDtoMapper.toModel(createAdsDto, user, image);
        return adsDtoMapper.toDto(adsRepository.save(adsEntity));
    }

    @Override
    public AdsEntity getAdsById(long id) {

        return adsRepository.findById(id)
                .orElseThrow(() -> new AdsNotFoundException("Объявление с id " + id + " не найдено!"));
    }

    @Override
    public ResponseWrapperAdsDto getMyAds(String userLogin) {
        List<AdsEntity> myAds = adsRepository.findByUserEmail(userLogin);
        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();

        if (!myAds.isEmpty()) {
            wrapperAds.setCount(myAds.size());
            wrapperAds.setResults(
                    adsDtoMapper.toAdsDtoList(myAds)
                            .toArray(new AdsDto[0]));
        } else {
            wrapperAds.setCount(0);
            wrapperAds.setResults(new AdsDto[0]);
        }

        return wrapperAds;
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();
        List<AdsEntity> adsList = adsRepository.findAll();

        if (!adsList.isEmpty()) {
            wrapperAds.setResults(
                    adsDtoMapper.toAdsDtoList(adsList)
                            .toArray(new AdsDto[0]));
            wrapperAds.setCount(adsList.size());
        } else {
            wrapperAds.setCount(0);
            wrapperAds.setResults(new AdsDto[0]);
        }

        return wrapperAds;
    }
    @Override
    public AdsDto updateAds(String userLogin, long adsId, CreateAdsDto updatedAdsDto) {
        User user = usersService.getUserByLogin(userLogin);
        Optional<AdsEntity> optionalAds = adsRepository.findByIdAndUserId(adsId, user.getId());

        optionalAds.ifPresent(entity -> {
            entity.setDescription(updatedAdsDto.getDescription());
            entity.setTitle(updatedAdsDto.getTitle());
            entity.setPrice(updatedAdsDto.getPrice());

            adsRepository.save(entity);});

        return optionalAds
                .map(adsDtoMapper::toDto)
                .orElse(null);
    }

    @Override
    public boolean removeAds(long adsId, String userLogin) {
        Optional<AdsEntity> optionalAds = adsRepository.findByIdAndUserEmail(adsId, userLogin);

        optionalAds.ifPresent((adsEntity -> {
            // Удаляем комментарий при удалении объявления
            // adsCommentService.deleteAdsCommentByDeletedAds(adsEntity.getId());
            adsRepository.delete(adsEntity);
        }));

        return optionalAds.isPresent();
    }

    @Override
    public FullAdsDto getAds(
            long adsId
    ) {
        Optional<AdsEntity> optionalAds = adsRepository.findById(
                adsId
        );

        return optionalAds
                .map(fullAdsDtoMapper::toDto)
                .orElse(null);
    }

    public boolean updateAdsImagePath(Long adsId, String userLogin, String filePath) {
        Optional<AdsEntity> optionalAds = adsRepository.findByIdAndUserEmail(adsId, userLogin);

        AdsEntity adsEntity = optionalAds.orElseThrow(EntityNotFoundException::new);
        String oldImage = adsEntity.getImage();

        try {
            if (!oldImage.isEmpty()) {
                fileService.removeFileByPath(oldImage);
            }
        } catch (IOException ignored) {}
        adsEntity.setImage(filePath);

        try {
            adsRepository.save(adsEntity);
        } catch (DataAccessException e) {
            return false;
        }

        return true;
    }
}
