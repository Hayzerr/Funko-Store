package com.bolashak.onlinestorebackend.controllers;

import com.bolashak.onlinestorebackend.entities.Order;
import com.bolashak.onlinestorebackend.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Эндпоинты для управления заказами")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(
            summary = "Создать заказ",
            description = "Создает новый заказ из корзины текущего пользователя. Требуется указать адрес доставки.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Заказ успешно создан",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка при создании заказа (например, пустая корзина)",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Cart is empty. Cannot create order.\"}")
                            )
                    )
            }
    )
    public ResponseEntity<Order> createOrder(@RequestParam String shippingAddress) {
        return ResponseEntity.ok(orderService.createOrder(shippingAddress));
    }

    @GetMapping("/{orderId}")
    @Operation(
            summary = "Получить заказ по ID",
            description = "Возвращает информацию о заказе по указанному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Заказ успешно найден",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Order.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Заказ не найден",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Order not found\"}")
                            )
                    )
            }
    )
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping
    @Operation(
            summary = "Получить заказы текущего пользователя",
            description = "Возвращает список всех заказов, связанных с текущим пользователем.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список заказов успешно получен",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = Order.class)
                            )
                    )
            }
    )
    public ResponseEntity<List<Order>> getOrdersForCurrentUser() {
        return ResponseEntity.ok(orderService.getOrdersForCurrentUser());
    }

    @PutMapping("/{orderId}/cancel")
    @Operation(
            summary = "Отменить заказ",
            description = "Отменяет заказ по указанному идентификатору. Можно отменять только заказы в статусе `PENDING`.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Заказ успешно отменен"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка при отмене заказа (например, заказ уже обработан)",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Only pending orders can be canceled.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Заказ не найден",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Order not found\"}")
                            )
                    )
            }
    )
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
