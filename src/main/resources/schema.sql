create table IF NOT EXISTS FILM_RATING
(
    RATING_ID   INTEGER auto_increment,
    RATING_NAME CHARACTER VARYING(10) not null,
    constraint "FILM_RATING_pk"
        primary key (RATING_ID)
);
create table IF NOT EXISTS FILMS
(
    FILM_ID           INTEGER auto_increment,
    FILM_TITLE        CHARACTER VARYING(50) not null,
    FILM_DESCRIPTION  CHARACTER VARYING(500),
    FILM_RELEASE_DATE DATE                  not null,
    FILM_DURATION     INTEGER               not null,
    FILM_RATING_ID    INTEGER               not null,
    constraint FILMS_PK
        primary key (FILM_ID),
    constraint FILMS_FILM_RATING_RATING_ID_FK
        foreign key (FILM_RATING_ID) references FILM_RATING
);


create table IF NOT EXISTS FRIENDSHIP_STATUS
(
    STATUS_ID   INTEGER auto_increment,
    STATUS_NAME CHARACTER VARYING(20) not null,
    constraint "FRIENDSHIP_STATUS_pk"
        primary key (STATUS_ID)
);
create table IF NOT EXISTS USERS
(
    USER_ID             INTEGER auto_increment,
    USER_EMAIL          CHARACTER VARYING(50) not null,
    USER_LOGIN          CHARACTER VARYING(50) not null,
    USER_NAME           CHARACTER VARYING(50),
    USER_BIRTHDAY       DATE,
    constraint USERS_PK
        primary key (USER_ID)
);
create table IF NOT EXISTS FRIENDS
(
    USER_ID              INTEGER not null,
    FRIEND_ID            INTEGER not null,
    FRIENDSHIP_STATUS_ID INTEGER not null,
    constraint "FRIENDS_FRIENDSHIP_STATUS_STATUS_ID_fk"
        foreign key (FRIENDSHIP_STATUS_ID) references FRIENDSHIP_STATUS,
    constraint "FRIENDS_USERS_USER_ID_fk"
        foreign key (USER_ID) references USERS,
    constraint "FRIENDS_USERS_USER_ID_fk2"
        foreign key (FRIEND_ID) references USERS
);
create table IF NOT EXISTS USERS_LIKED_FILMS
(
    FILM_ID INTEGER not null,
    USER_ID INTEGER not null,
    constraint "USERS_LIKED_FILM_FILMS_FILM_ID_fk"
        foreign key (FILM_ID) references FILMS,
    constraint "USERS_LIKED_FILM_USERS_USER_ID_fk"
        foreign key (USER_ID) references USERS
);
create table IF NOT EXISTS GENRES
(
    GENRE_ID    INTEGER auto_increment,
    GENRE_TITLE CHARACTER VARYING(255) not null,
    constraint FILM_GENRE_PK
        primary key (GENRE_ID)
);
create table IF NOT EXISTS FILM_GENRE
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint "FILM_GENRE_FILMS_FILM_ID_fk"
        foreign key (FILM_ID) references FILMS,
    constraint "FILM_GENRE_GENRES_GENRE_ID_fk"
        foreign key (GENRE_ID) references GENRES
);

