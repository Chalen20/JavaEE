package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.repos.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class ControllerUnitTest {

    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void shouldHandleCreateBookRequest() {
        when(bookService.doLogin(any())).thenReturn(BookResponseDto.of("isbn-value", "success-message"));

        mockMvc.perform(
                        post("/create-book")
                                .content(ControllerUnitTest.class.getResourceAsStream("/createBookRequest.json")
                                        .readAllBytes())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(
                        new String(ControllerUnitTest.class.getResourceAsStream("/createBookResponse.json")
                                .readAllBytes())));

        verify(bookService).doLogin(new BookDto("title", "isbn", "author"));
    }


    @Test
    @SneakyThrows
    void shouldHandleCreateBookRequest_withObjectMapper() {
        final BookDto request = new BookDto("title", "isbn", "author");
        final BookResponseDto response = BookResponseDto.of("isbn", "success message");
        when(bookService.doLogin(any())).thenReturn(response);

        mockMvc.perform(
                        post("/create-book")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(bookService).doLogin(request);
    }

    @Test
    @SneakyThrows
    void shouldHandleCreateBookRequest_withExtraParam() {
        when(bookService.doLogin(any())).thenReturn(BookResponseDto.of("isbn-value", "success-message"));

        mockMvc.perform(
                        post("/create-book")
                                .content(ControllerUnitTest.class.getResourceAsStream("/createBookRequest_withExtraParam.json")
                                        .readAllBytes())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(
                        new String(ControllerUnitTest.class.getResourceAsStream("/createBookResponse.json")
                                .readAllBytes())));

        verify(bookService).doLogin(new BookDto("title", "isbn", "author"));
    }

    @Test
    @SneakyThrows
    void checkAddToRepository() {
        final BookDto request = new BookDto("title", "isbn", "author");
        final BookResponseDto response = BookResponseDto.of("isbn", "success message");
        when(bookService.doLogin(any())).thenReturn(response);

        mockMvc.perform(
                        post("/create-book")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        assertThat(BookRepository.getBooks()).contains(request);
    }
}