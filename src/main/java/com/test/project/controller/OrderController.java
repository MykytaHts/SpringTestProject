package com.test.project.controller;

import com.test.project.model.Book;
import com.test.project.model.Order;
import com.test.project.service.BookService;
import com.test.project.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final BookService bookService;
    private final OrderService orderService;

    public OrderController(BookService bookService, OrderService orderService) {
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @PostMapping("/user/{id}")
    public String createNewOrder(@PathVariable Long id, @RequestParam String title) {
        Order order = new Order();
        orderService.save(order, title, id);

        return String.format("redirect:/users/%d", id);
    }

    @PostMapping("approve/{id}")
    public String submit(@PathVariable Long id) {
        Order order = orderService.findById(id);
        order.setActive(true);
        orderService.save(order);

        return String.format("redirect:/managers/%d", order.getUser().getId());
    }

    @DeleteMapping("/book/{id}")
    public String returnBook(@PathVariable Long id, @RequestParam Long userId) {
        Book book = bookService.findById(id);
        book.setActive(false);
        Order order = orderService.findOrderByBookId(id);
        order.setActive(false);
        order.setReturnDate(LocalDate.now());

        // Місце для питання про контекст
        orderService.save(order);
        bookService.save(book);

        return String.format("redirect:/users/%d", userId);
    }
}