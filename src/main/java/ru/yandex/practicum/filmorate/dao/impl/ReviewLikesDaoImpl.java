package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.ReviewDao;
import ru.yandex.practicum.filmorate.dao.ReviewLikesDao;

@Component
@RequiredArgsConstructor
public class ReviewLikesDaoImpl implements ReviewLikesDao {
    private final JdbcTemplate jdbcTemplate;
    private final ReviewDao reviewDao;

    @Override
    public void like(int reviewId, int userId) {
        String sql =
                "INSERT INTO review_likes (review_id, is_like, user_id) " +
                        "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, reviewId, Boolean.TRUE, userId);
        reviewDao.updateReviewUsefulness(reviewId, 1);
    }

    @Override
    public void dislike(int reviewId, int userId) {
        String sql =
                "INSERT INTO review_likes (review_id, is_like, user_id) " +
                        "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, reviewId, Boolean.FALSE, userId);
        reviewDao.updateReviewUsefulness(reviewId, -1);
    }

    @Override
    public void deleteLike(int reviewId, int userId) {
        String sql =
                "DELETE FROM review_likes WHERE review_id = ? AND is_like = ? AND user_id = ?";

        jdbcTemplate.update(sql, reviewId, Boolean.TRUE, userId);
        reviewDao.updateReviewUsefulness(reviewId, -1);
    }

    @Override
    public void deleteDislike(int reviewId, int userId) {
        String sql =
                "DELETE FROM review_likes WHERE review_id = ? AND is_like = ? AND user_id = ?";

        jdbcTemplate.update(sql, reviewId, Boolean.FALSE, userId);
        reviewDao.updateReviewUsefulness(reviewId, 1);
    }
}
