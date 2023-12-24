package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.Product.dto.OrderDto;
import ru.Product.dto.OrderGetAllDto;
import ru.Product.dto.OrderedProductDto;
import ru.Product.dto.UserDto;
import ru.Product.dto.mapper.OrderDtoMapper;
import ru.Product.model.*;
import ru.Product.repository.OrderRepository;
import ru.Product.repository.OrderedProductRepository;
import ru.Product.repository.ProductRepository;
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
    private final ProductRepository productRepository;
    private final OrderedProductRepository orderedProductRepository;
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
        log.info("Получение заказа по id: {}", id);
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            log.error("Заказ не найден по id: {}", id);
            throw new NotFoundException("Заказ с id" + id + " не найден");
        } else {
            Order order = optionalOrder.get();
            log.info("Заказ получен с id: {}", order.getId());
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

    @Override
    public OrderDto createOrder(UUID userId) {
        log.info("Создание нового заказа пользователя c id: {}", userId);
        Order savedOrder = null;
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            log.info("Пользователь не найден c id: {}", userId);
            throw new NotFoundException("Пользователь не найден с id: " + userId);
        } else {
            User user = userOptional.get();
            Cart cart = user.getCart();
            if ((cart == null)) {
                log.info("Корзина пользователя с id {} не найдена", userId);
                throw new NotFoundException("Корзина пользователя  с id " + userId + " не найдена");
            } else {
                Set<CartItem> cartItems = cart.getCartItems();
                if (cartItems.isEmpty()) {
                    log.info("Корзина пользователя с id {} пуста", userId);
                    throw new NotFoundException("Корзина пользователя с id " + userId + " пуста");
                } else {
                    Order order = createUserOrderFromCart(user, cart);
                    savedOrder = orderRepository.save(order);
                    cartService.clearCart(userId);
                    log.info("Заказ создан c id: {}", savedOrder.getId());
                }
            }
        }
        return orderDtoMapper.toDto(savedOrder);
    }

    @Override
    public void updateOrderItemQuantity(UUID orderId, UUID productId, int quantity) {
        log.info("Изменение количества продукта с id {} в заказе с id {} на {} шт.", productId, orderId, quantity);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            Optional<OrderedProduct> optionalOrderedProduct = getOrderedProduct(order, productId);
            optionalOrderedProduct.ifPresent(orderedProduct -> {
                log.info("Количество продукта с id {} в заказе с id {} равно {}", productId, orderId, orderedProduct.getQuantity());
                orderedProduct.setQuantity(quantity);
                orderRepository.save(order);
                log.info("Изменено количество продукта с id {} в заказе с id {} на {} шт.", productId, orderId, quantity);
            });
            if (optionalOrderedProduct.isEmpty()) {
                throw new NotFoundException("Продукт с id " + productId + " не найден в заказе с id " + orderId);
            }
        } else {
            throw new NotFoundException("Заказ с id " + orderId + " не найден");
        }
    }

    @Override
    public void addProductToOrder(UUID orderId, UUID productId, int quantity) {
        log.info("Добавление продукта с id {} в заказ с id {} на {} шт.", productId, orderId, quantity);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalOrder.isPresent() && optionalProduct.isPresent()) {
            Order order = optionalOrder.get();
            Product product = optionalProduct.get();
            if (isProductAlreadyInOrder(order, product)) {
                throw new IllegalArgumentException("Продукт с id " + productId + " уже есть в заказе с id " + orderId);
            }
            OrderedProduct orderedProduct = createOrderedProduct(order, product, quantity);
            order.getOrderedProducts().add(orderedProduct);
            orderRepository.save(order);
            log.info("Продукт с id {} добавлен в заказ с id {} на {} шт.", productId, orderId, quantity);
        } else {
            throw new NotFoundException("Заказ с id " + orderId + " или продукт с id " + productId + " не найден");
        }
    }

    @Override
    public void removeProductFromOrder(UUID orderId, UUID productId) {
        log.info("Удаление продукта с id {} из заказа с id {}", productId, orderId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            Optional<OrderedProduct> optionalOrderedProduct = getOrderedProduct(order, productId);
            if (optionalOrderedProduct.isPresent()) {
                OrderedProduct orderedProduct = optionalOrderedProduct.get();
                order.getOrderedProducts().remove(orderedProduct);
                orderRepository.save(order);
                orderedProductRepository.delete(orderedProduct);
                log.info("Продукт с id {} удалён из заказа с id {}", productId, orderId);
            } else {
                throw new NotFoundException("Продукт с id " + productId + " не найден в заказе с id " + orderId);
            }
        } else {
            throw new NotFoundException("Заказ с id " + orderId + " не найден");
        }
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
                .status("Создан")
                .totalPrice(cart.calculateItemsCost())
                .build();
        Set<OrderedProduct> orderedProducts = getOrderedProducts(cart, order);
        order.setOrderedProducts(orderedProducts);
        return order;
    }

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

    private List<OrderedProductDto> convertToOrderItemDtos(Set<OrderedProduct> orderedProductDtos) {
        return orderedProductDtos.stream()
                .map(this::convertToOrderItemDto)
                .collect(Collectors.toList());
    }

    private OrderedProductDto convertToOrderItemDto(OrderedProduct orderedProductDto) {
        return OrderedProductDto.builder()
                .productId(orderedProductDto.getProduct().getId())
                .productName(orderedProductDto.getName())
                .productPrice(orderedProductDto.getPrice())
                .productQuantity(orderedProductDto.getQuantity())
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

    private Optional<OrderedProduct> getOrderedProduct(Order order, UUID productId) {
        return order.getOrderedProducts().stream()
                .filter(orderedProduct -> orderedProduct.getProduct().getId().equals(productId))
                .findFirst();
    }

    private OrderedProduct createOrderedProduct(Order order, Product product, int quantity) {
        return OrderedProduct.builder()
                .product(product)
                .name(product.getName())
                .order(order)
                .quantity(quantity)
                .price(product.getPrice().intValue())
                .build();
    }

    private boolean isProductAlreadyInOrder(Order order, Product product) {
        for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
            if (orderedProduct.getProduct().equals(product)) {
                return true;
            }
        }
        return false;
    }
}


