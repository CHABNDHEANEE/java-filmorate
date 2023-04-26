package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.ReviewDao;
import ru.yandex.practicum.filmorate.model.Review;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewDao reviewDao;

    public Review addReview(Review review) {
        return reviewDao.addReview(review);
    }

    public Review updateReview(Review review) {
        return reviewDao.updateReview(review);
    }

    public Review deleteReview(int id) {
        return reviewDao.deleteReviewById(id);
    }

    public Review getReviewById(int id) {
        return reviewDao.getReviewById(id);
    }
}
