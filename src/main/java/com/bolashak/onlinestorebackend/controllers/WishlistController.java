package com.bolashak.onlinestorebackend.controllers;

import com.bolashak.onlinestorebackend.dto.WishlistDto;
import com.bolashak.onlinestorebackend.entities.Wishlist;
import com.bolashak.onlinestorebackend.services.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
@Tag(name = "Список желаемого", description = "Эндпоинты для управления списком желаемого (Wishlist)")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    @Operation(
            summary = "Получить список желаемого текущего пользователя",
            description = "Возвращает текущий список желаемого для аутентифицированного пользователя.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список желаемого успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Wishlist.class))
                    )
            }
    )
    public ResponseEntity<Wishlist> getWishlistForCurrentUser() {
        return ResponseEntity.ok(wishlistService.getWishlistForCurrentUser());
    }

    @PostMapping("/add/{productId}")
    @Operation(
            summary = "Добавить продукт в список желаемого",
            description = "Добавляет продукт с указанным ID в список желаемого текущего пользователя.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Продукт успешно добавлен в список желаемого",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Wishlist.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Продукт не найден",
                            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Product not found\"}"))
                    )
            }
    )
    public ResponseEntity<Wishlist> addProductToWishlist(@PathVariable String productId) {
        return ResponseEntity.ok(wishlistService.addProductToWishlist(productId));
    }

    @DeleteMapping("/remove/{productId}")
    @Operation(
            summary = "Удалить продукт из списка желаемого",
            description = "Удаляет продукт с указанным ID из списка желаемого текущего пользователя.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Продукт успешно удален из списка желаемого"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Продукт не найден в списке желаемого",
                            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Product not found in wishlist\"}"))
                    )
            }
    )
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable String productId) {
        wishlistService.removeProductFromWishlist(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    @Operation(
            summary = "Очистить список желаемого",
            description = "Удаляет все продукты из списка желаемого текущего пользователя.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Список желаемого успешно очищен"
                    )
            }
    )
    public ResponseEntity<Void> clearWishlist() {
        wishlistService.clearWishlist();
        return ResponseEntity.noContent().build();
    }
}
