package ru.yandex.practicum.filmorate.storage.rate;

import ru.yandex.practicum.filmorate.model.RateMPA;

import java.util.List;

public interface RateMPAStorage {

    List<RateMPA> findRates();

    RateMPA findRateById(int id);
}
