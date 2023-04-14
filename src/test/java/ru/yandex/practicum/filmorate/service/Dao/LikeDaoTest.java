package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.FilmRating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmDbService;
import ru.yandex.practicum.filmorate.service.LikeService;
import ru.yandex.practicum.filmorate.service.UserDbService;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LikeDaoTest {
    private final FilmDbService filmService;
    private final UserDbService userService;
    private final LikeService likeService;
    private final FilmGenre genre = new FilmGenre(1);
    private final FilmRating mpa = new FilmRating(1);
    private final Film film1 = new Film(1, "God Father", List.of(genre), "Film about father",
            LocalDate.now(), 240, mpa);
    private final Film film2 = new Film(2, "God Father2", List.of(genre), "Film about father2",
            LocalDate.now(), 240, mpa);
    private final Film film3 = new Film(3, "God Father3", List.of(genre), "Film about father3",
            LocalDate.now(), 240, mpa);
    private final User user1 = new User(1, "test@gmail.com", "testLogin", "Name", LocalDate.of(2000, 1, 1));
    private final User user2 = new User(2, "test@gmail.com", "testLogin", "Name", LocalDate.of(2000, 1, 1));

    @Test
    public void testLikeFilm() {
        filmService.addFilm(film1);
        filmService.addFilm(film2);
        filmService.addFilm(film3);
        userService.addUser(user1);
        userService.addUser(user2);

        likeService.like(film1.getId(), user1.getId());

        likeService.like(film3.getId(), user2.getId());
        likeService.like(film3.getId(), user1.getId());

    }

    @Test
    public void testUnlikeFilm() {
        testLikeFilm();

        likeService.unlike(film3.getId(), user2.getId());
        likeService.unlike(film3.getId(), user1.getId());

        List<Film> result = likeService.getMostPopularFilms(2);

        assertThat(result.get(0), is(film1));
        assertThat(result.get(1), is(film3));
    }

    @Test
    public void testGetMostLikedFilmsWithLimit() {
        testLikeFilm();

        List<Film> result = likeService.getMostPopularFilms(2);

        assertThat(result.get(0), is(film3));
        assertThat(result.get(1), is(film1));
    }
}
