package com.example.demo.controller;

import com.example.demo.BookServiceDTO;
import com.example.demo.DB.BookEntity;
import com.example.demo.DB.BookService;
import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;
////    @GetMapping("/old-book-create")
////    public String oldBookForm(){
////        return "redirect:/book-create";
////    }
//
//    // @RequestMapping(value = "/book-create", method = RequestMethod.GET)
//    @GetMapping("/book-create")
//    public String bookForm(){
//        return "book-create";
//    }
//
//    @GetMapping("/")
//    public String defaultRedirect(){
//        return "redirect:/books";
//    }
//
//    @PostMapping("/book-create")
//    public String saveBook(final BookDto bookDto) {
//        System.out.println("save book: " + bookDto);
//        // model.addAttribute("book", bookDto);
//        BookRepository.addBook(bookDto);
//        //return "saved-book";
//        return "redirect:/books";
//    }
//
//    @GetMapping("/books")
//    public String bookView(final Model model) {
//        model.addAttribute("books", BookRepository.getBooks());
//        return "saved-book";
//    }
//    @ResponseBody
//    @GetMapping("/get-books")
//    public List<BookDto> getBooksByName (@RequestParam(value = "name", required = false) final String requiredField) {
//        if (requiredField == null) {
//            return BookRepository.getBooks();
//        }
//        return BookRepository.getBooks().stream().filter(
//                bookDto -> bookDto.getTitle().contains(requiredField) ||
//                        bookDto.getIsbn().contains(requiredField)).collect(Collectors.toList());
//    }

    @RequestMapping("/book/{isbn}")
    public String getBook(final Model model, @PathVariable(value="isbn") String isbn){
        BookEntity book = bookService.getBookByIsbn(isbn);
        System.out.println(book);
        model.addAttribute("book", book);
        return "book-page";
    }
}
