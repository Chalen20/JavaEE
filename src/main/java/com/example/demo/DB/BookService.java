package com.example.demo.DB;

import com.example.demo.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final EntityManager entityManager;

    @Transactional
    public BookEntity createBook(String author, String title, String isbn) {
        BookEntity user = new BookEntity();
        user.setAuthor(author);
        user.setTitle(title);
        user.setIsbn(isbn);

        return entityManager.merge(user);
    }

//    @Transactional
//    public BookEntity createBook(BookDto bookDto) {
//        BookEntity user = new BookEntity();
//        user.setAuthor(bookDto.getAuthor());
//        user.setTitle(bookDto.getTitle());
//        user.setIsbn(bookDto.getIsbn());
//
//        return entityManager.merge(user);
//    }

    @Transactional
    public List<BookEntity> findAllBooks() {
        return entityManager.createQuery("SELECT u FROM BookEntity u", BookEntity.class)
                .getResultList();
    }


    @Transactional
    public BookEntity getBookByIsbn(String isbn) {
        return entityManager.find(BookEntity.class, isbn);
    }

    @Transactional
    public List<BookEntity> findBookWhereAuthorOrTitleOrISBNContains(String searchText) {
        return entityManager.createQuery("SELECT u FROM BookEntity u WHERE u.author LIKE :query OR" +
                        " u.title LIKE :query OR" +
                        " u.isbn LIKE :query", BookEntity.class)
                .setParameter("query", '%' + searchText + '%')
                .getResultList();
    }
}