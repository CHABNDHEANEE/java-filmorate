# java-filmorate
Template repository for Filmorate project.
---
ER-diagram: https://dbdiagram.io/d/642da7ba5758ac5f17270683

1. FILMS - хранилище с фильмами
* FILM_ID - ID фильма
* FILM_TITLE - Название фильма
* FILM_GENRE_ID - ID жанра фильма. Список жанров хранится в FILM_GENRE
* FILM_DESCRIPTION - Описание фильма
* FILM_REALEASE_DATE - Дата релиза фильма
* FILM_DURATION - Длительность фильма
* FILM_RATING_ID - Возрастной рейтинг фильма. Хранится в таблице FILM_RATING.
2. USERS_LIKED_FILM - Юзеры, которым понравился фильм.
* FILM_ID - ID лайкнутого фильма
* USER_ID - ID юзера, который лайкнул фильм
3. USERS - хранилище с юзерами
* USER_ID - ID юзера
* USER_EMAIL - mail юзера
* USER_LOGIN - login юзера
* USER_NAME - Имя юзера
* USER_BIRTHDAY - ДР юзера
4. FRIENDS - хранилище со с друзьями
* USER_ID - ID юзера
* FRIEND_ID - ID друга юзера
* FRIENDSHIP_STATUS_ID - статус дружбы. Хранится в FRIENDSHIP_STATUS
