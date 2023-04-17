package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDao likeDao;
    private final UserDao userDao;
    private final FilmDao filmDao;

    public void like(int filmId, int userId) {
        checkExistenceOfUserAndFilm(filmId, userId);

        likeDao.like(userId, filmId);
    }

    public void unlike(int filmId, int userId) {
        checkExistenceOfUserAndFilm(filmId, userId);

        likeDao.unlike(userId, filmId);
    }

    public List<Film> getMostPopularFilms(Integer filmsCount) {
        return likeDao.getMostPopularFilms(filmsCount);
    }

    private void checkExistenceOfUserAndFilm(int filmId, int userId) {
        userDao.getUserById(userId);
        filmDao.getFilmById(filmId);
    }
}
