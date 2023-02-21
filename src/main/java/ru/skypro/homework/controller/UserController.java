package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.AvatarService;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    private final AvatarService avatarService;

    public UserController(UserService userService, AvatarService avatarService) {
        this.userService = userService;
        this.avatarService = avatarService;
    }

    @PatchMapping("/me")
    @Operation(
            summary = "updateUser",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "users"),
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "204", content = @Content()),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, Authentication authentication) {
        logger.info("Processing updateUser Controller");
        UserDto updatedUser = userService.editUser(userDto, authentication.getName());
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        logger.info("Processing getUser Controller");
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @PostMapping("/set_password")
    @Operation(
            summary = "setPassword",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "201", content = @Content())
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> setPassword(@RequestBody PasswordDto password, Authentication authentication) {
        logger.info("Processing setPassword Controller");
        UserDto user = userService.changePassword(password, authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "updateUserImage",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content),
                    @ApiResponse(responseCode = "401", content = @Content),
                    @ApiResponse(responseCode = "403", content = @Content),
                    @ApiResponse(responseCode = "404", content = @Content)
            })
    public ResponseEntity<HttpStatus> updateUsersImage(@RequestParam MultipartFile image, Authentication authentication) {
        logger.info("method UserController - updateImage");
        avatarService.updateAvatar(image, authentication);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/avatar/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<byte[]> getAvatarUser(@PathVariable Long id) {
        logger.info("UserController - getAvatarUser");
        return ResponseEntity.ok(avatarService.getAvatar(id));
    }
}

