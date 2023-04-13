package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.DateValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class Film {
    @JsonIgnore
    Set<Integer> users = new HashSet<>();
    private int id;
    @NotNull(message = "Name can't be null")
    @NotBlank(message = "Name can't be empty")
    private String name;
    @Size(max = 200, message = "Description can't be longer than 200 characters")
    private String description;
    @DateValidator
    private LocalDate releaseDate;
    @Positive(message = "Duration must be positive")
    private int duration;

    public int getUsersSize() {
        return users.size();
    }

}
