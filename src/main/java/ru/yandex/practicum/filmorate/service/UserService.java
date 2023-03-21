package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User addUser(User user) {
        log.info("add user service");

        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        log.info("update user service");

        return userStorage.updateUser(user);
    }

    public User getUser(int id) {
        log.info("get user service");

        return userStorage.getUser(id);
    }

    public User deleteUser(User user) {
        log.info("delete user service");

        return userStorage.deleteUser(user);
    }

    public void addFriend(int userId, int friendId) {
        log.info("add friends");

        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);

        user.addFriend(friend);
        friend.addFriend(user);
    }

    public void deleteFriend(int userId, int friendId) {
        log.info("delete friend");

        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);

        user.deleteFriend(friend);
        friend.deleteFriend(user);
    }

    public List<User> getFriendsList(int id) {
        log.info("get friends list");
        return convertSetOfIdsToUsers(userStorage.getUser(id).getFriendsId());
    }

    public List<User> getCommonFriends(int id, int otherId) {
        log.info("get common friends service");

        User user = userStorage.getUser(id);
        User other = userStorage.getUser(otherId);
        Set<Integer> userFriends = new HashSet<>(user.getFriendsId());
        Set<Integer> otherFriends = new HashSet<>(other.getFriendsId());

        userFriends.retainAll(otherFriends);

        return convertSetOfIdsToUsers(userFriends);
    }

    private List<User> convertSetOfIdsToUsers(Set<Integer> ids) {
        return ids.stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        log.info("get all users service");

        return userStorage.getAllUsers();
    }

    public void deleteFriend(User initUser, User requestedUser) {
        log.info("delete friend");
        initUser.addFriend(requestedUser);
        requestedUser.addFriend(initUser);
    }

    public void clearUserList() {
        userStorage.clearUserList();
    }
}
