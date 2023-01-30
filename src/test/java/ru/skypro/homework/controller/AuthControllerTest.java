package ru.skypro.homework.controller;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.service.AuthService;

@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private AuthService authService;

    @InjectMocks
    private AuthController authController;


    @Test
    void login() {
    }

    @Test
    void register() {
    }
}
