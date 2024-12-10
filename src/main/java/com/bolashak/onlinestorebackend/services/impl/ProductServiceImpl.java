package com.bolashak.onlinestorebackend.services.impl;

import com.bolashak.onlinestorebackend.dto.ProductDto;
import com.bolashak.onlinestorebackend.entities.Image;
import com.bolashak.onlinestorebackend.entities.Product;
import com.bolashak.onlinestorebackend.repository.ProductRepository;
import com.bolashak.onlinestorebackend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<List<ProductDto>> list() {
        List<Product> products = productRepository.findAll();

        return ResponseEntity.ok(mapToProductDto(products));
    }
    public Iterable<Product> save(List<ProductDto> productDtos) {

        return productRepository.saveAll(mapToProduct(productDtos));
    }

    @Override
    public List<ProductDto> getByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category).stream().toList();

        return mapToProductDto(products);
    }

    @Override
    public List<ProductDto > getByFeature(String feature) {
        List<Product> products = productRepository.findByFeature(feature).stream().toList();

        return mapToProductDto(products);
    }
    @Override
    public List<ProductDto > getByStatus(String status) {
        List<Product> products = productRepository.findByStatus(status).stream().toList();

        return mapToProductDto(products);
    }

    @Override
    public List<ProductDto > getAll() {
        List<Product> products = productRepository.findAll();

        return mapToProductDto(products);
    }

    @Override
    public ProductDto  getById(String id) {
        List<Product> products = productRepository.findById(id).stream().toList();

        return mapToProductDto(products).getFirst();
    }

    @Override
    public List<ProductDto > getByName(String Name) {
        List<Product> products = productRepository.findByName(Name).stream().toList();

        return mapToProductDto(products);
    }

    @Override
    public List<ProductDto > getByStock(String stock) {
        List<Product> products = productRepository.findByStock(stock).stream().toList();

        return mapToProductDto(products);       }

    @Override
    public List<ProductDto> getByType(String type) {
        List<Product> products = productRepository.findByProductType(type).stream().toList();

        return mapToProductDto(products);
    }
    private List<ProductDto> mapToProductDto(List<Product> products){
        return products.stream()
                .map(product -> {
                    ProductDto productDto = modelMapper.map(product, ProductDto.class);

                    if (product.getImages() != null) {
                        productDto.setImages(product.getImages().stream()
                                .map(Image::getImageUrl)
                                .collect(Collectors.toList()));
                    }

                    return productDto;
                })
                .collect(Collectors.toList());
    }

    private List<Product> mapToProduct(List<ProductDto> productDtos){
        return productDtos.stream()
                .map(productDto -> {
                    Product product = modelMapper.map(productDto, Product.class);
                    List<Image> images = productDto.getImages().stream()
                            .map(imageUrl -> new Image(imageUrl, product))
                            .collect(Collectors.toList());
                    product.setImages(images);
                    return product;
                })
                .collect(Collectors.toList());
    }
}