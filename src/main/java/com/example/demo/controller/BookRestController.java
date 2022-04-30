package com.example.demo.controller;

import com.example.demo.DB.BookEntity;
import com.example.demo.DB.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    // private final BookServiceDTO bookServiceDTO;
    private final BookService bookService;

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
    public ResponseEntity<BookEntity> createBook(@RequestBody final BookEntity bookDto) {
        System.out.println("Accept request: " + bookDto);

        BookEntity bookEntity = bookService.createBook(bookDto.getAuthor(), bookDto.getTitle(), bookDto.getIsbn());

        return ResponseEntity.ok()
                .body(bookEntity);
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
}
