package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.dao.RecommendationsDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationsDao recommendationsDao;
    private final LikeDao likeDao;
    private final UserDao userDao;

    public List<Film> getRecommendation(int userId) {
        Map<Integer, List<Integer>> usersFilms = getUserFilms();
        List<Integer> userFilmsIds = usersFilms.get(userId);
        if (userFilmsIds == null) {
            return new ArrayList<>();
        }

        userDao.getUserById(userId);
        usersFilms.remove(userId);
        Map<Integer, Long> similarUsers = usersFilms.keySet().stream()
                .collect(Collectors.toMap(Function.identity(),
                        otherUserId -> usersFilms.get(otherUserId).stream()
                                .filter(userFilmsIds::contains)
                                .count()));

        List<Integer> recommendationFilmsIds = similarUsers.entrySet().stream()
                .filter(similarUser -> similarUser.getValue() != 0)
                .sorted(comparingLong(Map.Entry::getValue))
                .limit(3)
                .flatMap(recommendationUser -> usersFilms.get(recommendationUser.getKey()).stream())
                .distinct()
                .filter(filmId -> !userFilmsIds.contains(filmId))
                .collect(Collectors.toList());

        log.info("Recommendation films for userId {}: {}", userId, recommendationFilmsIds);
        return likeDao.findMostPopularFilms(recommendationFilmsIds);
    }

    private Map<Integer, List<Integer>> getUserFilms() {
        return recommendationsDao.getNumTotalUsersFavoriteFilms();
    }
}
