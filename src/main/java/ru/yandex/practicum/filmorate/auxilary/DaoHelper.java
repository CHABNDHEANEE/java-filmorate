package ru.yandex.practicum.filmorate.auxilary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.DirectorDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("unused")
@RequiredArgsConstructor
@Component
public class DaoHelper {
    private final RatingDao ratingDao;
    private final GenreDao genreDao;
    private final DirectorDao directorDao;

    public Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getInt("film_id"))
                .name(rs.getString("film_title"))
                .genres(genreDao.getGenresListForFilm(rs.getInt("film_id")))
                .directors(directorDao.getDirectorListForFilm(rs.getInt("film_id")))
                .description(rs.getString("film_description"))
                .releaseDate(rs.getDate("film_release_date").toLocalDate())
                .duration(rs.getInt("film_duration"))
                .mpa(ratingDao.getRatingById(rs.getInt("film_rating_id")))
                .build();
    }

    public User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("user_id"))
                .email(rs.getString("user_email"))
                .login(rs.getString("user_login"))
                .name(rs.getString("user_name"))
                .birthday(rs.getDate("user_birthday").toLocalDate())
                .build();
    }

    public List<FilmGenre> getGenresListForFilm(int filmId) {
        return genreDao.getGenresListForFilm(filmId);
    }

    public List<Director> getDirectorListForFilm(int filmId) {
        return directorDao.getDirectorListForFilm(filmId);
    }

    public void deleteDirectorForFilm(int filmId) {
        directorDao.deleteDirectorForFilm(filmId);
    }
}
