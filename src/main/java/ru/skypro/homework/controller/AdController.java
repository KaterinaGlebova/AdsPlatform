package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")

public class AdController {
    @GetMapping()
    public ResponseEntity<Ads> getAllAds() {
        return ResponseEntity.ok (new Ads());
    }

    @PostMapping()
    public ResponseEntity <CreateOrUpdateAd> addAd(CreateOrUpdateAd createOrUpdateAd) {
        return ResponseEntity.ok (new CreateOrUpdateAd());
    }

    @GetMapping("/{id}")
    public ResponseEntity <ExtendedAd> getExtendedAd(@PathVariable int id) {
        return ResponseEntity.ok (new ExtendedAd());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteAd(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity <CreateOrUpdateAd> updateAd(@PathVariable int id) {
        return ResponseEntity.ok (new CreateOrUpdateAd());
    }

    @GetMapping("/me")
    public ResponseEntity <CreateOrUpdateAd> getAdMe() {
        return ResponseEntity.ok (new CreateOrUpdateAd());
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity <String> updateAdImage(@PathVariable int id, @RequestBody String image ){
        return ResponseEntity.ok(image);
    }
}
