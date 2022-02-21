package com.example.demo;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookResponseDto;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    public BookResponseDto doLogin(final BookDto bookDto) {
        System.out.println("inside service");
        return BookResponseDto.of(bookDto.getIsbn(), "book created successfully");
    }

}
