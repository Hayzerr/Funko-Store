package com.bolashak.onlinestorebackend.controllers;

import com.bolashak.onlinestorebackend.dto.ProductDto;
import com.bolashak.onlinestorebackend.entities.enums.ProductCategory;
import com.bolashak.onlinestorebackend.entities.enums.ProductFeature;
import com.bolashak.onlinestorebackend.entities.enums.ProductStatus;
import com.bolashak.onlinestorebackend.entities.enums.ProductType;
import com.bolashak.onlinestorebackend.services.impl.ProductServiceImpl;
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
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Эндпоинты для управления продуктами")
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping
    @Operation(
            summary = "Получить продукты с фильтрацией",
            description = "Возвращает список продуктов с возможностью фильтрации по категории, характеристике, статусу, типу и имени.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список продуктов успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<List<ProductDto>> getFilteredProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) ProductFeature feature,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) ProductType type,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.getFilteredProducts(category, feature, status, type, name, page, size));
    }

    @GetMapping("/list")
    @Operation(
            summary = "Получить все продукты",
            description = "Возвращает список всех доступных продуктов.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех продуктов успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping
    @Operation(
            summary = "Создать продукт",
            description = "Создает новый продукт на основе переданных данных.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Продукт успешно создан",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить продукт",
            description = "Обновляет данные существующего продукта.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Продукт успешно обновлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить продукт",
            description = "Удаляет продукт по указанному ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Продукт успешно удален"
                    )
            }
    )
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/id/{id}")
    @Operation(
            summary = "Получить продукт по ID",
            description = "Возвращает информацию о продукте по указанному ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Продукт успешно найден",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<ProductDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/category/{category}")
    @Operation(
            summary = "Получить продукты по категории",
            description = "Возвращает список продуктов, принадлежащих указанной категории.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список продуктов по категории успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<List<ProductDto>> getByCategory(@PathVariable ProductCategory category) {
        return ResponseEntity.ok(productService.getByCategory(category));
    }

    @GetMapping("/feature/{feature}")
    @Operation(
            summary = "Получить продукты по характеристике",
            description = "Возвращает список продуктов с указанной характеристикой.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список продуктов по характеристике успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<List<ProductDto>> getByFeature(@PathVariable ProductFeature feature) {
        return ResponseEntity.ok(productService.getByFeature(feature));
    }

    @GetMapping("/status/{status}")
    @Operation(
            summary = "Получить продукты по статусу",
            description = "Возвращает список продуктов с указанным статусом.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список продуктов по статусу успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<List<ProductDto>> getByStatus(@PathVariable ProductStatus status) {
        return ResponseEntity.ok(productService.getByStatus(status));
    }

    @GetMapping("/type/{type}")
    @Operation(
            summary = "Получить продукты по типу",
            description = "Возвращает список продуктов указанного типа.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список продуктов по типу успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<List<ProductDto>> getByType(@PathVariable ProductType type) {
        return ResponseEntity.ok(productService.getByType(type));
    }

    @GetMapping("/name/{name}")
    @Operation(
            summary = "Получить продукты по имени",
            description = "Возвращает список продуктов, соответствующих указанному имени.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список продуктов по имени успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    public ResponseEntity<List<ProductDto>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getByName(name));
    }
}
