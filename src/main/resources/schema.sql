DROP TABLE IF EXISTS mpa_rating, genres, films, films_genres, users, friends, likes;

CREATE TABLE IF NOT EXISTS mpa_rating
(
    mpa_id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    mpa_name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS genres
(
    genre_id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre_name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS films
(
    film_id      INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_name    VARCHAR NOT NULL,
    description  VARCHAR(200),
    release_date date    NOT NULL,
    duration     INTEGER
        CONSTRAINT duration_positive CHECK (duration > 0),
    mpa_id       INTEGER
        CONSTRAINT rating_ref REFERENCES mpa_rating (mpa_id)
);

CREATE TABLE IF NOT EXISTS films_genres
(
    film_id INTEGER
        CONSTRAINT film_id_ref REFERENCES films (film_id) ON DELETE CASCADE,
    genre_id INTEGER
        CONSTRAINT genre_id_ref REFERENCES genres (genre_id),
    CONSTRAINT films_and_genres_id_unique UNIQUE (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    login     VARCHAR NOT NULL,
    user_name VARCHAR,
    email     VARCHAR NOT NULL
        CONSTRAINT valid_email_check CHECK (email LIKE '%_@_%.__%'),
    birthday DATE    NOT NULL
);

CREATE TABLE IF NOT EXISTS friends
(
    user_id   INTEGER
        CONSTRAINT user_id_ref REFERENCES users (user_id) ON DELETE CASCADE,
    friend_id INTEGER
        CONSTRAINT friend_id_ref REFERENCES users (user_id) ON DELETE CASCADE,
    status BOOLEAN DEFAULT false,
        CONSTRAINT friends_pk PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS likes
(
    film_id INTEGER
        CONSTRAINT likes_film_id_ref REFERENCES films (film_id) ON DELETE CASCADE,
    user_id INTEGER
        CONSTRAINT likes_user_id_ref REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT films_and_user_id_unique UNIQUE (film_id, user_id)
);