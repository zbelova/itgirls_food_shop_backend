package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.Product.dto.ProductCreateDto;
import ru.Product.dto.ProductDto;
import ru.Product.dto.ProductUpdateDto;
import ru.Product.model.Category;
import ru.Product.model.Product;
import ru.Product.repository.CategoryRepository;
import ru.Product.repository.ProductRepository;
import ru.Product.service.ProductService;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> getAll() {
        log.info("Поиск всех продуктов");
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> {
                    log.info("Преобразование продукта в DTO");
                    return ProductDto.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .image(product.getImage())
                            .price(product.getPrice())
                            .quantity(product.getQuantity())
                            .categoryName(product.getCategory().getName())
                            .build();
                })
                .toList();
    }

    @Override
    public ProductDto getOne(UUID id) {
        log.info("Поиск продукта с id: {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product foundProduct = product.get();
            log.info("Найден продукт: {}", foundProduct);
            Category category = foundProduct.getCategory();
            log.info("Найдена категория: {}", category);
            return convertToProductDto(foundProduct, category);
        } else {
            log.error("Продукт не найден с id: {}", id);
            throw new NotFoundException("Продукт не найден с id: " + id);
        }

    }

    @Override
    public List<ProductDto> getAllFromOneCategory(UUID id) {
        log.info("Поиск всех продуктов в категории с id: {}", id);
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category foundCategory = category.get();
            log.info("Найдена категория: {}", foundCategory);
            List<Product> products = productRepository.findAllByCategoryId(id);
            return products.stream()
                    .map(product -> {
                        log.info("Преобразование продукта в DTO");
                        return ProductDto.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .image(product.getImage())
                                .price(product.getPrice())
                                .quantity(product.getQuantity())
                                .categoryName(foundCategory.getName())
                                .build();
                    })
                    .toList();
        } else {
            log.error("Категория продуктов не найдена с id: {}", id);
            throw new NotFoundException("Категория продуктов не найдена с id: " + id);
        }
    }

    @Override
    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        log.info("Создание нового продукта: {}", productCreateDto);
        Product newProduct = convertToProductEntity(productCreateDto);
        Product savedProduct = productRepository.save(newProduct);
        Category category = savedProduct.getCategory();
        log.info("Продукт создан: {}", savedProduct);
        return convertToProductDto(savedProduct, category);
    }

    @Override
    public ProductDto updateProduct(UUID id, ProductUpdateDto productUpdateDto) {
        log.info("Обновление информации о продукте с id: {}", id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            log.info("Найден существующий продукт: {}", existingProduct);
            existingProduct.setName(productUpdateDto.getName());
            existingProduct.setCategory(categoryRepository.findByName(productUpdateDto.getCategoryName()));
            existingProduct.setDescription(productUpdateDto.getDescription());
            existingProduct.setPrice(productUpdateDto.getPrice());
            existingProduct.setQuantity(productUpdateDto.getQuantity());
            existingProduct.setImage(productUpdateDto.getImage());
            Product updatedProduct = productRepository.save(existingProduct);
            log.info("Продукт обновлён: {}", updatedProduct);
            Category category = existingProduct.getCategory();
            return convertToProductDto(updatedProduct, category);
        } else {
            log.error("Продукт не найден с id: {}", id);
            throw new NotFoundException("Продукт не найден с id: " +  id);
        }
    }

    @Override
    public void deleteProduct(UUID id) {
        log.info("Удаление продукта с id: {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public Map<UUID, Integer> getProductsInStock(List<UUID> productIds) {
        log.info("Получение количества продуктов в наличии: {}", productIds);
        Map<UUID, Integer> productsInStock = new HashMap<>();
        for (UUID productId : productIds) {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                log.info("Найден продукт: {}", product);
                int quantityInStock = product.getQuantity();
                productsInStock.put(productId, quantityInStock);
                log.info("Продукт с id {} в наличии: {} шт.", productId, quantityInStock);
            } else {
                throw new NotFoundException("Продукт с id: " + productId + " не найден");
            }
        }
        return productsInStock;
    }

    private Product convertToProductEntity(ProductCreateDto productCreateDto) {
        return Product.builder()
                .id(UUID.randomUUID())
                .name(productCreateDto.getName())
                .category(categoryRepository.findByName(productCreateDto.getCategoryName()))
                .description(productCreateDto.getDescription())
                .price(productCreateDto.getPrice())
                .quantity(productCreateDto.getQuantity())
                .image(productCreateDto.getImage())
                .build();
    }

    private ProductDto convertToProductDto(Product product, Category category) {

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .categoryName(category.getName())
                .build();
    }
}
