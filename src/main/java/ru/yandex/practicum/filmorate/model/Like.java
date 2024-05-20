package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Like {

    @NotNull
    private int id;
    @NotNull
    private int filmId;
    @NotNull
    private int userId;
    @NotNull
    private boolean isActive;
}
