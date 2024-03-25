package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validators.MinDate;
import ru.yandex.practicum.filmorate.validators.MinDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */

@Data
@Slf4j
@AllArgsConstructor
public class Film {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;
    @NotNull
    @NotBlank
    private String name;
    @Length(max = 200, message = "Too long description. Max 200 chars")
    private String description;
    private Set<Genre> genres;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @MinDate
    private LocalDate releaseDate;
    @MinDuration
    private Integer duration;
    private Set<Integer> likes;
    private RateMPA mpa;
    private int rate;
    private boolean isActive;

    public Film() {
        genres = new HashSet<>();
        likes = new HashSet<>();
    }

    public Film(String name, String description, LocalDate releaseDate, Integer duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        genres = new HashSet<>();
        likes = new HashSet<>();
    }
}
