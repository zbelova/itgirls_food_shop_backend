package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.Product.model.OrderStatus;
import ru.Product.service.OrderStatusService;

import java.util.List;

@RestController
@RequestMapping("api/name/orderStatus/")
@SecurityRequirement(name = "Статус заказа")
@RequiredArgsConstructor


public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    @GetMapping("/{name}")
    @Operation(summary = "Получить состояниe Корзины" )
    public OrderStatus getOrderStatusByName(@PathVariable String statusName) {
        OrderStatus orderStatus = orderStatusService.getOrderStatusByName(statusName);
        return orderStatus;
    }

    @GetMapping("/getAll")
    @Operation(summary = "Получит информацию про состояние Корзины")
    public List<OrderStatus> getAllOrderStatus() {
        return orderStatusService.getAll();
    }


}
