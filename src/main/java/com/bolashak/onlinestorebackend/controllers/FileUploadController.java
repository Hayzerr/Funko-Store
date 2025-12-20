//package com.bolashak.onlinestorebackend.controllers;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.ExampleObject;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/upload")
//@RequiredArgsConstructor
//@Tag(name = "Загрузка файлов", description = "Эндпоинты для загрузки файлов в AWS S3")
//public class FileUploadController {
//
//    private final AmazonS3 s3client;
//
//    @Value("${aws.s3.bucket}")
//    private String bucketName;
//
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    @PostMapping
//    @Operation(
//            summary = "Загрузка файлов на S3",
//            description = "Позволяет загрузить до 10 файлов в хранилище AWS S3. Каждый файл сохраняется с уникальным именем.",
//            requestBody = @RequestBody(
//                    description = "Файлы для загрузки",
//                    required = true,
//                    content = @Content(
//                            mediaType = "multipart/form-data",
//                            schema = @Schema(type = "array", format = "binary", description = "Массив файлов")
//                    )
//            ),
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Файлы успешно загружены",
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(type = "array", description = "Список URL-адресов загруженных файлов")
//                            )
//                    ),
//                    @ApiResponse(
//                            responseCode = "400",
//                            description = "Превышено максимальное количество файлов (10)",
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    examples = @ExampleObject(value = "[]")
//                            )
//                    ),
//                    @ApiResponse(
//                            responseCode = "500",
//                            description = "Ошибка загрузки файлов",
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    examples = @ExampleObject(value = "null")
//                            )
//                    )
//            }
//    )
//    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
//        List<String> fileUrls = new ArrayList<>();
//
//        if (files.length > 10) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(null);
//        }
//
//        for (MultipartFile file : files) {
//            try {
//                ObjectMetadata metadata = new ObjectMetadata();
//                metadata.setContentLength(file.getSize());
//
//                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//                s3client.putObject(bucketName, fileName, file.getInputStream(), metadata);
//
//                String fileUrl = s3client.getUrl(bucketName, fileName).toString();
//                fileUrls.add(fileUrl);
//            } catch (IOException e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//            }
//        }
//
//        return ResponseEntity.ok(fileUrls);
//    }
//}
