package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.Operation;

import java.util.List;

public interface FeedDao {
    void addFeed(Integer userId, Integer entityId, long timeStamp, EventType eventType, Operation operation);

    List<Feed> getFeed(int id);
}
