package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class User {
    @JsonIgnore
    Set<Integer> friends = new HashSet<>();
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
