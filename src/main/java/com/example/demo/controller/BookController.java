package com.example.demo.controller;

import com.example.demo.DB.BookEntity;
import com.example.demo.DB.BookService;
import com.example.demo.config.MyPasswordEncoder;
import com.example.demo.config.WebSecurityConfig;
import com.example.demo.dto.UserDto;
import com.example.demo.exeption.UserAlreadyExistAuthenticationException;
import com.example.demo.DB.UserService;
import com.example.demo.domain.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final HttpServletRequest servletRequest;

    private final MyPasswordEncoder passwordEncoder;
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
    public String getBook(final Model model, @PathVariable(value = "isbn") String isbn) {
        BookEntity book = bookService.getBookByIsbn(isbn);
        System.out.println(book);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found");
        }
        model.addAttribute("book", book);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserEntity> myUser = userService.findFavouriteByLogin(username);
        if (myUser.isPresent()) {
            boolean isFavourite = userService.isUserFavourite(myUser.get(), book);
            model.addAttribute("isUserFavourites", isFavourite);
            System.out.println("ok");
        }
        return "book-page";
    }

    @GetMapping(value = "/register-user")
    public String registerUser(final Model model,
                               @RequestParam(name = "userExistsError", required = false) final String userExistsError,
                               @RequestParam(name = "validationError", required = false) final String validationError) {
        model.addAttribute("validationError", validationError != null);
        model.addAttribute("userExistsError", userExistsError != null);
        return "registration-page";
    }

    @PostMapping(value = "/register-user")
    public String registerUser(@Valid final UserDto userDto) throws UserAlreadyExistAuthenticationException {
        UserEntity userEntity = userService.registerUser(userDto.getLogin(), passwordEncoder.encode(userDto.getPassword()));
        try {
            if (userEntity != null) {
                servletRequest.login(userDto.getLogin(), userDto.getPassword());
            }
        } catch (ServletException e) {
            System.out.println("ServletException");
        }

//        return ResponseEntity.ok()
//                .body(userEntity);
        return "redirect:/";
    }

    @GetMapping("/favourite-books")
    private String favouriteBooks(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Set<BookEntity> books = userService.findAllFavouriteBooks(username);
        model.addAttribute("books", books);
        return "favourite-books";
    }
}
