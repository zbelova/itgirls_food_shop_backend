package ru.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Product.model.OrderStatus;

import java.util.Optional;
@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    Optional<OrderStatus> findByName(String statusName);


}
//Можно сделать отдельным задачами, по шагам.
//
//Создать миграцию для добавления нового поля status_id в таблицу order с помощью команды:
//ALTER TABLE ADD COLUMN, добавить ссылку на таблицу order_status
//
//В сущности Заказ сделать поле статус через сущность Статус заказов (отношение @manytoone как жанр у книги в библиотеке).
//
//Изменить методы OrderService, чтобы работали через новое поле:
//getAllOrdersByUserId()
//getAllOrders()
//createOrder()