package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorDbService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DirectorDaoTest {
    private final DirectorDbService directorService;

    private final Director director1 = new Director(1, "First Director");
    private final Director director2 = new Director(2, "Second Director");
    private final Director director3 = new Director(3, "Third Director");

    @BeforeEach
    void beforeEach() {
        directorService.addDirector(director1);
        directorService.addDirector(director2);
    }

    @Test
    public void testAddFilm() {
        Director result1 = directorService.addDirector(director3);

        checkDirector(result1, director3);
    }

    @Test
    public void testUpdateFilm() {
        Director updatedDirector = director1;
        updatedDirector.setName("Updated Name");

        Director result = directorService.updateDirector(updatedDirector);

        checkDirector(result, updatedDirector);
    }

    @Test
    public void testGetFilmById() {
        checkDirector(directorService.getDirectorById(1), director1);
        checkDirector(directorService.getDirectorById(2), director2);
    }

    private void checkDirector(Director result, Director expected) {
        assertThat(result.getId(), is(expected.getId()));
        assertThat(result.getName(), is(expected.getName()));
    }
}
