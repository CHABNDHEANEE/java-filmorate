package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.FilmDbService;
import ru.yandex.practicum.filmorate.service.ReviewLikesService;
import ru.yandex.practicum.filmorate.service.ReviewService;
import ru.yandex.practicum.filmorate.service.UserDbService;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReviewLikesDaoTest {
    private final FilmDbService filmService;
    private final UserDbService userService;
    private final ReviewService reviewService;
    private final ReviewLikesService reviewLikesService;
    private final FilmGenre genre = new FilmGenre(1);
    private final FilmRating mpa = new FilmRating(1);
    private final Film film1 = new Film(1, "God Father", List.of(genre), "Film about father",
            LocalDate.now(), 240, mpa, null);
    private final Film film2 = new Film(2, "God Father2", List.of(genre), "Film about father2",
            LocalDate.now(), 240, mpa, null);
    private final User user1 = new User(1, "test@gmail.com", "testLogin", "Name", LocalDate.of(2000, 1, 1));
    private final User user2 = new User(2, "test2@gmail.com", "testLogin2", "Name2", LocalDate.of(2001, 1, 1));

    private final Review review1 = new Review(1, "review content1", true, 1, 1, 0);
    private final Review review2 = new Review(2, "review content2", true, 1, 2, 0);

    @BeforeEach
    void beforeEach() {
        filmService.addFilm(film1);
        filmService.addFilm(film2);
        userService.addUser(user1);
        userService.addUser(user2);

        reviewService.addReview(review1);
        reviewService.addReview(review2);
    }

    @Test
    public void testLikes() {
        reviewLikesService.like(1, 1);
        assertThat(reviewService.getReviewById(1).getUseful(), is(1));
    }

    @Test
    public void testDeleteLike() {
        reviewLikesService.like(1, 1);
        reviewLikesService.like(1, 2);
        reviewLikesService.deleteLike(1, 2);
        assertThat(reviewService.getReviewById(1).getUseful(), is(1));
    }

    @Test
    public void testDislike() {
        reviewLikesService.like(1, 1);
        reviewLikesService.dislike(1, 2);
        assertThat(reviewService.getReviewById(1).getUseful(), is(0));
    }

    @Test
    public void testDeleteDislike() {
        reviewLikesService.like(1, 1);
        reviewLikesService.dislike(1, 2);
        reviewLikesService.deleteDislike(1, 2);
        assertThat(reviewService.getReviewById(1).getUseful(), is(1));
    }
}
