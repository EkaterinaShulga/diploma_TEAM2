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
import ru.skypro.homework.dto.PasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserHasNoRightsException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.mapper.UserMapperImpl;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @InjectMocks
    private UserServiceImpl userService;

    private User defaultUser;
    private Authentication authentication;


    @BeforeEach
    void init() {
        defaultUser = new User();
        defaultUser.setId(1L);
        defaultUser.setUsername("defaultUser@gmail.com");

        authentication = new UsernamePasswordAuthenticationToken(defaultUser, true);
    }

    @Test
    void findUserTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(defaultUser));
        UserDto result = userService.findUser(defaultUser.getId());

        assertNotNull(result);
    }

    @Test
    void editUserTest() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(defaultUser));
        when(userRepository.save(defaultUser)).thenReturn(defaultUser);

        UserDto result = userService.editUser(userMapper.toDto(defaultUser), defaultUser.getUsername());
        assertNotNull(result);

    }

    @Test
    void getUserByLoginTest() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(defaultUser));


        User result = userService.getUserByLogin(defaultUser.getUsername());
        assertNotNull(result);
    }

    @Test
    void getUserTest() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(defaultUser));

        UserDto result = userService.getUser(defaultUser.getUsername());
        assertNotNull(result);

    }

    @Test
    void changePasswordTest() {
        PasswordDto passwordDto = new PasswordDto();
        passwordDto.setNewPassword("password");
        passwordDto.setCurrentPassword("currentPassword");
        when(userRepository.getUserByPassword(anyString())).thenReturn(defaultUser);

        UserDto result = userService.changePassword(passwordDto, defaultUser.getUsername());

        assertNotNull(result);
    }

    @Test
    void checkUserPermissionTest() {
        assertThatExceptionOfType(UserHasNoRightsException.class)
                .isThrownBy(() -> userService.checkUserPermission(authentication, defaultUser.getUsername()));
    }

    @Test
    void changePasswordWrongTest() {
        UserDto result = userService.changePassword(null, defaultUser.getUsername());

        assertNull(result);

    }
}
