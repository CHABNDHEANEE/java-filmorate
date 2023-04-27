package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.dao.ReviewDao;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.FilmDbService;
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
public class ReviewDaoTest {
    private final FilmDbService filmService;
    private final UserDbService userService;
    private final ReviewService reviewService;
    private final FilmGenre genre = new FilmGenre(1);
    private final FilmRating mpa = new FilmRating(1);
    private final Film film1 = new Film(1, "God Father", List.of(genre), "Film about father",
            LocalDate.now(), 240, mpa);
    private final Film film2 = new Film(2, "God Father2", List.of(genre), "Film about father2",
            LocalDate.now(), 240, mpa);
    private final User user1 = new User(1, "test@gmail.com", "testLogin", "Name", LocalDate.of(2000, 1, 1));
    private final Review review1 = new Review(1, "review content1", true, 1, 1, 0);
    private final Review review2 = new Review(2, "review content2", true, 1, 2, 0);
    private final Review review3 = new Review(3, "review content3", false, 1, 2, 0);


    @BeforeEach
    void beforeEach() {
        filmService.addFilm(film1);
        filmService.addFilm(film2);
        userService.addUser(user1);

        reviewService.addReview(review1);
        reviewService.addReview(review2);
    }

    @Test
    public void testAddReview() {
        Review result = reviewService.addReview(review3);
        checkReview(review3, result);
    }

    @Test
    public void getReviewById() {
        Review result = reviewService.getReviewById(1);
        checkReview(result, result);
    }

    @Test
    public void testUpdateReview() {
        review1.setContent("new content");
        Review result = reviewService.updateReview(review1);
        checkReview(review1, result);
    }

    @Test
    public void testGetReviewList_WithoutLimit_WithoutFilm() {
        List<Review> result = reviewService.getReviewList(-1, 10);
        assertThat(result.size(), is(2));
        checkReview(review1, result.get(0));
        checkReview(review2, result.get(1));
    }

    @Test
    public void testGetReviewList_WithLimit_WithFilm() {
        reviewService.addReview(review3);

        List<Review> result = reviewService.getReviewList(2, 1);

        assertThat(result.size(), is(1));
        checkReview(review2, result.get(0));
    }

    @Test
    public void testDeleteReview() {
        reviewService.deleteReview(1);
        List<Review> result = reviewService.getReviewList(-1, 10);
        assertThat(result.size(), is(1));
        checkReview(review2, result.get(0));
    }

    private void checkReview(Review expect, Review result) {
        assertThat(expect.getReviewId(), is(result.getReviewId()));
        assertThat(expect.getContent(), is(result.getContent()));
        assertThat(expect.getUserId(), is(result.getUserId()));
        assertThat(expect.getFilmId(), is(result.getFilmId()));
        assertThat(expect.getUseful(), is(result.getUseful()));
        assertThat(expect.getIsPositive(), is(result.getIsPositive()));
    }
}
