package com.example.demo;

import com.example.demo.DB.BookEntity;
import com.example.demo.DB.BookService;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

public class BookServiceTest extends AbstractTest {

    @Autowired
    private BookService service;

    @Test
    @Sql("/BookService/init.sql")
    @Sql(value = "/BookService/clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldSelectBookByIsbn() {
        assertThat(service.getBookByIsbn("isbn1"))
                .returns("isbn1", BookEntity::getIsbn)
                .returns("title1", BookEntity::getTitle)
                .returns("author1", BookEntity::getAuthor);
    }

    @Test
    @Sql("/BookService/init.sql")
    @Sql(value = "/BookService/clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldSelectAllBooks() {
        assertThat(service.findAllBooks())
                .hasSize(3);
    }

    @Test
    @Sql("/BookService/init.sql")
    @Sql(value = "/BookService/clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldCreateBook() {
        service.createBook("newAuthor", "newTitle", "newISBN");
        assertThat(service.findAllBooks())
                .hasSize(4);
    }

    @Test
    @Sql("/BookService/init.sql")
    @Sql(value = "/BookService/clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldFindBookWhereAuthorOrTitleOrISBNContains1() {
        assertThat(service.findBookWhereAuthorOrTitleOrISBNContains("1"))
                .hasSize(1);
    }

    @Test
    @Sql("/BookService/init.sql")
    @Sql(value = "/BookService/clean-up.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldFindBookWhereAuthorOrTitleOrISBNContains3() {
        assertThat(service.findBookWhereAuthorOrTitleOrISBNContains("isbn"))
                .hasSize(3);
    }

    @Test
    @DatabaseSetup("/BookService/init.xml")
    @DatabaseTearDown("/BookService/clean-up.xml")
    void shouldSelectBookByIsbn_dbunit() {
        assertThat(service.getBookByIsbn("isbn1"))
                .returns("isbn1", BookEntity::getIsbn)
                .returns("title1", BookEntity::getTitle)
                .returns("author1", BookEntity::getAuthor);
    }

    @Test
    @DatabaseSetup("/BookService/init.xml")
    @DatabaseTearDown("/BookService/clean-up.xml")
    void shouldSelectAllBooks_dbunit() {
        assertThat(service.findAllBooks())
                .hasSize(3);
    }

    @Test
    @DatabaseSetup("/BookService/init.xml")
    @DatabaseTearDown("/BookService/clean-up.xml")
    @ExpectedDatabase(value = "/BookService/expectedBooksAfterCreateNew.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void shouldCreateBook_dbunit(){
        service.createBook("newAuthor", "newTitle", "newISBN");
    }

}
