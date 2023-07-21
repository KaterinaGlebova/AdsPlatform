package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")

public class UserController {

    @PostMapping("/set_password")
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.ok(new NewPassword());}

    @GetMapping("/me")
    public ResponseEntity <User> getUser() {return ResponseEntity.ok(new User());}

    @PatchMapping("/me")
    public ResponseEntity <User> updateUser(@RequestBody User user) {return ResponseEntity.ok(user);}

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateImage(@RequestBody String image) {return ResponseEntity.ok().build();}

}
