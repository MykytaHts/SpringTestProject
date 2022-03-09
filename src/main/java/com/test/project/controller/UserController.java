package com.test.project.controller;

import com.test.project.model.Book;
import com.test.project.model.User;
import com.test.project.service.BookService;
import com.test.project.service.OrderService;
import com.test.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    private final BookService bookService;
    private final UserService userService;
    private final OrderService orderService;

    private LocalDate startDate;
    private LocalDate endDate;

    public UserController(BookService bookService,
                          UserService userService,
                          OrderService orderService)
    {
        this.bookService = bookService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public String getUserPage(@PathVariable Long id, Model model) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        if (startDate != null && endDate != null) {
            model.addAttribute("startDate", startDate.format(dateTimeFormatter));
            model.addAttribute("endDate", endDate.format(dateTimeFormatter));
        }
        model.addAttribute("popularBook", bookService.getTheMostPopularBookBetween(startDate, endDate));
        model.addAttribute("unpopularBook", bookService.getTheMostUnpopularBookBetween(startDate, endDate));
        model.addAttribute("titles", bookService.findAllTitlesAvailable());
        model.addAttribute("ownBooks", userService.findUserBooksById(id));
        model.addAttribute("orders", orderService.findAllOrderByUserId(id));
        model.addAttribute("userId", id);

        return "user";
    }

    @GetMapping("/new")
    public String getCreateUserPage() {
        return "users/new_user";
    }

    @GetMapping("/{id}/manager/{mid}")
    public String getUserInfoPage(@PathVariable Long id, @PathVariable Long mid, Model model) {
        User user = userService.findById(id);
        List<Book> books = userService.findUserBooksById(id);
        model.addAttribute("user", user);
        model.addAttribute("books", books);
        model.addAttribute("managerId", mid);

        return "users/user_page";
    }

    @PostMapping
    public String saveNewUser(@ModelAttribute User user) {
        Long id = userService.save(user);

        return String.format("redirect:/users/%d", id);
    }

    @PostMapping("/book/{id}")
    public String getBookStatisticsBetweenDate(@PathVariable Long id,
                                               @RequestParam String startDate,
                                               @RequestParam String endDate) {
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);

        return String.format("redirect:/users/%d", id);
    }
}
