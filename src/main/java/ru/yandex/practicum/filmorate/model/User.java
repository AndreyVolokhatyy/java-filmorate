package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validators.NotIncludeSpace;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Slf4j
public class User {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @NotNull
    @NotIncludeSpace
    private String login;

    private String name;

    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User() {
    }
}
