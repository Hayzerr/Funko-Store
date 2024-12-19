package com.bolashak.onlinestorebackend.controllers;

import com.bolashak.onlinestorebackend.entities.User;
import com.bolashak.onlinestorebackend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Эндпоинты для управления пользователями")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(
            summary = "Получить всех пользователей",
            description = "Возвращает список всех пользователей системы.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список пользователей успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    )
            }
    )
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает данные пользователя по указанному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно найден",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"User not found\"}"))
                    )
            }
    )
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить данные пользователя",
            description = "Обновляет данные пользователя по указанному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно обновлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"User not found\"}"))
                    )
            }
    )
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя из системы по указанному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Пользователь успешно удален"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"User not found\"}"))
                    )
            }
    )
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
