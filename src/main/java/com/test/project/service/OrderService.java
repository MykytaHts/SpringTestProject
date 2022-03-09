package com.test.project.service;

import com.test.project.dao.BookRepository;
import com.test.project.dao.OrderRepository;
import com.test.project.dao.UserRepository;
import com.test.project.model.Book;
import com.test.project.model.Order;
import com.test.project.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public Long save(Order order) {
        return orderRepository.save(order).getId();
    }

    public Long save(Order order, String title, Long id) {
        Book book = bookRepository.findAvailableBookByTitle(title);
        book.setActive(true);
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        user.addOrder(order);
        order.setBook(book);

        return orderRepository.save(order).getId();
    }

    @Transactional(readOnly = true)
    public List<Order> findAllOrderByUserId(Long id) {
        return orderRepository.findAllOrderByUserId(id);
    }

    @Transactional(readOnly = true)
    public List<Order> findInactiveOrders() {
        return orderRepository.findAllInactiveOrders().stream()
                .filter(x -> x.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Order findOrderByBookId(Long id) {
        return orderRepository.findOrderByBookId(id);
    }

    @Transactional(readOnly = true)
    public Long countOrderedBooks(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) return null;

        return orderRepository.countOrderedBooks(startDate, endDate);
    }
}
