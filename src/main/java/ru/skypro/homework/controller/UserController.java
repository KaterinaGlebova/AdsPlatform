package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserDTO;

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
    public ResponseEntity <UserDTO> getUser() {return ResponseEntity.ok(new UserDTO());}

    @PatchMapping("/me")
    public ResponseEntity <UserDTO> updateUser(@RequestBody UserDTO userDTO) {return ResponseEntity.ok(userDTO);}

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateImage(@RequestBody String image) {return ResponseEntity.ok().build();}

}
