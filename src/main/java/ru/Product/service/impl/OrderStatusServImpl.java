package ru.Product.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.Product.dto.OrderStatusDto;
import ru.Product.model.OrderStatus;
import ru.Product.model.StatusName;
import ru.Product.repository.OrderStatusRepository;
import ru.Product.service.OrderStatusService;

import java.util.List;
@Service
@AllArgsConstructor
public class OrderStatusServImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    @Override
    public OrderStatus getOrderStatusByName(StatusName name) {
        return orderStatusRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Order Status not found"));
    }
}
