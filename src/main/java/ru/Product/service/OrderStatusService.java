package ru.Product.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Product.dto.OrderStatusDto;
import ru.Product.model.OrderStatus;
import ru.Product.model.StatusName;
import ru.Product.repository.OrderStatusRepository;

import java.util.List;

@Service
public interface OrderStatusService {

    OrderStatus getOrderStatusByName(StatusName name);

}

