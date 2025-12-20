package com.bolashak.onlinestorebackend.controllers;

import com.bolashak.onlinestorebackend.dto.ProductDto;
import com.bolashak.onlinestorebackend.entities.Product;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Эндпоинты для управления продуктами")
public class ProductController {

        private final ProductServiceImpl productService;
        private final EntityManager entityManager;

        @GetMapping
        @Operation(summary = "Получить продукты с фильтрацией", description = "Возвращает список продуктов с возможностью фильтрации по категории, характеристике, статусу, типу и имени.", responses = {
                        @ApiResponse(responseCode = "200", description = "Список продуктов успешно получен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)))
        })
        public ResponseEntity<List<ProductDto>> getFilteredProducts(
                        @RequestParam(required = false) ProductCategory category,
                        @RequestParam(required = false) ProductFeature feature,
                        @RequestParam(required = false) ProductStatus status,
                        @RequestParam(required = false) ProductType type,
                        @RequestParam(required = false) String name,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {
                return ResponseEntity.ok(
                                productService.getFilteredProducts(category, feature, status, type, name, page, size));
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
        @PostMapping
        @Operation(summary = "Создать продукт", description = "Создает новый продукт на основе переданных данных.", responses = {
                        @ApiResponse(responseCode = "200", description = "Продукт успешно создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)))
        })
        public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
                ProductDto createdProduct = productService.createProduct(productDto);
                return ResponseEntity.ok(createdProduct);
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
        @PutMapping("/{id}")
        @Operation(summary = "Обновить продукт", description = "Обновляет данные существующего продукта.", responses = {
                        @ApiResponse(responseCode = "200", description = "Продукт успешно обновлен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)))
        })
        public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
                return ResponseEntity.ok(productService.updateProduct(id, productDto));
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
        @DeleteMapping("/{id}")
        @Operation(summary = "Удалить продукт", description = "Удаляет продукт по указанному ID.", responses = {
                        @ApiResponse(responseCode = "204", description = "Продукт успешно удален")
        })
        public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
                productService.deleteProduct(id);
                return ResponseEntity.noContent().build();
        }

        @GetMapping("/id/{id}")
        @Operation(summary = "Получить продукт по ID", description = "Возвращает информацию о продукте по указанному ID.", responses = {
                        @ApiResponse(responseCode = "200", description = "Продукт успешно найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class)))
        })
        public ResponseEntity<ProductDto> getById(@PathVariable String id) {
                return ResponseEntity.ok(productService.getById(id));
        }

        // ⚠️ VULNERABILITY: SQL Injection - FOR EDUCATIONAL PURPOSES ONLY
        // This endpoint is intentionally vulnerable for security testing demonstration
        @GetMapping("/search")
        @Operation(summary = "Поиск продуктов", description = "Поиск продуктов по названию", responses = {
                        @ApiResponse(responseCode = "200", description = "Результаты поиска", content = @Content(mediaType = "application/json"))
        })
        public ResponseEntity<?> vulnerableSearch(@RequestParam String query) {
                try {
                        // VULNERABILITY: Direct string concatenation in SQL query
                        // This allows SQL injection attacks
                        // Using LOWER() for case-insensitive search
                        String sql = "SELECT * FROM product WHERE LOWER(name) LIKE LOWER('%" + query + "%')";

                        System.out.println("⚠️ EXECUTING VULNERABLE QUERY: " + sql);

                        Query nativeQuery = entityManager.createNativeQuery(sql, Product.class);
                        List<Product> results = nativeQuery.getResultList();

                        return ResponseEntity.ok(results);
                } catch (Exception e) {
                        // VULNERABILITY: Exposing detailed error messages
                        System.err.println("⚠️ SQL ERROR: " + e.getMessage());
                        return ResponseEntity.status(500).body("Database Error: " + e.getMessage() +
                                        "\n\nStack Trace: " + e.getClass().getName());
                }
        }
}
