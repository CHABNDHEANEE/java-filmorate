package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.FilmRating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserDbService;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SuppressWarnings("SpellCheckingInspection")
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
	private final UserDbService userService;

	private Film film;
	private User user;
	private FilmGenre genre;
	private FilmRating mpa;

	@BeforeEach
	void beforeEach() {
		genre = new FilmGenre(1);
		mpa = new FilmRating(1);
		film = new Film(1, "God Father", List.of(genre), "Film about father",
				LocalDate.now(), 240, mpa);
		user = new User(1, "test@gmail.com", "testLogin", "Name", LocalDate.of(2000, 1, 1));
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void testFindUserById() {
		userService.addUser(user);
		User user = userService.getUserById(1);

		assertThat(user.getId(), is(1));
	}
}
