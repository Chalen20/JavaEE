package com.example.demo.repos;

import com.example.demo.dto.BookDto;

import java.util.ArrayList;

public class BookRepository {
    private static final ArrayList<BookDto> books = new ArrayList<>();

    public static void addBook(BookDto bookDto) {
        books.add(bookDto);
    }

    public static ArrayList<BookDto> getBooks(){
        return books;
    }
}
