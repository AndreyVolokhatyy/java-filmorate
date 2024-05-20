package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.RateMPA;
import ru.yandex.practicum.filmorate.storage.rate.RateMPAStorage;

import java.util.List;

@Service
public class MPAService {

    private RateMPAStorage rateMPADao;

    public MPAService(RateMPAStorage rateMPADao) {
        this.rateMPADao = rateMPADao;
    }

    public List<RateMPA> getListMPA() {
        return rateMPADao.findRates();
    }

    public RateMPA getMPA(int id) {
        return rateMPADao.findRateById(id);
    }
}
