package com.example.demo.controller;

import com.example.demo.DB.*;
import com.example.demo.domain.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    // private final BookServiceDTO bookServiceDTO;
    private final BookService bookService;
    private final UserService userService;
//    @RequestMapping(value = "/create-book", method = RequestMethod.POST)
//    public ResponseEntity<BookResponseDto> createBook(@RequestBody final BookDto bookDto) {
//        System.out.println("Accept login request: " + bookDto);
//
//        BookRepository.addBook(bookDto);
//        final BookResponseDto responseDto = bookServiceDTO.doLogic(bookDto);
//
//        return ResponseEntity.ok()
//                .body(responseDto);
//    }

    @RequestMapping(value = "/create-book", method = RequestMethod.POST)
    public ResponseEntity<BookEntity> createBook(
            @Valid @RequestBody final BookEntity bookDto) throws MethodArgumentNotValidException {
        if (Utils.isValidISBN(bookDto.getIsbn())) {
            System.out.println("Accept request: " + bookDto);

            BookEntity bookEntity = bookService.createBook(bookDto.getAuthor(), bookDto.getTitle(), bookDto.getIsbn());

            return ResponseEntity.ok()
                    .body(bookEntity);
        } else {
            BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "book");
            bindingResult.addError(new FieldError("bookEntity", "isbn", "ISBN is invalid"));
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
    }

//    @ResponseBody
//    @GetMapping("/get-books")
//    public List<BookEntity> getBooksByName (@RequestParam(value = "name", required = false) final String requiredField) {
//        if (requiredField == null) {
//            return bookService.findAllBooks();
//        }
//        return bookService.findAllBooks().stream().filter(
//                bookDto -> bookDto.getTitle().contains(requiredField) ||
//                        bookDto.getIsbn().contains(requiredField)).collect(Collectors.toList());
//    }

    @PostMapping("/add-to-favourite")
    public ResponseEntity<UserEntity>  AddToFavourites(@RequestParam(value = "param") final String bookIsbn) {
        System.out.println("here");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserEntity> myUser = userService.findFavouriteByLogin(username);
        BookEntity book = bookService.getBookByIsbn(bookIsbn);
        if (myUser.isPresent()) {
            if (book != null) {
                UserEntity user = userService.addToFavourites(myUser.get(), book);
                System.out.println("ok");
                return ResponseEntity.ok()
                        .body(user);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/remove-from-favourite")
    public ResponseEntity<UserEntity> RemoveFromFavourites(@RequestParam(value = "param") final String bookIsbn) {
        System.out.println("here");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<UserEntity> myUser = userService.findFavouriteByLogin(username);
        BookEntity book = bookService.getBookByIsbn(bookIsbn);
        if (myUser.isPresent()) {
            if (book != null) {
                UserEntity user = userService.removeFromFavourites(myUser.get(), book);
                System.out.println("ok");
                return ResponseEntity.ok()
                        .body(user);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @ResponseBody
    @GetMapping("/get-books")
    public Page<BookEntity> getBooksByName (@RequestParam(value = "name", required = false) final String requiredField,
                                            @RequestParam(value = "page", required = false, defaultValue = "0") final int pageNumber) {
        if (requiredField == null || requiredField.isEmpty()) {
            return bookService.findAllBooks(pageNumber);
        }
        //return bookService.findAllBooks(pageNumber);
        return bookService.findBookWhereAuthorOrTitleOrISBNContains(requiredField, pageNumber);
    }

    @GetMapping("/get-favourite-books")
    public Set<BookEntity> getFavouriteBooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findAllFavouriteBooks(username);
    }
}
