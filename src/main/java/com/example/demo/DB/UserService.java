package com.example.demo.DB;

import com.example.demo.domain.entities.UserEntity;
import com.example.demo.exeption.UserAlreadyExistAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Optional<UserEntity> findFavouriteByLogin(String login) {
        return userRepository.findFavouritesByLogin(login);
    }

    @Transactional
    public UserEntity registerUser(String login, String password) throws UserAlreadyExistAuthenticationException {
        if (!userRepository.existsByLogin(login)) {
            UserEntity user = UserEntity.builder()
                    .login(login).password(password)
                    .permissions(new ArrayList<>()).build();
            System.out.println(user);
            return userRepository.saveAndFlush(user);
        }
        else {
            throw new UserAlreadyExistAuthenticationException("User already exists");
        }
    }

    @Transactional
    public boolean isUserFavourite(UserEntity user, BookEntity book) {
        Set<BookEntity> books = user.getFavourites();
        //System.out.println(3);
        return books.contains(book);
    }

    @Transactional
    public UserEntity removeFromFavourites(UserEntity user, BookEntity book) {
        Set<BookEntity> books = user.getFavourites();
        books.remove(book);
        user.setFavourites(books);
        //System.out.println(2);
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public UserEntity addToFavourites(UserEntity user, BookEntity book) {
        Set<BookEntity> books = user.getFavourites();
        books.add(book);
        user.setFavourites(books);
        //System.out.println(1);
        return userRepository.saveAndFlush(user);
    }

    public Set<BookEntity> findAllFavouriteBooks(String username) {
        Optional<UserEntity> oUser = userRepository.findFavouritesByLogin(username);
        if (oUser.isPresent()) {
            UserEntity user = oUser.get();
            //System.out.println(4);
            return  user.getFavourites();
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
