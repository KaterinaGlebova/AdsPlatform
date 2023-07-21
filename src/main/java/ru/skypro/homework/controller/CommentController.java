package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/comments")
public class CommentController {

    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable int id) {
        return ResponseEntity.ok(new Comments());
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> sendComment(@PathVariable int id, @RequestBody Comment comment) {
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment (@PathVariable int adId, @PathVariable int commentId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable int adId, @PathVariable int commentId, @RequestBody Comment comment) {
        return ResponseEntity.ok(comment);
    }
}

