package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

/**
 * Контроллер по обработке запросов с комментариями
 */
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {

    private final CommentService commentService;

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
    public ResponseEntity<CommentDTO> sendComment(@PathVariable(name = "id") int adId,
                                                  @RequestBody CreateOrUpdateComment createOrUpdateComment,
                                                  Authentication authentication) {
        CommentDTO result = commentService.createComment(adId, createOrUpdateComment, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
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
    public ResponseEntity<Void> deleteComment(@PathVariable int adId, @PathVariable int commentId, Authentication authentication) {
        commentService.deleteComment(adId, commentId, authentication);
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
    public ResponseEntity<CommentDTO> editComment(@PathVariable int adId, @PathVariable int commentId,
                                                  @RequestBody CreateOrUpdateComment createOrUpdateComment,
                                                  Authentication authentication) {
        CommentDTO result = commentService.update(adId, commentId, createOrUpdateComment, authentication);
        return ResponseEntity.ok(result);
    }
}