package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

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
    private User user;

    @BeforeEach
    void beforeEach() {
        film = new Film(1, "God Father", 1, "Film about father",
                LocalDate.now(), 240, 1);
        film2 = new Film(2, "God Father", 1, "Film about father",
                LocalDate.now(), 240, 1);
        user = new User(1, "test@gmail.com", "testLogin", "Name", LocalDate.of(2000, 1, 1));
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