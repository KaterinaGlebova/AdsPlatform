package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Получение всех комментариев объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comments.class)
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
    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable int id) {
        return ResponseEntity.ok(commentService.getCommentByAdId(id));
    }

    @Operation(summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CreateOrUpdateComment.class)
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
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable int adId,
                                                  @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok(commentService.createComment(adId, createOrUpdateComment));
    }

    @Operation(summary = "Удаление комментарий",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok"
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
                            description = "Forbidden"
                    )
            })
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int adId, @PathVariable int id) {
        commentService.deleteComment(adId, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ok",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CreateOrUpdateComment.class)
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
                            description = "Forbidden"
                    )
            })
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable int adId,
                                                    @PathVariable int id,
                                                    @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok(commentService.update(adId, id, createOrUpdateComment));
    }
}