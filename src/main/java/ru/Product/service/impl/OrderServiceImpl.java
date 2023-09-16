package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.Product.dto.*;
import ru.Product.model.Category;
import ru.Product.model.Order;
import ru.Product.model.Product;
import ru.Product.model.User;
import ru.Product.repository.OrderRepository;
import ru.Product.repository.ProductRepository;
import ru.Product.service.CartService;
import ru.Product.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderGetAllDto> getAllOrdersByUserId(UUID id) {
        List<Order> orders = orderRepository.findAllByUserId(id);
        List<OrderGetAllDto> orderDtoList = new ArrayList<>();

        for (Order order : orders) {
            OrderGetAllDto orderDto = new OrderGetAllDto();
            orderDto.setId(order.getId());

            // Преобразуем список продуктов Order в список ProductDto
            List<ProductDto> productDtoList = order.getProduct().stream()
                    .map(this::convertToProductDto)
                    .collect(Collectors.toList());

            orderDto.setProduct(productDtoList);
            orderDto.setDateTime(order.getDateTime());
            orderDto.setTotalPrice(order.getTotalPrice());
            orderDto.setStatus(order.getStatus());
            orderDto.setUser(UserDto.builder()
                    .id(order.getUser().getId())
                    .name(order.getUser().getName())
                    .email(order.getUser().getEmail())
                    .phone(order.getUser().getPhone())
                    .address(order.getUser().getAddress())
                    .password(order.getUser().getPassword())
                    .build());

            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }

    @Override
    public List<OrderGetAllDto> getAllOrders() {
        List<Order> allOrders = orderRepository.findAll();
        List<OrderGetAllDto> orderDtoList = new ArrayList<>();

        for (Order order : allOrders) {
            OrderGetAllDto orderDto = new OrderGetAllDto();
            orderDto.setId(order.getId());

            // Преобразуем список продуктов Order в список ProductDto
            List<ProductDto> productDtoList = order.getProduct().stream()
                    .map(this::convertToProductDto)
                    .collect(Collectors.toList());

            orderDto.setProduct(productDtoList);
            orderDto.setDateTime(order.getDateTime());
            orderDto.setTotalPrice(order.getTotalPrice());
            orderDto.setStatus(order.getStatus());
            // Установка значения поля user
            orderDto.setUser(UserDto.builder()
                    .id(order.getUser().getId())
                    .name(order.getUser().getName())
                    .email(order.getUser().getEmail())
                    .phone(order.getUser().getPhone())
                    .address(order.getUser().getAddress())
                    .password(order.getUser().getPassword())
                    .build());

            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }

    // TODO сейчас не находит в БД продукты по UUID просто как пример
    @Override
    public OrderSaveDto createOrder(OrderSaveDto orderSaveDto) {
        // Получаем список объектов Product по списку UUID из OrderSaveDto
        List<Product> productList = productRepository.findAllById(orderSaveDto.getProduct());

        // Создаем новый объект Order на основе OrderSaveDto
        Order order = Order.builder()
                .id(UUID.randomUUID()) // Генерируем новый UUID для заказа
                .product(productList) // Помещаем список объектов Product
                .dateTime(orderSaveDto.getDateTime())
                .totalPrice(orderSaveDto.getTotalPrice())
                .status(orderSaveDto.getStatus())
                .user(User.builder().id(orderSaveDto.getUserId()).build()) // Используем только id пользователя для связи
                .build();

        // Сохраняем заказ в репозитории базы данных
        Order savedOrder = orderRepository.save(order);

        // Создаем и возвращаем новый OrderSaveDto на основе сохраненного объекта Order
        return OrderSaveDto.builder()
                .product(orderSaveDto.getProduct())
                .dateTime(savedOrder.getDateTime())
                .totalPrice(savedOrder.getTotalPrice())
                .status(savedOrder.getStatus())
                .userId(savedOrder.getUser().getId())
                .build();
    }

    private ProductDto convertToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(convertToCategoryDto(product.getCategory()))
                .build();
    }

    private CategoryDto convertToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .image(category.getImage())
                .build();
    }

}


