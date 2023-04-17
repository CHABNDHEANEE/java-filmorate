package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.model.FilmRating;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingDao ratingDao;

    public List<FilmRating> getRatingList() {
        return ratingDao.getRatingList();
    }

    public FilmRating getRatingById(int ratingId) {
        return ratingDao.getRatingById(ratingId);
    }
}
