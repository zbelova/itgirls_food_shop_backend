package ru.Product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.Product.dto.OrderDto;
import ru.Product.dto.OrderGetAllDto;
import ru.Product.dto.OrderSaveDto;
import ru.Product.dto.mapper.OrderDtoMapper;
import ru.Product.model.*;
import ru.Product.repository.OrderRepository;
import ru.Product.repository.UserRepository;
import ru.Product.service.OrderService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderDtoMapper orderDtoMapper;

    @Override
    public List<OrderGetAllDto> getAllOrdersByUserId(UUID id) {
//        log.info("Попытка получить список заказов по user_id {}", id);
//        List<Order> orders = orderRepository.findAllByUserId(id);
//        List<OrderGetAllDto> orderDtoList = new ArrayList<>();
//
//        for (Order order : orders) {
//            log.info("Статус заказа с id {}: {}", order.getId(), order.getStatus());
//            OrderGetAllDto orderDto = new OrderGetAllDto();
//            orderDto.setId(order.getId());
//
//            // Преобразуем список продуктов Order в список ProductDto
//            List<ProductDto> productDtoList = order.getProduct().stream()
//                    .map(this::convertToProductDto)
//                    .collect(Collectors.toList());
//
//            orderDto.setProduct(productDtoList);
//            orderDto.setDateTime(order.getDateTime());
//            orderDto.setTotalPrice(order.getTotalPrice());
//            orderDto.setStatus(order.getStatus());
//            orderDto.setUser(UserDto.builder()
//                    .id(order.getUser().getId())
//                    .name(order.getUser().getName())
//                    .email(order.getUser().getEmail())
//                    .phone(order.getUser().getPhone())
//                    .address(order.getUser().getAddress())
//                    .password(order.getUser().getPassword())
//                    .build());
//
//            orderDtoList.add(orderDto);
//        }
//
//        return orderDtoList;
        return null;
    }

    @Override
    public List<OrderGetAllDto> getAllOrders() {
//        log.info("Попытка получить список всех заказов");
//        List<Order> allOrders = orderRepository.findAll();
//        List<OrderGetAllDto> orderDtoList = new ArrayList<>();
//
//        for (Order order : allOrders) {
//            log.info("Статус заказа с id {}: {}", order.getId(), order.getStatus());
//            OrderGetAllDto orderDto = new OrderGetAllDto();
//            orderDto.setId(order.getId());
//
//            // Преобразуем список продуктов Order в список ProductDto
//            List<ProductDto> productDtoList = order.getProduct().stream()
//                    .map(this::convertToProductDto)
//                    .collect(Collectors.toList());
//
//            orderDto.setProduct(productDtoList);
//            orderDto.setDateTime(order.getDateTime());
//            orderDto.setTotalPrice(order.getTotalPrice());
//            orderDto.setStatus(order.getStatus());
//            // Установка значения поля user
//            orderDto.setUser(UserDto.builder()
//                    .id(order.getUser().getId())
//                    .name(order.getUser().getName())
//                    .email(order.getUser().getEmail())
//                    .phone(order.getUser().getPhone())
//                    .address(order.getUser().getAddress())
//                    .password(order.getUser().getPassword())
//                    .build());
//
//            orderDtoList.add(orderDto);
//        }
//
//        return orderDtoList;
        return null;
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
        log.info("Создание нового заказа пользователя: {}", user_id);
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
                    //TODO очистить корзину
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
                .totalPrice(500000)                                  //TODO cart.getTotal();
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

}


