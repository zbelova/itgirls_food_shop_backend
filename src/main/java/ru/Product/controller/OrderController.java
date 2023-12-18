package ru.Product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.Product.dto.OrderDto;
import ru.Product.dto.OrderGetAllDto;
import ru.Product.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
@SecurityRequirement(name = "Заказы")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("getAllOrdersByUserId")
    @Operation(summary = "Получение всех заказов пользователя по id")
    public List<OrderGetAllDto> getAllOrdersByUserId(
            @Parameter(description = "id пользователя", required = true) @RequestParam(value = "id") String id
    ) {
        return orderService.getAllOrdersByUserId(UUID.fromString(id));
    }

    @GetMapping("/getAllOrders")
    @Operation(summary = "Получение всех заказов в БД")
    public List<OrderGetAllDto> getAllOrder() {
        return orderService.getAllOrders();
    }

    // TODO сейчас не находит в БД продукты по UUID просто как пример, выпилить
    /*@PostMapping("/saveOrder")
    @Operation(summary = "Добавление заказа")
    public ResponseEntity<OrderSaveDto> createOrder(@RequestBody @Valid OrderSaveDto orderSaveDto) {
        // Вызываем метод сервиса для создания заказа
        OrderSaveDto savedOrder = orderService.createOrder(orderSaveDto);

        // Возвращаем HTTP-ответ со статусом 200 OK и сохраненным заказом в теле ответа
        return ResponseEntity.ok(savedOrder);
    }*/

    @PostMapping("/saveOrder")
    @Operation(summary = "Добавление заказа")
    public OrderDto createOrder(@RequestParam("user_id") String user_id) {
        return orderService.createOrder(UUID.fromString(user_id));
    }
}
