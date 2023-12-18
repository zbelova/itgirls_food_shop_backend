package ru.Product.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Product.dto.OrderGetAllDto;
import ru.Product.dto.OrderSaveDto;
import ru.Product.model.Order;
import ru.Product.model.Product;
import ru.Product.model.User;
import ru.Product.repository.OrderRepository;
import ru.Product.repository.ProductRepository;
import ru.Product.service.impl.OrderServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    public void testGetAllOrdersByUserId() {

//        UUID id = UUID.randomUUID();
//        List<Product> product = new ArrayList<>();
//        LocalDate dateTime = LocalDate.now();
//        BigDecimal totalPrice = BigDecimal.valueOf(100);
//        String status = "Processed";
//        User user = new User();
//
//        Order order = Order.builder()
//                .id(UUID.randomUUID())
//                .product(product)
//                .dateTime(dateTime)
//                .totalPrice(totalPrice)
//                .status(status)
//                .user(user)
//                .build();
//
//        when(orderRepository.findAllByUserId(id)).thenReturn(List.of(order));
//
//
//        List<OrderGetAllDto> orderGetAllDto = orderService.getAllOrdersByUserId(id);
//
//        verify(orderRepository).findAllByUserId(id);
//        for (OrderGetAllDto orderDto : orderGetAllDto) {
//            Assertions.assertEquals(orderDto.getId(), order.getId());
//            Assertions.assertEquals(orderDto.getDateTime(), order.getDateTime());
//            Assertions.assertEquals(orderDto.getTotalPrice(), order.getTotalPrice());
//            Assertions.assertEquals(orderDto.getStatus(), order.getStatus());
//        }
    }


    @Test
    public void testGetAllOrders() {

//        when(orderRepository.findAllByUserId(UUID.randomUUID())).thenReturn(new ArrayList<>());
//
//        List<Product> product = new ArrayList<>();
//        LocalDate dateTime = LocalDate.now();
//        BigDecimal totalPrice = BigDecimal.valueOf(100);
//        String status = "Processed";
//        User user = new User();
//
//        Order order = Order.builder()
//                .id(UUID.randomUUID())
//                .product(product)
//                .dateTime(dateTime)
//                .totalPrice(totalPrice)
//                .status(status)
//                .user(user)
//                .build();
//
//        when(orderRepository.findAll()).thenReturn(List.of(order));
//
//        List<OrderGetAllDto> orderGetAllDto = orderService.getAllOrders();
//
//        verify(orderRepository).findAll();
//        for (OrderGetAllDto orderDto : orderGetAllDto) {
//            Assertions.assertEquals(orderDto.getId(), order.getId());
//            Assertions.assertEquals(orderDto.getDateTime(), order.getDateTime());
//            Assertions.assertEquals(orderDto.getTotalPrice(), order.getTotalPrice());
//            Assertions.assertEquals(orderDto.getStatus(), order.getStatus());
//        }
//
    }

    @Test
    public void testCreateOrder() {

//        List<Product> product = new ArrayList<>();
//        LocalDate dateTime = LocalDate.now();
//        BigDecimal totalPrice = BigDecimal.valueOf(100);
//        String status = "Processed";
//        User user = new User();
//
//        Order order = Order.builder()
//                .id(UUID.randomUUID())
//                .product(product)
//                .dateTime(dateTime)
//                .totalPrice(totalPrice)
//                .status(status)
//                .user(user)
//                .build();
//
//        OrderSaveDto orderDto = OrderSaveDto.builder()
//                .product(new ArrayList<>())
//                .dateTime(dateTime)
//                .totalPrice(totalPrice)
//                .status(status)
//                .userId(user.getId())
//                .build();
//
//        when(productRepository.findAllById(any())).thenReturn(new ArrayList<>());
//        when(orderRepository.save(any(Order.class))).thenReturn(order);
//
//        OrderSaveDto orderSaveDto = orderService.createOrder(orderDto);
//
//        verify(orderRepository).save(any(Order.class));
//        Assertions.assertEquals(orderSaveDto.getProduct(), order.getProduct());
//        Assertions.assertEquals(orderSaveDto.getDateTime(), order.getDateTime());
//        Assertions.assertEquals(orderSaveDto.getTotalPrice(), order.getTotalPrice());
//        Assertions.assertEquals(orderSaveDto.getStatus(), order.getStatus());
//        Assertions.assertEquals(orderSaveDto.getUserId(), order.getUser().getId());
//    }
}
