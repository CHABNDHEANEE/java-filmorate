package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.ReviewLikesDao;

@RequiredArgsConstructor
@Service
public class ReviewLikesService {
    private final ReviewLikesDao likesDao;

    public void like(int reviewId, int userId) {
        likesDao.like(reviewId, userId);
    }

    public void dislike(int reviewId, int userId) {
        likesDao.dislike(reviewId, userId);
    }

    public void deleteLike(int reviewId, int userId) {
        likesDao.deleteLike(reviewId, userId);
    }

    public void deleteDislike(int reviewId, int userId) {
        likesDao.deleteDislike(reviewId, userId);
    }
}
