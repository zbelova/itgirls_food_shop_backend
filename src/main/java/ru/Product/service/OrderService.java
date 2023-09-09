package ru.Product.service;

import ru.Product.dto.OrderGetAllDto;
import ru.Product.dto.OrderSaveDto;
import ru.Product.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderGetAllDto> getAllOrdersByUserId(UUID id);

    List<OrderGetAllDto> getAllOrders();

    OrderSaveDto createOrder(OrderSaveDto orderSaveDto);
}
