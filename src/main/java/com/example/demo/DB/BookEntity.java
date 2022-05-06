package com.example.demo.DB;

import com.example.demo.domain.entities.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookEntity {
    @Column(name = "title")
    private String title;

    @Id
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "author")
    private String author;

//    @ManyToMany(mappedBy = "favourites")
//    private List<UserEntity> users;

}