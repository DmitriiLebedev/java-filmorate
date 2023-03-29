package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.DateValidator;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
public class Film {

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
}
