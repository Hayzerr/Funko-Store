package com.bolashak.onlinestorebackend.controllers;

import com.bolashak.onlinestorebackend.dto.request.RegisterRequest;
import com.bolashak.onlinestorebackend.dto.response.UserResponse;
import com.bolashak.onlinestorebackend.dto.response.AuthenticationResponse;
import com.bolashak.onlinestorebackend.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Эндпоинты для управления регистрацией, входом, выходом и обновлением токенов")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Регистрирует нового пользователя в системе на основе предоставленных данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для регистрации пользователя",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно зарегистрирован",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка в предоставленных данных",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Invalid data\"}")
                            )
                    )
            }
    )
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        UserResponse registeredUser = authenticationService.register(registerRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Вход пользователя",
            description = "Позволяет пользователю войти в систему, предоставив имя пользователя и пароль",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Вход успешен. Возвращает токен доступа и обновления",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Неверные учетные данные",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Invalid credentials\"}")
                            )
                    )
            }
    )
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            AuthenticationResponse response = authenticationService.login(username, password);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Обновление токена доступа",
            description = "Обновляет токен доступа с использованием предоставленного обновляющего токена",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Токен успешно обновлен",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Неверный или истекший токен обновления",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Invalid refresh token\"}")
                            )
                    )
            }
    )
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) {
        try {
            AuthenticationResponse response = authenticationService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Выход пользователя",
            description = "Удаляет токен обновления и завершает сеанс пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Выход выполнен успешно",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Logout successful\"")
                            )
                    )
            }
    )
    public ResponseEntity<?> logout(@RequestParam String refreshToken) {
        authenticationService.logout(refreshToken);
        return ResponseEntity.ok("Logout successful");
    }
}
