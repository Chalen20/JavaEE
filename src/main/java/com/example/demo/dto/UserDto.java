package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Length(max = 255, message = "Login`s length must be <=30")
    @NotEmpty(message = "Login can't be empty")
    @Pattern(regexp = "[a-zA-Z\\d]+", message = "Login must consists of latin letters and numbers")
    private String login;

    @NotEmpty(message = "Password can't be empty")
    @Pattern(regexp = ".{8,20}", message = "Password must have 8-20 symbols")
    private String password;
}
