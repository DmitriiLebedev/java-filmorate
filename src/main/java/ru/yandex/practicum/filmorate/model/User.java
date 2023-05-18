package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private int id;
    @Email(message = "Email must be valid")
    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email can't be empty")
    private String email;
    @Pattern(regexp = "^\\S+$")
    @NotNull(message = "Login can't be null")
    @NotBlank(message = "Login can't be empty")
    private String login;
    private String name;
    @Past(message = "Birthday can't be in future")
    @NotNull(message = "Birthday can't be empty")
    private LocalDate birthday;


}
