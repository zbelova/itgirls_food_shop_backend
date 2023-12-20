package ru.Product.service;

import ru.Product.dto.OrderDto;
import ru.Product.dto.OrderGetAllDto;
import ru.Product.dto.OrderSaveDto;
import ru.Product.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderGetAllDto> getAllOrdersByUserId(UUID id);
    List<OrderGetAllDto> getAllOrders();
    OrderGetAllDto getOrderById(UUID id);
    OrderSaveDto createOrder(OrderSaveDto orderSaveDto);
    OrderDto createOrder(UUID user_id);
    void updateOrderItemQuantity(UUID orderId, UUID productId, int quantity);
    void addProductToOrder(UUID orderId, UUID productId, int quantity);
}
