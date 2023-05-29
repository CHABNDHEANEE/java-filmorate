# filmorate

***Учебный проект Яндекс Практикума.***

Backend социальной сети поддерживающей добавление, удаление, оценку фильмов, подбор и вывод рейтингов.

---
### Стек технологий

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring%20Boot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

---
### Функционал
- Есть возможность заводить друзей
- Возможен просмотр списка общих фильмов
- Реализована автоматическая рекомендательная система для фильмов
- Система отзывов с оценкой полезности
- Оставлять отзывы
- Рекомендовать друг другу фильмы
- Реализована лента событий
- Поиск с агригацией по жанру и году
- Реализован функционал поиска по ключевому слову из названия или описания

---
### Хранение данных

Для хранения данных используется встраиваемая база данных H2.

Доступ к данным осуществляется через JdbcTemplate с нативными SQL-запросами.
![ER](https://github.com/CHABNDHEANEE/java-filmorate/blob/main/ER.png?raw=true)
