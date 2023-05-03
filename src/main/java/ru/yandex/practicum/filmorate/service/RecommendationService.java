package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.dao.RecommendationsDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationsDao recommendationsDao;
    private final LikeDao likeDao;
    private final UserDao userDao;

    public List<Film> getRecommendation(int userId) {
        userDao.getUserById(userId);
        Map<Integer, List<Integer>> usersFilms = getUserFilms();
        Map<Integer, Integer> similarUsers = new HashMap<>();
        List<Integer> userFilmsIds = usersFilms.get(userId);
        if (userFilmsIds == null) {
            return new ArrayList<>();
        }
        usersFilms.remove(userId);
        List<Integer> recommendationFilmsIds = new ArrayList<>();
        for (int otherUserId : usersFilms.keySet()) {
            List<Integer> otherFilms = usersFilms.get(otherUserId);
            int count = 0;
            for (Integer filmId : otherFilms) {
                if (userFilmsIds.contains(filmId)) {
                    count++;
                }
            }
            if (count != 0) {
                similarUsers.put(otherUserId, count);
            }
            if (similarUsers.size() > 3) {
                similarUsers.remove(getMin(similarUsers));
            }
        }
        for (Integer recommendationUser : similarUsers.keySet()) {
            List<Integer> userFilms = usersFilms.get(recommendationUser);
            recommendationFilmsIds.addAll(userFilms);
        }
        recommendationFilmsIds = recommendationFilmsIds.stream()
                .distinct()
                .filter(filmId -> !userFilmsIds.contains(filmId))
                .collect(Collectors.toList());
        log.info("Recommendation films for userId {}: {}", userId, recommendationFilmsIds);
        return likeDao.findMostPopularFilms(recommendationFilmsIds);
    }

    private Map<Integer, List<Integer>> getUserFilms() {
        return recommendationsDao.getNumTotalUsersFavoriteFilms();
    }

    private Integer getMin(Map<Integer, Integer> map) {
        Map.Entry<Integer, Integer> maxEntry = Collections.min(map.entrySet(), Map.Entry.comparingByValue());
        return maxEntry.getKey();
    }
}
