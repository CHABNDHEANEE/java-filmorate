package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.ReviewDao;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

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

    public List<Review> getReviewList(int filmId, int count) {
        return reviewDao.getReviewList(filmId, count);
    }

    public void deleteReview(int id) {
        reviewDao.deleteReviewById(id);
    }

    public Review getReviewById(int id) {
        return reviewDao.getReviewById(id);
    }
}
