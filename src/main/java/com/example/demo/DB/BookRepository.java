package com.example.demo.DB;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, String> {

    Page<BookEntity> findAllByIsbnLikeOrTitleLike(String isbn, String title, Pageable pageable);

    boolean existsByIsbn(String isbn);
}
