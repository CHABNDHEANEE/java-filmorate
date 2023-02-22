package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("SpellCheckingInspection")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserController userController;
    private User user;

    @BeforeEach
    void beforeEach() {
        user = new User(1, "test@gmail.com", "testLogin", "Name", LocalDate.of(2000, 1, 1));
    }

    @AfterEach
    void afterEach() {
        userController.clearUserList();
    }

    @Test
    void addUser_WithNickAndEmail_AndExpect200() {
        ResponseEntity<User> response = restTemplate.postForEntity("/user", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void addUser_WithoutNickAndWithEmail_AndExpect200() {
        user.setName("");
        ResponseEntity<User> response = restTemplate.postForEntity("/user", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getName(), is(user.getLogin()));
    }

    @Test
    void addUser_WithoutLogin_AndExpect500() {
        user.setLogin("");
        ResponseEntity<User> response = restTemplate.postForEntity("/user", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    void addUser_WithoutMail_AndExpect500() {
        user.setEmail("");
        ResponseEntity<User> response = restTemplate.postForEntity("/user", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    void addUser_WithWrongEmail_AndExpect500() {
        user.setEmail("testgmail.com");
        ResponseEntity<User> response = restTemplate.postForEntity("/user", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    void updateUser_AndExpect200() {
        ResponseEntity<User> response = restTemplate.postForEntity("/user", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        user.setName("changedName");

        response = restTemplate.postForEntity("/user", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getName(), is("changedName"));
    }

    @Test
    void getAllUsers_AndExpect200() {
        restTemplate.postForEntity("/user", user, User.class);
        ResponseEntity<User[]> response = restTemplate.getForEntity("/users", User[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new User[]{user}));
    }

    @Test
    void getAllUsers_EmptyList_AndExpect500() {
        ResponseEntity<User> response = restTemplate.getForEntity("/users", User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
