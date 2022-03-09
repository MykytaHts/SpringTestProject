package com.test.project.controller;

import com.test.project.model.Book;
import com.test.project.service.AuthorService;
import com.test.project.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @PostMapping
    public String saveBook(@RequestParam String title,
                                  @RequestParam Integer amount,
                                  @RequestParam Long id,
                                  @RequestParam(required = false) List<Long> ids,
                                  @RequestParam Long mid) {
        Book book = new Book();
        book.setTitle(title);
        bookService.save(book, amount, id, ids);

        return String.format("redirect:/managers/%d", mid);
    }
    @GetMapping("/manager/{mid}/new")
    public String createBookPage(@PathVariable Long mid, Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("managerId", mid);

        return "books/new_book";
    }

    @PutMapping("/{default}")
    public String updateBooksTitle(@PathVariable("default") String def,
                                   @RequestParam String fin,
                                   @RequestParam Long mid) {
        bookService.updateBooksTitle(def, fin);

        return String.format("redirect:/managers/%d", mid);
    }

    @PostMapping("/{mid}")
    public String get(@PathVariable Long mid,
                      @RequestParam LocalDate from,
                      @RequestParam LocalDate to) {

        return String.format("redirect:/managers/%d", mid);
    }

    @DeleteMapping("/{title}")
    public String deleteBooksAmount(@PathVariable String title,
                                    @RequestParam Long amount,
                                    @RequestParam Long mid) {
        bookService.deleteAmountBooksWithTitle(title, amount);

        return String.format("redirect:/managers/%d", mid);
    }
}
