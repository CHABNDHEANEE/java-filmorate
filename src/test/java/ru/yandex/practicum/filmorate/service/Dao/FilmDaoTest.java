package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.FilmRating;
import ru.yandex.practicum.filmorate.service.FilmDbService;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FilmDaoTest {
    private final FilmDbService filmService;
    private final FilmGenre genre = new FilmGenre(1);
    private final FilmRating mpa = new FilmRating(1);
    private final Film film1 = new Film(1, "God Father", List.of(genre), "Film about father",
            LocalDate.now(), 240, mpa);
    private final Film film2 = new Film(2, "God Father2", List.of(genre), "Film about father2",
            LocalDate.now(), 240, mpa);
    private final Film film3 = new Film(3, "God Father3", List.of(genre), "Film about father3",
            LocalDate.now(), 240, mpa);
    private final Film film4 = new Film(4, "God Father4", List.of(genre), "Film about father4",
            LocalDate.now(), 240, mpa);

    @BeforeEach
    void beforeEach() {
        filmService.addFilm(film1);
        filmService.addFilm(film2);
        filmService.addFilm(film3);
    }

    @Test
    public void testAddFilm() {
        Film result1 = filmService.addFilm(film4);

        checkFilm(result1, film4);
    }

    @Test
    public void testUpdateFilm() {
        Film updatedFilm = film1;
        updatedFilm.setName("Updated Name");

        Film result = filmService.updateFilm(updatedFilm);

        checkFilm(result, updatedFilm);
    }

    @Test
    public void testGetFilmById() {
        checkFilm(filmService.getFilmById(1), film1);
        checkFilm(filmService.getFilmById(2), film2);
        checkFilm(filmService.getFilmById(3), film3);
    }

    @Test
    public void testGetFilmsListWithLimit() {
        List<Film> result = filmService.getFilmsList(2);

        assertThat(result.size(), is(2));
        checkFilm(result.get(0), film1);
        checkFilm(result.get(1), film2);
    }

    private void checkFilm(Film result, Film expected) {
        assertThat(result.getId(), is(expected.getId()));
        assertThat(result.getName(), is(expected.getName()));
        assertThat(result.getMpa(), is(expected.getMpa()));
        assertThat(result.getDescription(), is(expected.getDescription()));
        assertThat(result.getGenres(), is(expected.getGenres()));
        assertThat(result.getReleaseDate(), is(expected.getReleaseDate()));
        assertThat(result.getDuration(), is(expected.getDuration()));
    }
}
