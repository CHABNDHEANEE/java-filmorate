package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FeedDao;
import ru.yandex.practicum.filmorate.dao.ReviewDao;
import ru.yandex.practicum.filmorate.model.Review;

import java.time.Instant;
import java.util.List;

import static ru.yandex.practicum.filmorate.model.EventType.REVIEW;
import static ru.yandex.practicum.filmorate.model.Operation.*;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewDao reviewDao;
    private final FeedDao feedDao;

    public Review addReview(Review review) {
        Review review1 = reviewDao.addReview(review);
        feedDao.addFeed(review1.getReviewId(), review1.getUserId(), Instant.now().toEpochMilli(), REVIEW, ADD);
        return review1;
    }

    public Review updateReview(Review review) {
        Review review1 = reviewDao.updateReview(review);
        feedDao.addFeed(review1.getReviewId(), review1.getUserId(), Instant.now().toEpochMilli(), REVIEW, UPDATE);
        return review1;
    }

    public List<Review> getReviewList(int filmId, int count) {
        return reviewDao.getReviewList(filmId, count);
    }

    public void deleteReview(int id) {
        feedDao.addFeed(id, reviewDao.getReviewById(id).getUserId(), Instant.now().toEpochMilli(), REVIEW, REMOVE);
        reviewDao.deleteReviewById(id);
    }

    public Review getReviewById(int id) {
        return reviewDao.getReviewById(id);
    }
}
