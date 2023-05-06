package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewDao {
    Review addReview(Review review);

    Review updateReview(Review review);

    List<Review> getReviewList(int filmId, int count);

    void deleteReviewById(int id);

    Review getReviewById(int id);
}
