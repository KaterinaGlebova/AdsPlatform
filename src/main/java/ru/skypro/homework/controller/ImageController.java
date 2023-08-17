package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

@Slf4j
@RestController

@RequestMapping("/images")
@CrossOrigin(value = "http://localhost:3000")

public class ImageController {
    private final UserService userService;
    private final AdService adService;

    public ImageController(UserService userService, AdService adService) {
        this.userService = userService;
        this.adService = adService;
    }

    @Operation(
            summary = "Получение аватара авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok"

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unauthorized"

                    )
            })

    @GetMapping(value = "/user/{email}")
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable String email) {
        return ResponseEntity
                .ok()
                .contentType(userService.getImageType(email))
                .body(userService.getImage(email));
    }

    @Operation(
            summary = "Получение картинки объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok"

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unauthorized"

                    )
            })
    @GetMapping(value = "/ads/{id}")
    public ResponseEntity<byte[]> getAdImage(@PathVariable int id) {
        return ResponseEntity
                .ok()
                .contentType(adService.getImageType(id))
                .body(adService.getImage(id));
    }
}
