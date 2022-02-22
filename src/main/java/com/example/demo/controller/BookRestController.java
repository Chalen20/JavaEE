package com.example.demo.controller;

import com.example.demo.BookService;
import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.repos.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @RequestMapping(value = "/create-book", method = RequestMethod.POST)
    public ResponseEntity<BookResponseDto> createBook(@RequestBody final BookDto bookDto) {
        System.out.println("Accept login request: " + bookDto);

        BookRepository.addBook(bookDto);
        final BookResponseDto responseDto = bookService.doLogin(bookDto);

        return ResponseEntity.ok()
                .body(responseDto);
    }
}
