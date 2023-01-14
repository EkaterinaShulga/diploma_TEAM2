package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Password;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Collection;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

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

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto user = userService.findUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<Collection<UserDto>> getUsers(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/set_password")
    public ResponseEntity<UserDto> setPassword(@RequestBody Password password) {
        UserDto user = userService.changePassword(password);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
