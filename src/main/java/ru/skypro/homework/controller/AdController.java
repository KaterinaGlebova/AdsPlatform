package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")

public class AdController {

    private AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @Operation(summary = "Получение всех объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdDTO.class)
                                    )
                            )
                    )
            })
    @GetMapping()
    public ResponseEntity<Ads> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @Operation(summary = "Добавление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CreateOrUpdateAd.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            })
    @PostMapping()
    public ResponseEntity<AdDTO> createAd(@PathVariable CreateOrUpdateAd createOrUpdateAd, String image){
        return ResponseEntity.ok(adService.createAd(createOrUpdateAd, image));
    }

    @Operation(summary = "Получение информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExtendedAd.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "NotFound"
                    )
            })
    @GetMapping("/{id}")
    public ResponseEntity <ExtendedAd> getExtendedAd(@PathVariable int id) {
        ExtendedAd extendedAd = adService.getExtendedAd(id);
        return ResponseEntity.ok(extendedAd);
    }

    @Operation(summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No content"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NewPassword.class)
                            )
                    )
            })
    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteAd(@PathVariable int id) {
        adService.deleteAd(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = NewPassword.class)
                            )
                    )
            })
    @PatchMapping("/{id}")
    public ResponseEntity <AdDTO> updateAd(@PathVariable int id, @RequestBody CreateOrUpdateAd createOrUpdateAd) {
        return ResponseEntity.ok(adService.updateAd(id, createOrUpdateAd));
    }

    @Operation(summary = "Получение объявлений авторизованного пользователя")
    @GetMapping("/me")
    public ResponseEntity <CreateOrUpdateAd> getAdMe() {
        return ResponseEntity.ok (new CreateOrUpdateAd());
    }

    @Operation(summary = "Обновление картинки объявления")
    @PatchMapping("/{id}/image")
    public ResponseEntity <String> updateAdImage(@PathVariable int id, @RequestBody String image ){
        return ResponseEntity.ok(image);
    }
}
