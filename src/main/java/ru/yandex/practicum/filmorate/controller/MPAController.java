package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.RateMPA;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.List;

@Validated
@RestController
@Slf4j
public class MPAController {

    private final MPAService mpaService;

    public MPAController(MPAService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/mpa")
    public List<RateMPA> listGenre() {
        return mpaService.getListMPA();
    }

    @GetMapping("/mpa/{id}")
    public RateMPA getRateMPA(@PathVariable int id) {
        return mpaService.getMPA(id);
    }
}
