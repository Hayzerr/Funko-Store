package com.bolashak.onlinestorebackend.controllers;

import com.bolashak.onlinestorebackend.entities.Cart;
import com.bolashak.onlinestorebackend.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Корзина", description = "Эндпоинты для управления корзиной пользователя")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(
            summary = "Получить корзину текущего пользователя",
            description = "Возвращает текущую корзину, связанную с аутентифицированным пользователем",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Корзина успешно получена")
            }
    )
    public ResponseEntity<Cart> getCartForCurrentUser() {
        return ResponseEntity.ok(cartService.getCartForCurrentUser());
    }

    @PostMapping("/add/{productId}")
    @Operation(
            summary = "Добавить продукт в корзину",
            description = "Добавляет продукт в корзину текущего пользователя с указанным количеством",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Продукт успешно добавлен в корзину"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос")
            }
    )
    public ResponseEntity<Cart> addProductToCart(
            @PathVariable String productId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(cartService.addProductToCart(productId, quantity));
    }

    @PutMapping("/update/{productId}")
    @Operation(
            summary = "Обновить количество продукта",
            description = "Обновляет количество указанного продукта в корзине текущего пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Количество продукта обновлено"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос")
            }
    )
    public ResponseEntity<Cart> updateProductQuantity(
            @PathVariable String productId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(cartService.updateProductQuantity(productId, quantity));
    }

    @DeleteMapping("/remove/{productId}")
    @Operation(
            summary = "Удалить продукт из корзины",
            description = "Удаляет указанный продукт из корзины текущего пользователя",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Продукт успешно удален из корзины"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос")
            }
    )
    public ResponseEntity<Void> removeProductFromCart(@PathVariable String productId) {
        cartService.removeProductFromCart(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    @Operation(
            summary = "Очистить корзину",
            description = "Удаляет все продукты из корзины текущего пользователя",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Корзина успешно очищена")
            }
    )
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}
