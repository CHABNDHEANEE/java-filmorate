package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.DirectorDbService;
import ru.yandex.practicum.filmorate.service.FilmDbService;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.service.UserDbService;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FilmDaoTest {
    private final FilmDbService filmService;

    private final LikeService likeService;

    private final UserDbService userDbService;
    private final FilmGenre genre = new FilmGenre(1);
    private final FilmRating mpa = new FilmRating(1);
    private final DirectorDbService directorService;
    private final Director director1 = new Director(1, "Father's Director");

    private final Film film1 = new Film(1, "God Father", List.of(genre), "Film about father",
            LocalDate.now(), 240, mpa, null);
    private final Film film2 = new Film(2, "God Father2", List.of(genre), "Film about father2",
            LocalDate.now(), 240, mpa, null);
    private final Film film3 = new Film(3, "God Father3", List.of(genre), "Film about father3",
            LocalDate.now(), 240, mpa, null);
    private final Film film4 = new Film(4, "God Father4", List.of(genre), "Film about father4",
            LocalDate.now(), 240, mpa, null);
    private final Film film5 = new Film(5, "God Father5", List.of(genre), "Film about father5",
            LocalDate.now(), 240, mpa, List.of(director1));

    private final User user1 = User
            .builder()
            .name("Mike")
            .login("mike")
            .email("mike@mail.ru")
            .birthday(LocalDate.now())
            .build();

    private final User user2 = User.builder()
            .name("Vasya")
            .login("mike")
            .email("vs@mail.ru")
            .birthday(LocalDate.now())
            .build();

    @BeforeEach
    void beforeEach() {
        directorService.addDirector(director1);
        filmService.addFilm(film1);
        filmService.addFilm(film2);
        filmService.addFilm(film3);
        userDbService.addUser(user1);
        userDbService.addUser(user2);
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

    @Test
    public void shouldRemoveFilm1WhenUseMethodDelete() {
        List<Film> result = filmService.getFilmsList(10);
        Film film = filmService.addFilm(film4);
        filmService.deleteFilm(film.getId());
        List<Film> result2 = filmService.getFilmsList(10);

        assertThat(result2.size(), is(result.size()));
    }

    @Test
    public void shouldRemoveFilm1WhenFilmContainsLikes() {
        List<Film> result = filmService.getFilmsList(10);
        Film film = filmService.addFilm(film4);
        likeService.like(film.getId(), user1.getId());
        filmService.deleteFilm(film.getId());
        List<Film> result2 = filmService.getFilmsList(10);

        assertThat(result2.size(), is(result.size()));
    }

    @Test
    public void commonFilms() {
        likeService.like(film1.getId(), 1);
        likeService.like(film1.getId(), 2);

        List<Film> expected = List.of(film1);

        List<Film> result = filmService.getCommonFilms(1, 2);

        assertThat(result.size(), is(expected.size()));
        assertThat(result.get(0), is(expected.get(0)));
        assertThat(result.get(0).getName(), is(film1.getName()));
    }

    @Test
    public void testGetFilmsByTitle() {
        likeService.like(film1.getId(), 2);
        likeService.like(film2.getId(), 1);
        likeService.like(film2.getId(), 2);

        List<Film> films = filmService.getFilmsSearchByTitle("god");

        assertThat(films.size(), is(3));
        assertThat(films.get(0), is(film2));
        assertThat(films.get(1), is(film1));
    }

    @Test
    public void testGetFilmsByByDirectorAndTitle() {
        filmService.addFilm(film4);
        filmService.addFilm(film5);

        likeService.like(film1.getId(), 1);
        likeService.like(film5.getId(), 1);
        likeService.like(film5.getId(), 2);

        List<Film> films = filmService.getFilmsSearchByDirectorAndTitle("fatHer");

        assertThat(films.size(), is(5));
        assertThat(films.get(0), is(film5));
        assertThat(films.get(1), is(film1));
    }

    @Test
    public void testGetFilmsByDirector() {
        filmService.addFilm(film4);
        filmService.addFilm(film5);

        likeService.like(film5.getId(), 1);
        likeService.like(film4.getId(), 1);
        likeService.like(film1.getId(), 1);
        likeService.like(film1.getId(), 2);

        List<Film> films = filmService.getFilmsSearchByDirector("fatHer");

        assertThat(films.size(), is(1));
        assertThat(films.get(0), is(film5));
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
