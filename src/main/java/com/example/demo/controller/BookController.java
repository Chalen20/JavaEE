package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.repos.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

//    @GetMapping("/old-book-create")
//    public String oldBookForm(){
//        return "redirect:/book-create";
//    }

    // @RequestMapping(value = "/book-create", method = RequestMethod.GET)
    @GetMapping("/book-create")
    public String bookForm(){
        return "book-create";
    }

    @GetMapping("/")
    public String defaultRedirect(){
        return "redirect:/books";
    }

    @PostMapping("/book-create")
    public String saveBook(final BookDto bookDto) {
        System.out.println("save book: " + bookDto);
        // model.addAttribute("book", bookDto);
        BookRepository.addBook(bookDto);
        //return "saved-book";
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String bookView(final Model model) {
        model.addAttribute("books", BookRepository.getBooks());
        return "saved-book";
    }
}
