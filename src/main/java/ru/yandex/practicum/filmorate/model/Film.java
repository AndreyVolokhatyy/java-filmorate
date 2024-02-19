package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validators.MinDate;
import ru.yandex.practicum.filmorate.validators.MinDuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Film.
 */

@Data
@Slf4j
public class Film {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;

    @NotNull
    @NotBlank
    private String name;

    @Length(max = 200, message = "Too long description. Max 200 chars")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    @MinDate
    private LocalDateTime releaseDate;

    @MinDuration
    private Duration duration;

    public Film() {
    }

    public Film(String name, String description, LocalDateTime releaseDate, Duration duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
