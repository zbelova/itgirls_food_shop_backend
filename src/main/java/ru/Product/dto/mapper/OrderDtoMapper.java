package ru.Product.dto.mapper;

import ru.Product.dto.OrderDto;
import ru.Product.dto.OrderedProductDto;
import ru.Product.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {
    default OrderDto toDto(Order order){
        List<OrderedProductDto> orderedProductList = order
                .getOrderedProducts()
                .stream()
                .map(orderedProduct -> OrderedProductDto
                        .builder()
                        .productId(orderedProduct.getProduct().getId())
                        .productName(orderedProduct.getName())
                        .productPrice(orderedProduct.getPrice())
                        .productQuantity(orderedProduct.getQuantity())
                        .build())
                .toList();
        return OrderDto
                .builder()
                .id(order.getId())
                .status(order.getStatus().getName())
                .userName(order.getUser().getName())
                .dateTime(order.getDateTime())
                .address(order.getAddress())
                .totalPrice(order.getTotalPrice())
                .orderedProducts(orderedProductList)
                .build();
    };
}
