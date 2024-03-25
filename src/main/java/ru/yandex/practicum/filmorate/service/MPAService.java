package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.RateMPA;
import ru.yandex.practicum.filmorate.storage.rate.RateMPADaoImpl;

import java.util.List;

@Service
public class MPAService {

    @Autowired
    private RateMPADaoImpl rateMPADao;

    public List<RateMPA> getListMPA() {
        return rateMPADao.findRates();
    }

    public RateMPA getMPA(int id) {
        return rateMPADao.findRateById(id);
    }
}
