package ru.Product.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.Product.model.OrderStatus;
import ru.Product.model.StatusName;
import ru.Product.service.OrderStatusService;

import java.util.List;

@RestController
@RequestMapping
@SecurityRequirement(name = "Статус заказа")
@RequiredArgsConstructor


public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    @GetMapping("/{name}")
    public OrderStatus getOrderStatusByName(@PathVariable String name) {
        StatusName statusName = StatusName.valueOf(name.toUpperCase());
        OrderStatus orderStatus = orderStatusService.getOrderStatusByName(statusName);
        return orderStatus;
    }
}
