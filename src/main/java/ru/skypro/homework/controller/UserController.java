package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dto.User.PasswordDto;
import ru.skypro.homework.dto.User.UserDto;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Collection;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        UserDto updatedUser = userService.editUser(user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/me")
    public ResponseEntity<Collection<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/set_password")
    public ResponseEntity<UserDto> setPassword(@RequestBody PasswordDto password) {
        UserDto user = userService.changePassword(password);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("me/image")
    public ResponseEntity<String> updateUserImage(@RequestPart MultipartFile image) {
        String filePath = "";
        return ResponseEntity.ok(String.format("{\"data\":{ \"image\": \"%s\"}}", filePath));
    }
}
