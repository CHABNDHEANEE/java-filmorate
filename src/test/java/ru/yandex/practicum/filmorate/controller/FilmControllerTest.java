package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FilmController controller;

    @AfterEach
    void afterEach() {
        controller.clearFilmsList();
    }

    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    public void getEmptyFilms_AndExpect500() {
        ResponseEntity<Film> response = restTemplate.getForEntity("/films", Film.class);
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void addFilm_AndExpect200() {
        Film film = new Film(1, "God Father", "Film about father", LocalDate.now(), Duration.ofMinutes(240));

        ResponseEntity<Film> response = restTemplate.postForEntity("/film", film, Film.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody(), is(film));
    }

    @Test
    public void addFilm_WithBlankName_AndExpect500() {
        Film film = new Film(1, "   ", "Film about father", LocalDate.now(), Duration.ofMinutes(240));

        ResponseEntity<Film> response = restTemplate.postForEntity("/film", film, Film.class);

        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void addFilm_WithNameLongerThan200Chars_AndExpect500() {
        String name = new String("aa").repeat(101);
        Film film = new Film(1, name, "Film about father", LocalDate.now(), Duration.ofMinutes(240));

        ResponseEntity<Film> response = restTemplate.postForEntity("/film", film, Film.class);

        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void addFilm_WithTooEarlyReleaseDate_AndExpect500() {
        Film film = new Film(1, "God Father", "Film about father",
                LocalDate.of(1800, 1, 1), Duration.ofMinutes(240));

        ResponseEntity<Film> response = restTemplate.postForEntity("/film", film, Film.class);

        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void addFilm_WithNegativeDuration_AndExpect500() {
        Film film = new Film(1, "God Father", "Film about father",
                LocalDate.of(1800, 1, 1), Duration.ofMinutes(-10));

        ResponseEntity<Film> response = restTemplate.postForEntity("/film", film, Film.class);

        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void getFilm_AndExpect200() {
        Film film = new Film(1, "God Father", "Film about father",
                LocalDate.now(), Duration.ofMinutes(240));

        restTemplate.postForEntity("/film", film, String.class);

        ResponseEntity<Film[]> response = restTemplate.getForEntity("/films", Film[].class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody()[0], is(film));
    }

    @Test
    public void updateFilm_AndExpect200() {
        Film film = new Film(1, "God Father", "Film about father",
                LocalDate.now(), Duration.ofMinutes(240));

        restTemplate.postForEntity("/film", film, Film.class);

        film.setName("God Son");

        restTemplate.put("/film", film);

        ResponseEntity<Film[]> response = restTemplate.getForEntity("/films", Film[].class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
