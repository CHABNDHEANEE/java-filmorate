package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Review;

public interface ReviewDao {
    Review addReview(Review review);

    Review updateReview(Review review);

    Review deleteReviewById(int id);

    Review getReviewById(int id);
}
