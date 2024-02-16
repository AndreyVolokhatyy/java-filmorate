package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.validators.NotIncludeSpace;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime birthday;

    public User(String email, String login, String name, LocalDateTime birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User() {
    }
}
