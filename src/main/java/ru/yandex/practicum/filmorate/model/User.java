package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validators.NotIncludeSpace;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
@AllArgsConstructor
public class User {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;
    @NotBlank
    @NotNull
    @NotIncludeSpace
    private String login;
    private String name;
    @NotBlank
    @Email
    private String email;
    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Set<Friend> friends;
    private boolean isActive;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        friends = new HashSet<>();
    }

    public User() {
        friends = new HashSet<>();
    }
}