package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserDbService;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SuppressWarnings("SpellCheckingInspection")
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
	private final UserDbService userService;
	private User user;

	@BeforeEach
	void beforeEach() {
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
