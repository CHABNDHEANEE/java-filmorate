package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.service.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FeedDaoTest {

    private final FeedService feedService;
    private final FilmDbService filmService;
    private final UserDbService userService;
    private final LikeService likeService;
    private final ReviewService reviewService;
    private final FilmGenre genre = new FilmGenre(1);
    private final FilmRating mpa = new FilmRating(1);
    private final Film film1 = new Film(1, "God Father", List.of(genre), "Film about father",
            LocalDate.now(), 240, mpa, null);

    private final User user1 = new User(1, "test@gmail.com", "testLogin", "Name", LocalDate.of(2000, 1, 1));
    private final User user2 = new User(2, "test2@gmail.com", "testLogin2", "Name2", LocalDate.of(2020, 1, 1));
    private final Review review1 = new Review(1, "review content1", true, 1, 1, 0);

    @Test
    public void testGetFeed() {
        filmService.addFilm(film1);
        userService.addUser(user1);
        userService.addUser(user2);

        likeService.like(film1.getId(), user1.getId());
        userService.addFriend(user1.getId(), user2.getId());
        reviewService.addReview(review1);

        AssertionsForClassTypes.assertThat(feedService.getFeed(user1.getId()).size())
                .isEqualTo(3);
    }

    @Test
    public void testDoNotGetFeedByWrongId() {
        assertThrows(ObjectExistenceException.class, () -> feedService.getFeed(999));
    }

}
