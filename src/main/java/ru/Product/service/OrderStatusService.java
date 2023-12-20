package ru.Product.service;

import org.springframework.stereotype.Service;
import ru.Product.model.OrderStatus;

import java.util.List;

@Service
public interface OrderStatusService {

    OrderStatus getOrderStatusByName(String name);

    List<OrderStatus> getAll();

}

