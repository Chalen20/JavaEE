package com.example.demo.DB;

import com.example.demo.exeption.UserAlreadyExistAuthenticationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handle(final MethodArgumentNotValidException ex) {
        final BindingResult bindingResult = ex.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        final List<String> errors = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest()
                .body(errors);
    }

    @ExceptionHandler(BindException.class)
    public String handle(final BindException ex) {
        return "redirect:/register-user?validationError";
    }

    @ExceptionHandler(UserAlreadyExistAuthenticationException.class)
    public String handle(final UserAlreadyExistAuthenticationException ex) {
        return "redirect:/register-user?userExistsError";
    }

}
