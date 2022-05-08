package com.example.demo.DB;

import com.example.demo.domain.entities.UserEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
    @NotEmpty(message = "Title can't be empty")
    private String title;

    @Id
    @Column(name = "isbn")
    @NotEmpty(message = "ISBN can't be empty")
    @Pattern(regexp = "\\d{13}", message = "ISBN has bad format")
    private String isbn;

    @Column(name = "author")
    @NotEmpty(message = "Author can't be empty")
    private String author;

//    @ManyToMany(mappedBy = "favourites")
//    private List<UserEntity> users;

}