package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Dao.LikeDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDao likeDao;

    public void like(int userId, int filmId) {
        likeDao.like(userId, filmId);
    }

    public void unlike(int userId, int filmId) {
        likeDao.unlike(userId, filmId);
    }

    public List<Film> getMostPopularFilms(int filmsCount) {
        return likeDao.getMostPopularFilms(filmsCount);
    }
}
