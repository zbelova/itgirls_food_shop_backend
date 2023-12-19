package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.Product.dto.*;
import ru.Product.dto.mapper.OrderDtoMapper;
import ru.Product.model.*;
import ru.Product.repository.OrderRepository;
import ru.Product.repository.UserRepository;
import ru.Product.service.CartService;
import ru.Product.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDtoMapper orderDtoMapper;
    private final CartService cartService;

    @Override
    public List<OrderGetAllDto> getAllOrdersByUserId(UUID id) {
        log.info("Поиск всех заказов пользователя с id: {}", id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            log.error("Пользователь не найден с id: {}", id);
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        List<Order> orders = orderRepository.findAllByUserId(id);
        List<OrderGetAllDto> orderDtoList = new ArrayList<>();
        for (Order order : orders) {
            log.info("Статус заказа с id {}: {}", order.getId(), order.getStatus());
            OrderGetAllDto orderDto = convertOrderToProductDto(order);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    @Override
    public List<OrderGetAllDto> getAllOrders() {
        log.info("Получить все заказы");
        List<Order> allOrders = orderRepository.findAll();
        List<OrderGetAllDto> orderDtoList = new ArrayList<>();

        for (Order order : allOrders) {
            log.info("Получение заказа с id: {}", order.getId());
            OrderGetAllDto orderDto = new OrderGetAllDto();
            orderDto.setId(order.getId());

            List<OrderedProductDto> orderedProducts = order.getOrderedProducts().stream()
                    .map(this::convertToOrderedProductDto)
                    .collect(Collectors.toList());

            orderDto.setOrderedProducts(orderedProducts);
            orderDto.setDateTime(order.getDateTime());
            orderDto.setTotalPrice(BigDecimal.valueOf(order.getTotalPrice()));
            orderDto.setStatus(order.getStatus());
            orderDto.setUser(convertToUserDto(order.getUser()));

            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    @Override
    public OrderGetAllDto getOrderById(UUID id) {
        log.info("Получение заказа с id: {}", id);
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            log.error("Заказ с id {} не найден", id);
            throw new NotFoundException("Заказ с id" + id + " не найден");
        } else {
            Order order = optionalOrder.get();
            log.info("Заказ найден");
            OrderGetAllDto orderDto = new OrderGetAllDto();
            orderDto.setId(order.getId());
            orderDto.setOrderedProducts(convertToOrderItemDtos(order.getOrderedProducts()));
            orderDto.setStatus(order.getStatus());
            orderDto.setUser(convertToUserDto(order.getUser()));
            orderDto.setDateTime(order.getDateTime());
            orderDto.setTotalPrice(BigDecimal.valueOf(order.getTotalPrice()));
            return orderDto;
        }
    }

    // TODO сейчас не находит в БД продукты по UUID просто как пример
    @Override
    public OrderSaveDto createOrder(OrderSaveDto orderSaveDto) {
//        // Получаем список объектов Product по списку UUID из OrderSaveDto
//        log.info("Попытка получить список продуктов по id {}", orderSaveDto.getProduct());
//        List<Product> productList = productRepository.findAllById(orderSaveDto.getProduct());
//
//        // Создаем новый объект Order на основе OrderSaveDto
//        Order order = Order.builder()
//                .id(UUID.randomUUID()) // Генерируем новый UUID для заказа
//                .product(productList) // Помещаем список объектов Product
//                .dateTime(orderSaveDto.getDateTime())
//                .totalPrice(orderSaveDto.getTotalPrice())
//                .status(orderSaveDto.getStatus())
//                .user(User.builder().id(orderSaveDto.getUserId()).build()) // Используем только id пользователя для связи
//                .build();
//
//        // Сохраняем заказ в репозитории базы данных
//        Order savedOrder = orderRepository.save(order);
//        log.info("Заказ с id {} сохранен в базе данных", savedOrder.getId());
//
//        // Создаем и возвращаем новый OrderSaveDto на основе сохраненного объекта Order
//        return OrderSaveDto.builder()
//                .product(orderSaveDto.getProduct())
//                .dateTime(savedOrder.getDateTime())
//                .totalPrice(savedOrder.getTotalPrice())
//                .status(savedOrder.getStatus())
//                .userId(savedOrder.getUser().getId())
//                .build();
        return null;
    }

    @Override
    public OrderDto createOrder(UUID user_id) {
        log.info("Создание нового заказа пользователя c id: {}", user_id);
        Order savedOrder = null;
        Optional<User> userOptional = userRepository.findById(user_id);
        if (userOptional.isEmpty()) {
            log.info("Пользователь не найден c id: {}", user_id);
            throw new NotFoundException("Пользователь не найден с id: " + user_id);
        } else {
            User user = userOptional.get();
            Cart cart = user.getCart();
            if ((cart == null)) {
                log.info("Корзина пользователя с id {} не найдена", user_id);
                throw new NotFoundException("Корзина пользователя  с id " + user_id + " не найдена");
            } else {
                Set<CartItem> cartItems = cart.getCartItems();
                if (cartItems.isEmpty()) {
                    log.info("Корзина пользователя с id {} пуста", user_id);
                    throw new NotFoundException("Корзина пользователя с id " + user_id + " пуста");
                } else {
                    Order order = createUserOrderFromCart(user, cart);
                    savedOrder = orderRepository.save(order);
                    cartService.clearCart(user_id);
                    log.info("Заказ создан c id: {}", savedOrder.getId());
                }
            }
        }
        return orderDtoMapper.toDto(savedOrder);
    }

    private Set<OrderedProduct> getOrderedProducts(Cart cart, Order order) {
        log.info("Определение продуктов из корзины");
        Set<CartItem> cartItems = cart.getCartItems();
        return cartItems.stream()
                .map(cartItem -> OrderedProduct
                        .builder()
                        .pk(OrderedProductPK
                                .builder()
                                .orderId(order.getId())
                                .productId(cartItem.getProduct().getId())
                                .build())
                        .order(order)
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getProduct().getPrice().intValue())
                        .name(cartItem.getProduct().getName())
                        .product(cartItem.getProduct())
                        .build())
                .collect(Collectors.toSet());
    }

    private Order createUserOrderFromCart(User user, Cart cart) {
        log.info("Определение параметров заказа");
        Order order = Order
                .builder()
                .id(UUID.randomUUID())
                .user(user)
                .dateTime(LocalDate.now())
                .address(user.getAddress())
                .status("Created")
                .totalPrice(cart.calculateItemsCost())
                .build();
        Set<OrderedProduct> orderedProducts = getOrderedProducts(cart, order);
        order.setOrderedProducts(orderedProducts);
        return order;
    }

//    private ProductDto convertToProductDto(Product product) {
//        return ProductDto.builder()
//                .id(product.getId())
//                .name(product.getName())
//                .description(product.getDescription())
//                .image(product.getImage())
//                .price(product.getPrice())
//                .quantity(product.getQuantity())
//                .category(convertToCategoryDto(product.getCategory()))
//                .build();
//    }

//    private CategoryDto convertToCategoryDto(Category category) {
//        return CategoryDto.builder()
//                .id(category.getId())
//                .name(category.getName())
//                .image(category.getImage())
//                .build();
//    }

    private OrderGetAllDto convertOrderToProductDto(Order order) {
        log.info("Преобразование заказа в список продуктов с id: {}", order.getId());
        OrderGetAllDto orderDto = new OrderGetAllDto();
        orderDto.setId(order.getId());

        List<OrderedProductDto> productDtoList = order.getOrderedProducts().stream()
                .map(orderedProduct -> {
                    log.info("Преобразование заказанных продуктов с id: {}", orderedProduct.getProduct().getId());
                    return OrderedProductDto
                            .builder()
                            .productId(orderedProduct.getProduct().getId())
                            .productName(orderedProduct.getName())
                            .productPrice(orderedProduct.getPrice())
                            .productQuantity(orderedProduct.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());
        orderDto.setOrderedProducts(productDtoList);
        orderDto.setDateTime(order.getDateTime());
        orderDto.setTotalPrice(BigDecimal.valueOf(order.getTotalPrice()));
        orderDto.setStatus(order.getStatus());
        orderDto.setUser(UserDto.builder()
                .id(order.getUser().getId())
                .name(order.getUser().getName())
                .email(order.getUser().getEmail())
                .phone(order.getUser().getPhone())
                .address(order.getUser().getAddress())
                .password(order.getUser().getPassword())
                .build());
        return orderDto;
    }

    private OrderedProductDto convertToOrderedProductDto(OrderedProduct orderedProduct) {
        log.info("Преобразование заказанного продукта с id: {}", orderedProduct.getProduct().getId());
        return OrderedProductDto.builder()
                .productQuantity(orderedProduct.getQuantity())
                .build();
    }

    private UserDto convertToUserDto(User user) {
        log.info("Преобразование пользователя с id: {}", user.getId());
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .password(user.getPassword())
                .build();
    }
}


