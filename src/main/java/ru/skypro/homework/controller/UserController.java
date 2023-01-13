package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.profile.CreateUserDto;
import ru.skypro.homework.exceptions.UserAlreadyCreatedException;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(AdsController.class);

    @PostMapping("/add")
    public CreateUserDto addUser(@RequestBody CreateUserDto updatedUserDto) {
        logger.info("Processing addUser Controller");
        try {
            CreateUserDto userDto = new CreateUserDto(); // Service createUser
            if (null == userDto) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return userDto;
        } catch (UserAlreadyCreatedException exception) {
            throw new ResponseStatusException(HttpStatus.CREATED);
        }
    }
}
