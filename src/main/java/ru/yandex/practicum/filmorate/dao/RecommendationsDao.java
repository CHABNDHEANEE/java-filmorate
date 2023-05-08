package ru.yandex.practicum.filmorate.dao;

import java.util.HashMap;
import java.util.List;

public interface RecommendationsDao {

    HashMap<Integer, List<Integer>> getNumTotalUsersFavoriteFilms();

}
