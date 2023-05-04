package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.FilmRating;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbServiceTest {
    private final FilmDbService filmService;
    private Film film;
    private Film film2;

    @BeforeEach
    void beforeEach() {
        FilmGenre genre = new FilmGenre(1);
        FilmRating mpa = new FilmRating(1);
        film = new Film(1, "God Father", List.of(genre), "Film about father",
                LocalDate.now(), 240, mpa, null);
        film2 = new Film(2, "God Father", List.of(genre), "Film about father",
                LocalDate.now(), 240, mpa, null);
    }

    @Test
    void testFilmAddition() {
        filmService.addFilm(film);
    }

    @Test
    void testGetFilmById() {
        testFilmAddition();

        Film result = filmService.getFilmById(1);

        assertThat(result.getId(), is(1));
    }

    @Test
    void testGetFilmsList() {
        List<Film> expectedList = new ArrayList<>();
        expectedList.add(film);
        expectedList.add(film2);

        filmService.addFilm(film);
        filmService.addFilm(film2);

        List<Film> result = filmService.getFilmsList(10);

        assertThat(result.get(0), is(expectedList.get(0)));
        assertThat(result.get(1), is(expectedList.get(1)));
    }
}
