package ru.Product.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Product.dto.ProductDto;
import ru.Product.model.Category;
import ru.Product.model.Product;
import ru.Product.repository.ProductRepository;
import ru.Product.service.impl.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    public ProductServiceTest() {
    }

    @Test
    public void testGetOne() {
        UUID categoryId = UUID.randomUUID();
        String categoryName = "Фрукты";
        String categoryImage = "fruits.org";
        List<Product> productList = new ArrayList<>();

        UUID productId = UUID.randomUUID();
        String productName = "Апельсины";
        String productDescription = "Апельсины крупные, 1 кг";
        String productImage = "oranges.org";
        BigDecimal productPrice = BigDecimal.valueOf(140);
        Integer productQuantity = 10;
        Category category = new Category(categoryId, categoryName, categoryImage, productList);
        Product product = new Product(productId,
                productName,
                productDescription,
                productImage,
                productPrice,
                productQuantity,
                category);
        when(productRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(product));
        ProductServiceTest productServiceTest = new ProductServiceTest();
        ProductDto result = productServiceTest.productService.getOne(UUID.randomUUID());
        assertNotNull(result);
        assertEquals(category.getName(), result.getCategoryName());
        verify(productRepository, times(1)).findById(ArgumentMatchers.any(UUID.class));


    }
}
