package com.test.project.dao;

import com.test.project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.user.id = :id")
    List<Order> findAllOrderByUserId(@Param("id") Long id);

    @Query("select o from Order o where o.active = false")
    List<Order> findAllInactiveOrders();

    @Query("select o from Order o where o.book.id = :id and o.active=true")
    Order findOrderByBookId(@Param("id") Long id);

    @Query(value = "SELECT CAST(COUNT(*) AS INTEGER) " +
            "FROM orders o " +
            "WHERE o.order_date BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    Long countOrderedBooks(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
