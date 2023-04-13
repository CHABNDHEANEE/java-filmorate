package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDao genreDao;

    public List<FilmGenre> getGenresList() {
        return genreDao.getGenresList();
    }

    public FilmGenre getGenreById(int genreId) {
        return genreDao.getGenreById(genreId);
    }
}
