package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdsDtoMapper;
import ru.skypro.homework.mapper.AdsDtoMapperImpl;
import ru.skypro.homework.mapper.CreateAdsDtoMapper;
import ru.skypro.homework.mapper.CreateAdsDtoMapperImpl;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdsServiceImplTest {

    @Mock
    private AdsRepository adsRepository;


    @Mock
    private ImageService imageService;

    @Mock
    private UserService userService;

    @Spy
    private CreateAdsDtoMapper createAdsDtoMapper = new CreateAdsDtoMapperImpl();

    @Spy
    private AdsDtoMapper adsDtoMapper = new AdsDtoMapperImpl();

    @InjectMocks
    private AdsServiceImpl adsService;

    private List<Ads> adsList;
    private User defaultUser;
    private Authentication authentication;
    private Image image;
    private Ads ads;
    private Ads ads2;
    private CreateAdsDto createAdsDto;

    @BeforeEach
    void init() {
        defaultUser = new User();
        defaultUser.setId(1L);
        defaultUser.setUsername("username@gmail.com");
        authentication = new UsernamePasswordAuthenticationToken(defaultUser, null);

        image = new Image();
        image.setId(2L);

        ads = new Ads();
        ads.setId(3L);
        ads.setPrice(16000);
        ads.setTitle("ads title");
        ads.setUser(defaultUser);

        ads2 = new Ads();
        ads2.setId(3L);
        ads2.setPrice(16000);
        ads2.setTitle("ads2 title");
        ads2.setUser(defaultUser);
        adsList = new ArrayList<>();

        adsList.add(ads);
        adsList.add(ads2);

        createAdsDto = new CreateAdsDto();
        createAdsDto.setDescription("description");
        createAdsDto.setTitle("Title");
        createAdsDto.setPrice(10000);
    }


    @Test
    void createAdsTest() {
        Ads adsMock = createAdsDtoMapper.toModel(createAdsDto);
        when(adsRepository.countByTitleAndUserId(anyString(), anyLong())).thenReturn(0);
        when(adsRepository.save(any())).thenReturn(adsMock);
        when(imageService.createImage(any(), any())).thenReturn(image);
        when(userService.getUserByLogin(anyString())).thenReturn(defaultUser);
        AdsDto result = adsService.createAds(defaultUser.getUsername(), createAdsDto, null);
        assertNotNull(result);
    }

    @Test
    void getAdsByPk() {
        when(adsRepository.findById(anyLong())).thenReturn(Optional.of(ads));
        Ads result = adsService.getAdsByPk(ads.getId());

        assertNotNull(result);
    }

    @Test
    void getMyAdsTest() {
        when(adsRepository.findByUserEmail(anyString())).thenReturn(adsList);
        ResponseWrapperAdsDto result = adsService.getMyAds("username@gmail.com");

        assertNotNull(result);

    }

    @Test
    void getAllAdsTest() {
        when(adsRepository.findAll()).thenReturn(adsList);
        ResponseWrapperAdsDto result = adsService.getAllAds();

        assertNotNull(result);
    }

    @Test
    void updateAdsTest() {
        Ads adsMock = createAdsDtoMapper.toModel(createAdsDto);
        when(adsRepository.getReferenceById(anyLong())).thenReturn(ads2);
        doNothing().when(userService).checkUserPermission(authentication, defaultUser.getUsername());
        when(adsRepository.save(any())).thenReturn(adsMock);

        AdsDto result = adsService.updateAds(defaultUser.getUsername(), ads2.getId(), createAdsDto, authentication);

        verify(adsRepository, atMostOnce()).save(ads2);
        verify(adsDtoMapper, atMostOnce()).toDto(ads2);

        assertNotNull(result);
        assertEquals(result.getPrice(),createAdsDto.getPrice());
    }

    @Test
    void removeAdsTest() {
        when(adsRepository.findAdsById(anyLong())).thenReturn(ads);
        doNothing().when(userService).checkUserPermission(authentication, ads.getUser().getUsername());
        adsService.removeAds(ads.getId(), authentication);

        verify(adsRepository, atMostOnce()).delete(ads);


    }

    @Test
    void getAdsTest() {
        when(adsRepository.findById(anyLong())).thenReturn(Optional.of(ads2));
        FullAdsDto result = adsService.getAds(ads2.getId());

        assertNotNull(result);
    }

    @Test
    void getAdsLikeTest() {
        when(adsRepository.searchByTitle(anyString())).thenReturn(adsList);
        List<Ads> result = adsService.getAdsLike(ads.getTitle());

        assertNotNull(result);
    }


}
