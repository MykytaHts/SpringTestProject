package com.test.project.controller;

import com.test.project.model.User;
import com.test.project.service.BookService;
import com.test.project.service.OrderService;
import com.test.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping("/managers")
public class ManagerController {

    private final BookService bookService;
    private final OrderService orderService;
    private final UserService userService;

    private LocalDate startDateOrder;
    private LocalDate endDateOrder;
    private LocalDate startDateBook;
    private LocalDate endDateBook;

    public ManagerController(BookService bookService,
                             OrderService orderService,
                             UserService userService) {
        this.bookService = bookService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String getMainPage(@PathVariable Long id, Model model) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Map<String, Long> titleMap = new HashMap<>();
        Map<User, Long> userMap = new HashMap<>();
        List<String> titles = bookService.findAllDistinctByTitle();
        List<User> users =  userService.findAllReaders();

        for (String title: titles) {
            titleMap.put(title, bookService.amountWithTitle(title));
        }
        for (User user: users) {
            userMap.put(user, DAYS.between(user.getRegistrationDate(), LocalDate.now()));
        }
        if (startDateBook != null && endDateBook != null) {
            model.addAttribute("startDateBook", startDateBook.format(dateTimeFormatter));
            model.addAttribute("endDateBook", endDateBook.format(dateTimeFormatter));
        }
        if (startDateOrder != null && endDateOrder != null) {
            model.addAttribute("startDateOrder", startDateOrder.format(dateTimeFormatter));
            model.addAttribute("endDateOrder", endDateOrder.format(dateTimeFormatter));
        }

        model.addAttribute("booksOrdered", orderService.countOrderedBooks(startDateBook, endDateBook));
        model.addAttribute("debtors", userService.findAllDebtors());
        model.addAttribute("averageOrders", bookService.averageBookOrders(startDateOrder, endDateOrder));
        model.addAttribute("averageDays", bookService.averageDays());
        model.addAttribute("averageAge", bookService.averageAge());
        model.addAttribute("managerId", id);
        model.addAttribute("readerMap", userMap);
        model.addAttribute("titleMap", titleMap);
        model.addAttribute("orders", orderService.findInactiveOrders());

        return "manager";
    }

    @PostMapping("/order/{id}")
    public String setOrderDateBetween(@PathVariable Long id,
                                 @RequestParam String startDate,
                                 @RequestParam String endDate) {

        this.startDateOrder = LocalDate.parse(startDate);
        this.endDateOrder = LocalDate.parse(endDate);

        return String.format("redirect:/managers/%d", id);
    }

    @PostMapping("/book/{id}")
    public String setBookDateBetween(@PathVariable Long id,
                                 @RequestParam String startDate,
                                 @RequestParam String endDate) {

        this.startDateBook = LocalDate.parse(startDate);
        this.endDateBook = LocalDate.parse(endDate);

        return String.format("redirect:/managers/%d", id);
    }
}
