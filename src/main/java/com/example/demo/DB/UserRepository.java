package com.example.demo.DB;

import java.util.Optional;

import com.example.demo.domain.entities.UserEntity;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT user FROM UserEntity user "
        + "LEFT JOIN FETCH user.permissions "
        + "WHERE user.login = :login")
    Optional<UserEntity> findByLogin(@Param("login") String login);

    @Query("SELECT user FROM UserEntity user "
            + "LEFT JOIN FETCH user.favourites "
            + "WHERE user.login = :login")
    Optional<UserEntity> findFavouritesByLogin(@Param("login") String login);

    boolean existsByLogin(String login);

//    @SQLInsert(sql = "INSERT INTO user_to_book (user_id, book_id)" +
//            " values (:user_id, :book_id)")
//    void addBookToFavourites(@Param("user_id") int user_id, @Param("book_id") String isbn);
}
