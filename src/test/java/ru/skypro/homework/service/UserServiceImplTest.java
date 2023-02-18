package ru.skypro.homework.service;

/*

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.mapper.UserMapperImpl;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
*/

  /*  @Mock
    private UserRepository userRepository;

   // @Spy
   // private UserMapper userMapper = new UserMapperImpl();

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
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(defaultUser));
        UserDto result = userService.findUser(defaultUser.getId());

        assertNotNull(result);
    }

    @Test
    void editUserTest() {
        when(userRepository.save(defaultUser)).thenReturn(defaultUser);
    }
}
*/