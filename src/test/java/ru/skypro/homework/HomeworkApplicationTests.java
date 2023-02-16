package ru.skypro.homework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.controller.AuthController;
import ru.skypro.homework.controller.ImageController;
import ru.skypro.homework.controller.UserController;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeworkApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private AdsController adsController;

    @Autowired
    private AuthController authController;

    @Autowired
    private ImageController imageController;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
        assertNotNull(adsController);
        assertNotNull(authController);
        assertNotNull(imageController);
        assertNotNull(userController);

    }

}
