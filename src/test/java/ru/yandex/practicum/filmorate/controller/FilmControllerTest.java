package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


@SuppressWarnings("unused")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private Film film;

    @Autowired
    private FilmController controller;

    @BeforeEach
    void beforeEach() {
        film = new Film(1, "God Father", 1, "Film about father",
                LocalDate.now(), 240, 1);
    }

    @AfterEach
    void afterEach() {
//        controller.clearFilmsList();
    }

    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    public void getEmptyFilms_AndExpect200() {
        ResponseEntity<Film[]> response = restTemplate.getForEntity("/films", Film[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new Film[]{}));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void addFilm_AndExpect200() {
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()).getId(), notNullValue());
        assertThat(response.getBody(), is(film));
    }

    @Test
    public void addFilm_WithBlankName_AndExpect400() {
        film.setTitle("       ");
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void addFilm_WithDescriptionLongerThan200Chars_AndExpect400() {
        String desc = "aa".repeat(101);
        film.setDescription(desc);
        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void addFilm_WithTooEarlyReleaseDate_AndExpect400() {
        film.setReleaseDate(LocalDate.of(1800, 1, 1));

        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void addFilm_WithNegativeDuration_AndExpect400() {
        film.setDuration(-10);

        ResponseEntity<Film> response = restTemplate.postForEntity("/films", film, Film.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getFilm_AndExpect200() {
        restTemplate.postForEntity("/films", film, String.class);

        ResponseEntity<Film[]> response = restTemplate.getForEntity("/films", Film[].class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody()[0], is(film));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void updateFilm_AndExpect200() {
        restTemplate.postForEntity("/films", film, Film.class);

        film.setTitle("God Son");

        restTemplate.put("/films", film);

        ResponseEntity<Film[]> response = restTemplate.getForEntity("/films", Film[].class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody())[0].getTitle(), is("God Son"));
    }
}
