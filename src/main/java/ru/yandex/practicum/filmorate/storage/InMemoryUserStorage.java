package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectExistenceException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();

    private int id = 1;

    @Override
    public User addUser(User user) {
        checkUser(user);

        setIdForUser(user);
        user.setFriendsId(new HashSet<>());
        users.put(user.getId(), user);
        log.info("User added");
        return user;
    }

    @Override
    public User updateUser(User user) {
        int userId = user.getId();

        checkUser(user);

        if (!users.containsKey(userId)) {
            log.info("wrong id update user");
            throw new ObjectExistenceException("User with the id doesn't exist");
        }

        Set<Integer> friendsList = users.get(userId).getFriendsId();
        user.setFriendsId(friendsList);

        users.put(userId, user);
        log.info("User updated");
        return user;
    }

    @Override
    public User getUser(int id) {
        if (!users.containsKey(id)) {
            throw new ObjectExistenceException("User with the id doesn't exist");
        }

        return users.get(id);
    }

    @Override
    public User deleteUser(User user) {
        checkUsersListExistence();

        users.remove(user.getId());
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        checkUsersListExistence();
        log.info("All users printed");
        return new ArrayList<>(users.values());
    }

    @Override
    public void clearUserList() {
        users.clear();
        id = 1;
    }

    private void setIdForUser(User user) {
        user.setId(id);
        id++;
    }

    private void checkUsersListExistence() {
        if (users.isEmpty()) {
            log.info("User list empty error");
            throw new ObjectExistenceException("User list is empty");
        }
    }

    private void checkUser(User user) {
        if (user.getLogin().contains(" ")) {
            log.info("Incorrect user login err");
            throw new ValidationException("Incorrect user login!");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Wrong bd err");
            throw new ValidationException("You have a time machine! Don't ya?");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getId() == -1) {
            user.setId(id);
            id++;
        }
    }
}
