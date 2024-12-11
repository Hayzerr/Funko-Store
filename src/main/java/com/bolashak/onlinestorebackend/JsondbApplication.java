//package com.bolashak.onlinestorebackend;
//
//import com.bolashak.onlinestorebackend.entities.Product;
//import com.bolashak.onlinestorebackend.services.impl.ProductServiceImpl;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//@SpringBootApplication
//public class JsondbApplication {
//
//    public static void main(String[] args) {
//
//        SpringApplication.run(JsondbApplication.class, args);
//    }
//    @Bean
//    CommandLineRunner runner(ProductServiceImpl productService) {
//        return args -> {
//            // Read JSON and write to DB
//            ObjectMapper mapper = new ObjectMapper();
//            TypeReference<List<Product>> typeReference = new TypeReference<List<Product>>() {};
//            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/products.json");
//            try {
//                List<Product> products = mapper.readValue(inputStream, typeReference);
//                productService.save(products);
//                System.out.println("Products Saved!");
//            } catch (IOException e) {
//                System.out.println("Unable to save products: " + e.getMessage());
//            }
//        };
//    }
//}