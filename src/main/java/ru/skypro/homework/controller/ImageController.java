package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.skypro.homework.service.ImageService;


@RequiredArgsConstructor
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

      @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImageAds(@PathVariable Long id) {
          return ResponseEntity.ok(imageService.getImage(id));
          }

    }


