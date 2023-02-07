package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dto.PasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

import java.util.Collection;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, Authentication authentication) {
        logger.info("Processing updateUser Controller");
        UserDto updatedUser = userService.editUser(userDto, authentication.getName());
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/me")
    public ResponseEntity<Collection<UserDto>> getUsers() {
        logger.info("Processing getUsers Controller");
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/set_password")
    public ResponseEntity<UserDto> setPassword(@RequestBody PasswordDto password, Authentication authentication) {
        logger.info("Processing setPassword Controller");
        UserDto user = userService.changePassword(password, authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("me/image")
    public ResponseEntity<String> updateUserImage(@RequestPart MultipartFile image) {
        logger.info("Processing updateUserImage Controller");
        String filePath = "";
        return ResponseEntity.ok(String.format("{\"data\":{ \"image\": \"%s\"}}", filePath));
    }
}
