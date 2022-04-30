package com.example.demo.DB;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    // private final EntityManager entityManager;
    private final BookRepository bookRepository;

    @Transactional
    public BookEntity createBook(String author, String title, String isbn) {
        BookEntity user = new BookEntity();
        user.setAuthor(author);
        user.setTitle(title);
        user.setIsbn(isbn);

        return bookRepository.saveAndFlush(user);
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
    public Page<BookEntity> findAllBooks(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return bookRepository.findAll(pageable);
    }
    //    public List<BookEntity> findAllBooks() {
//        return entityManager.createQuery("SELECT u FROM BookEntity u", BookEntity.class)
//                .getResultList();
//    }


    @Transactional
    public BookEntity getBookByIsbn(String isbn) {
        Optional<BookEntity> optionalBook = bookRepository.findById(isbn);

        return optionalBook
                .orElse(null);
    }
//    public BookEntity getBookByIsbn(String isbn) {
//        return entityManager.find(BookEntity.class, isbn);
//    }

    @Transactional
    public Page<BookEntity> findBookWhereAuthorOrTitleOrISBNContains(String searchText, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return bookRepository.findAllByIsbnLikeOrTitleLike('%' + searchText + '%', '%' + searchText + '%', pageable);
    }
//    public List<BookEntity> findBookWhereAuthorOrTitleOrISBNContains(String searchText) {
//        return entityManager.createQuery("SELECT u FROM BookEntity u WHERE u.author LIKE :query OR" +
//                        " u.title LIKE :query OR" +
//                        " u.isbn LIKE :query", BookEntity.class)
//                .setParameter("query", '%' + searchText + '%')
//                .getResultList();
//    }
}