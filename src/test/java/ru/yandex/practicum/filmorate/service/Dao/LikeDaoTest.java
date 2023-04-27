package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LikeDaoTest {
    private final JdbcTemplate jdbcTemplate;
    private final FilmDbService filmService;
    private final UserDbService userService;
    private final LikeService likeService;
    private final FilmGenre genre1 = new FilmGenre(1);
    private final FilmGenre genre2 = new FilmGenre(2);
    private final FilmRating mpa = new FilmRating(1);
    private final Film film1 = new Film(1, "God Father", List.of(genre1), "Film about father",
            LocalDate.of(2022, 10, 5), 240, mpa, List.of());
    private final Film film2 = new Film(2, "God Father2", List.of(genre2), "Film about father2",
            LocalDate.of(2020, 10, 5), 240, mpa, List.of());
    private final Film film3 = new Film(3, "God Father3", List.of(genre1), "Film about father3",
            LocalDate.of(2022, 8, 5), 240, mpa, List.of());
    private final User user1 = new User(1, "test@gmail.com", "testLogin", "Name",
            LocalDate.of(2000, 1, 1));
    private final User user2 = new User(2, "test@gmail.com", "testLogin", "Name",
            LocalDate.of(2000, 1, 1));

    @AfterEach
    public void clean() {
        jdbcTemplate.update("DELETE FROM users_liked_films");
    }

    @Test
    public void likeFilm_correctListFilms_threeLikesAdded() {
        createUsersAndFilms();

        likeService.like(film1.getId(), user1.getId());
        likeService.like(film3.getId(), user2.getId());
        likeService.like(film3.getId(), user1.getId());

        List<Film> result = likeService.getMostPopularFilms(null, null, null);

        assertAll(
                () -> assertThat(result.isEmpty(), is(false)),
                () -> assertThat(result.size(), is(3)),
                () -> assertThat(result.contains(film1), is(true))
        );
    }

    @Test
    public void unlikeFilm_correctListFilms_treeLikesAddedAndOneLikeCansel() {
        createUsersAndFilms();

        likeService.like(film1.getId(), user1.getId());
        likeService.like(film2.getId(), user1.getId());
        likeService.like(film3.getId(), user1.getId());
        likeService.unlike(film3.getId(), user1.getId());

        List<Film> result = likeService.getMostPopularFilms(2, null, null);

        System.out.println(result);

        assertAll(
                () -> assertThat(result.isEmpty(), is(false)),
                () -> assertThat(result.contains(film3), is(false))
        );
    }

    @Test
    public void findMostPopularFilmsWithLimit() {
        createUsersAndFilms();

        likeService.like(film1.getId(), user1.getId());
        likeService.like(film2.getId(), user1.getId());
        likeService.like(film1.getId(), user2.getId());
        likeService.like(film2.getId(), user2.getId());
        likeService.like(film3.getId(), user2.getId());

        List<Film> result = likeService.getMostPopularFilms(2, null, null);

        assertAll(
                () -> assertThat(result.isEmpty(), is(false)),
                () -> assertThat(result.size(), is(2)),
                () -> assertThat(result.contains(film1), is(true)),
                () -> assertThat(result.contains(film2), is(true))
        );
    }


    @Test
    @DisplayName("findMostPopularFilms | empty list films | user likes no added")
    void findMostPopularFilms_emptyList_noAddedLikes() {
        List<Film> result = likeService.getMostPopularFilms(null, null, null);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    @DisplayName("findMostPopularFilms | correct list with films | user likes added, all request parameter is null")
    void findMostPopularFilms_correctListFilms_addedLikesAndAllParameterNull() {
        createUsersAndFilms();

        likeService.like(film1.getId(), user1.getId());

        List<Film> result = likeService.getMostPopularFilms(null, null, null);

        assertThat(result.size(), is(3));
    }

    @Test
    @DisplayName("findMostPopularFilms | correct list with films | user likes added, parameter 'genreId' is null")
    void findMostPopularFilms_correctListFilms_addedLikesAndGenreIdNull() {
        createUsersAndFilms();

        likeService.like(film1.getId(), user1.getId());

        List<Film> result = likeService.getMostPopularFilms(2, null, "2022");

        assertThat(result.size(), is(2));
        assertThat(result.contains(film1), is(true));
        assertThat(result.contains(film3), is(true));
    }

    @Test
    @DisplayName("findMostPopularFilms | correct list with films | user likes added, parameter 'year' is null")
    void findMostPopularFilms_correctListFilms_addedLikesAndYearNull() {
        createUsersAndFilms();

        likeService.like(film1.getId(), user1.getId());

        List<Film> result = likeService.getMostPopularFilms(2, 1, null);

        assertThat(result.size(), is(2));
        assertThat(result.get(0), is(film1));
    }

    @Test
    @DisplayName("findMostPopularFilms | correct list with films | user likes added, all parameter is not null")
    void findMostPopularFilms_correctListFilms_addedLikesYearNull() {
        createUsersAndFilms();

        likeService.like(film1.getId(), user1.getId());

        List<Film> result = likeService.getMostPopularFilms(2, 2, "2020");

        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(film2));
    }

    @Test
    @DisplayName("findMostPopularFilms | empty list with films | user likes added, all parameter is not null")
    void findMostPopularFilms_emptyListFilms_addedLikesYearNull() {
        createUsersAndFilms();

        likeService.like(film1.getId(), user1.getId());

        List<Film> result = likeService.getMostPopularFilms(10, 4, "2018");

        assertThat(result.isEmpty(), is(true));
    }

    private void createUsersAndFilms() {
        filmService.addFilm(film1);
        filmService.addFilm(film2);
        filmService.addFilm(film3);

        userService.addUser(user1);
        userService.addUser(user2);
    }
}
