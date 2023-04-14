package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.FilmRating;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RatingDaoTest {
    private final RatingService ratingService;

    @Test
    public void getRatingList() {
        List<FilmRating> ratingList = ratingService.getRatingList();

        checkRating(ratingList.get(0));
        checkRating(ratingList.get(1));
        checkRating(ratingList.get(2));
        checkRating(ratingList.get(3));
        checkRating(ratingList.get(4));
    }

    @Test
    public void getRatingById() {
        checkRating(ratingService.getRatingById(1));
    }

    private void checkRating(FilmRating rating) {
        int id = rating.getId();
        String name = rating.getName();
        String expectedName;

        switch (id) {
            case 1:
                expectedName = "G";
                break;
            case 2:
                expectedName = "PG";
                break;
            case 3:
                expectedName = "PG-13";
                break;
            case 4:
                expectedName = "R";
                break;
            case 5:
                expectedName = "NC-17";
                break;
            default:
                expectedName = "";
        }

        assertThat(name, is(expectedName));
    }
}
