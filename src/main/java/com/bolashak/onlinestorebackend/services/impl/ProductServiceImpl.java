package com.bolashak.onlinestorebackend.services.impl;

import com.bolashak.onlinestorebackend.dto.ProductDto;
import com.bolashak.onlinestorebackend.entities.Image;
import com.bolashak.onlinestorebackend.entities.Product;
import com.bolashak.onlinestorebackend.entities.enums.ProductCategory;
import com.bolashak.onlinestorebackend.entities.enums.ProductFeature;
import com.bolashak.onlinestorebackend.entities.enums.ProductStatus;
import com.bolashak.onlinestorebackend.entities.enums.ProductType;
import com.bolashak.onlinestorebackend.repository.ProductRepository;
import com.bolashak.onlinestorebackend.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = mapToProduct(productDto);
        product.setId(UUID.randomUUID().toString());
        Product savedProduct = productRepository.save(product);
        return mapToProductDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(String id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        modelMapper.map(productDto, existingProduct);

        if (productDto.getImages() != null) {
            List<Image> images = productDto.getImages().stream()
                    .map(imageUrl -> new Image(imageUrl, existingProduct))
                    .collect(Collectors.toList());
            existingProduct.setImages(images);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return mapToProductDto(updatedProduct);
    }

    @Override
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public ProductDto getById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToProductDto(product);
    }

    @Override
    public List<ProductDto> getFilteredProducts(ProductCategory category, ProductFeature feature, ProductStatus status,
                                                ProductType type, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productsPage = productRepository.findFilteredProducts(
                category, feature, status, type, name != null ? "%" + name + "%" : null, pageable);

        return productsPage.getContent().stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }

    public Iterable<Product> list() {
        return productRepository.findAll();
    }
    public Iterable<Product> save(List<Product> products) {
        return productRepository.saveAll(products);
    }
    @Override
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return mapToProductDto(products);
    }

    @Override
    public List<ProductDto> getByCategory(ProductCategory category) {
        List<Product> products = productRepository.findByCategory(category);
        return mapToProductDto(products);
    }

    @Override
    public List<ProductDto> getByFeature(ProductFeature feature) {
        List<Product> products = productRepository.findByFeature(feature);
        return mapToProductDto(products);
    }

    @Override
    public List<ProductDto> getByStatus(ProductStatus status) {
        List<Product> products = productRepository.findByStatus(status);
        return mapToProductDto(products);
    }

    @Override
    public List<ProductDto> getByName(String name) {
        List<Product> products = productRepository.findByName(name);
        return mapToProductDto(products);
    }

    @Override
    public List<ProductDto> getByType(ProductType type) {
        List<Product> products = productRepository.findByProductType(type);
        return mapToProductDto(products);
    }

    private Product mapToProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);

        if (productDto.getImages() != null) {
            List<Image> images = productDto.getImages().stream()
                    .map(imageUrl -> new Image(imageUrl, product))
                    .collect(Collectors.toList());
            product.setImages(images);
        }

        return product;
    }

    private List<ProductDto> mapToProductDto(List<Product> products) {
        return products.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }

    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);

        if (product.getImages() != null) {
            List<String> imageUrls = product.getImages().stream()
                    .map(Image::getImageUrl)
                    .collect(Collectors.toList());
            productDto.setImages(imageUrls);
        }

        return productDto;
    }
}
