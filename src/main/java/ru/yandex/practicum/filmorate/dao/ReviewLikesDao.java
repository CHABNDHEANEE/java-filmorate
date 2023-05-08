package ru.yandex.practicum.filmorate.dao;

public interface ReviewLikesDao {
    void like(int reviewId, int userId);

    void dislike(int reviewId, int userId);

    void deleteLike(int reviewId, int userId);

    void deleteDislike(int reviewId, int userId);
}