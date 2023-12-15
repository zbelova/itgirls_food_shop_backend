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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        log.info("Попытка найти все продукты из категории по id", id);
        Optional<Category> category =  categoryRepository.findById(id);
        if (category.isPresent()) {
            log.info("Если категория существует, собирается список продуктов из этой категории");
            Category foundCategory = category.get();
            List<Product> productList = productRepository.findAllByCategoryId(id);
            List<ProductDto> productDtoList = new ArrayList<>();
            for (Product product : productList) {
                ProductDto productDto = new ProductDto();
                productDto.setId(product.getId());
                productDto.setName(product.getName());
                productDto.setDescription(product.getDescription());
                productDto.setImage(product.getImage());
                productDto.setPrice(product.getPrice());
                productDto.setQuantity(product.getQuantity());
                productDto.setCategoryName(product.getCategory().getName());
                productDtoList.add(productDto);
            }
            return productDtoList;

        } else {
            log.error("Ошибка на методе получения продуктов из одной категории");
            throw new NotFoundException("Категории продуктов с таким id нет: " + id);
        }
    }

    @Override
    public ProductDto createProduct(ProductCreateDto productCreateDto) {
        log.info("Создание продукта");
        Product newProduct = convertToProductEntity(productCreateDto);
        Product savedProduct = productRepository.save(newProduct);
        Category category = savedProduct.getCategory();
        return convertToProductDto(savedProduct, category);
    }

    @Override
    public ProductDto updateProduct(UUID id, ProductUpdateDto productUpdateDto) {
        log.info("Обновление информации по продукту по id");
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            log.info("Если продукт существует, то изменить информацию о нем");
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(productUpdateDto.getName());
            existingProduct.setCategory(categoryRepository.findByName(productUpdateDto.getCategoryName()));
            existingProduct.setDescription(productUpdateDto.getDescription());
            existingProduct.setPrice(productUpdateDto.getPrice());
            existingProduct.setQuantity(productUpdateDto.getQuantity());
            existingProduct.setImage(productUpdateDto.getImage());
            Product updatedProduct = productRepository.save(existingProduct);
            Category category = existingProduct.getCategory();
            return convertToProductDto(updatedProduct, category);
        } else {
            log.error("Ошибка на методе поиска продукта по id");
            throw new NotFoundException("Product not found with id: " +  id);
        }
    }

    @Override
    public void deleteProduct(UUID id) {
        log.info("Удаление продукта по id");
        productRepository.deleteById(id);
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
