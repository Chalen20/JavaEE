package com.example.demo.controller;

import com.example.demo.DB.BookEntity;
import com.example.demo.DB.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PageController {

    private final BookService bookService;

    @RequestMapping("/")
    public String index (Model model) {
        List<BookEntity> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "index";
    }
}
