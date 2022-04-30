package com.example.demo.controller;

import com.example.demo.DB.BookEntity;
import com.example.demo.DB.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        Page<BookEntity> books = bookService.findAllBooks(0);
        model.addAttribute("books", books.getContent());
        model.addAttribute("isLast", books.isLast()?"display_none":"display_block");
        model.addAttribute("isFirst", books.isFirst()?"display_none":"display_block");
        return "index";
    }
}
