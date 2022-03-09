package com.test.project.controller;


import com.test.project.model.Author;
import com.test.project.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/manager/{mid}/new")
    public String createAuthorPage(Model model, @PathVariable Long mid) {
        model.addAttribute("managerId", mid);

        return "authors/new_author";
    }

    @PostMapping
    public String saveAuthor(@ModelAttribute Author author, @RequestParam Long mid) {
        authorService.save(author);

        return String.format("redirect:/managers/%d", mid);
    }

}
