package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FeedDao;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Instant;
import java.util.List;

import static ru.yandex.practicum.filmorate.model.EventType.LIKE;
import static ru.yandex.practicum.filmorate.model.Operation.ADD;
import static ru.yandex.practicum.filmorate.model.Operation.REMOVE;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDao likeDao;
    private final UserDao userDao;
    private final FilmDao filmDao;
    private final FeedDao feedDao;

    public void like(int filmId, int userId) {
        checkExistenceOfUserAndFilm(filmId, userId);
        likeDao.like(userId, filmId);
        feedDao.addFeed(filmId, userId, Instant.now().toEpochMilli(), LIKE, ADD);
    }

    public void unlike(int filmId, int userId) {
        checkExistenceOfUserAndFilm(filmId, userId);
        likeDao.unlike(userId, filmId);
        feedDao.addFeed(filmId, userId, Instant.now().toEpochMilli(), LIKE, REMOVE);
    }

    public List<Film> getPopularFilms(Integer limit, Integer genreId, String year) {
        return likeDao.findPopularFilms(limit, genreId, year);
    }

    private void checkExistenceOfUserAndFilm(int filmId, int userId) {
        userDao.getUserById(userId);
        filmDao.getFilmById(filmId);
    }
}