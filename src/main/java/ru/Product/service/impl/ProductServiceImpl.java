package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.Product.dto.ProductDto;
import ru.Product.model.Product;
import ru.Product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ru.Product.service.ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(this::convertToProductDto)
                .toList();
    }

    @Override
    public ProductDto getOne(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product foundProduct = product.get();
            return convertToProductDto(foundProduct);
        } else {
            throw new NotFoundException("Продукта с таким id нет: " + id);
        }

    }
    private ProductDto convertToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .quantity(product.getQuantity())
//                .category(product.getCategory())
                .build();
    }
}
