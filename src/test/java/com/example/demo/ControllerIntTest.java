package com.example.demo;

import static io.restassured.RestAssured.given;

import com.example.demo.dto.BookResponseDto;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerIntTest {

    @LocalServerPort
    void savePort(final int port) {
        // save port of locally starter server during test
        RestAssured.port = port;
    }

    @Test
    void shouldSendRequest() {
        given()
                .body(ControllerUnitTest.class.getResourceAsStream("/createBookRequest.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/create-book")
                .then()
                .body("isbn", CoreMatchers.is("isbn"))
                .body("message", CoreMatchers.is("book created successfully"));
    }

    @Test
    void shouldSendRequest_withExtraParam() {
        given()
                .body(ControllerIntTest.class.getResourceAsStream("/createBookRequest_withExtraParam.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/create-book")
                .then()
                .body("isbn", CoreMatchers.is("isbn"))
                .body("message", CoreMatchers.is("book created successfully"));
    }

    @Test
    void shouldSendRequest_testResponseAsObject() {
        final BookResponseDto responseDto = given()
                .body(ControllerIntTest.class.getResourceAsStream("/createBookRequest.json"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/create-book")
                .then()
                .extract()
                .as(BookResponseDto.class);

        assertThat(responseDto).isEqualTo(BookResponseDto.of("isbn", "book created successfully"));
        // alternative way to test only few fields
        assertThat(responseDto)
                .returns("isbn", BookResponseDto::getIsbn)
                .returns("book created successfully", BookResponseDto::getMessage);
    }
}
