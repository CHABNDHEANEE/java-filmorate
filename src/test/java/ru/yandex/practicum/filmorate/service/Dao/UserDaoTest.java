package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserDbService;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDaoTest {
    private final UserDbService userStorage;
    private final User user1 = new User(1, "test@gmail.com", "testLogin", "Name", LocalDate.of(2000, 1, 1));
    private final User user2 = new User(2, "test2@gmail.com", "testLogin2", "Name2", LocalDate.of(2020, 1, 1));
    private final User user3 = new User(3, "test3@gmail.com", "testLogin3", "Name3", LocalDate.of(2021, 1, 1));
    private final User user4 = new User(4, "test4@gmail.com", "testLogin4", "Name4", LocalDate.of(2022, 1, 1));


    @BeforeEach
    void beforeEach() {
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        userStorage.addUser(user3);
    }

    @Test
    public void testAddUsers() {
        User result1 = userStorage.addUser(user4);

        checkUsers(result1, user4);
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = user2;
        updatedUser.setName("UpdatedName");

        User result = userStorage.updateUser(updatedUser);

        checkUsers(result, updatedUser);
    }

    @Test
    public void testFindUserById() {
        User result1 = userStorage.getUserById(1);
        User result2 = userStorage.getUserById(2);
        User result3 = userStorage.getUserById(3);

        checkUsers(result1, user1);
        checkUsers(result2, user2);
        checkUsers(result3, user3);
    }

    @Test
    public void testAddFriends() {
        addFriends();
    }

    @Test
    public void testGetFriends() {
        addFriends();

        List<User> result = userStorage.getFriends(1);
        List<User> result2 = userStorage.getFriends(2);

        checkUsers(result.get(0), user3);
        checkUsers(result2.get(0), user3);
    }

    @Test
    public void testGetCommonFriends() {
        addFriends();
        List<User> result = userStorage.getCommonFriends(1, 2);

        checkUsers(result.get(0), user3);
    }

    @Test
    public void testDeleteFriend() {
        userStorage.deleteFriend(1, 3);
        List<User> result = userStorage.getFriends(1);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void testGetUsersListWitLimit() {
        List<User> result = userStorage.getUsersList(2);

        assertThat(result.size(), is(2));
        checkUsers(result.get(0), user1);
        checkUsers(result.get(1), user2);
    }

    private void checkUsers(User result, User expected) {
        assertThat(result.getId(), is(expected.getId()));
        assertThat(result.getEmail(), is(expected.getEmail()));
        assertThat(result.getLogin(), is(expected.getLogin()));
        assertThat(result.getName(), is(expected.getName()));
        assertThat(result.getBirthday(), is(expected.getBirthday()));
    }

    private void addFriends() {
        userStorage.addFriend(1, 3);
        userStorage.addFriend(2, 3);
        userStorage.addFriend(3, 1);
        userStorage.addFriend(3, 2);
    }
}
