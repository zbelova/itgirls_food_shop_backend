package ru.Product.service;

import ru.Product.dto.OrderDto;
import ru.Product.dto.OrderGetAllDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderGetAllDto> getAllOrdersByUserId(UUID id);

    List<OrderGetAllDto> getAllOrders();

    OrderGetAllDto getOrderById(UUID id);

    OrderDto createOrder(UUID UserId);

    void updateOrderItemQuantity(UUID orderId, UUID productId, int quantity);

    void addProductToOrder(UUID orderId, UUID productId, int quantity);

    void removeProductFromOrder(UUID orderId, UUID productId);
}
