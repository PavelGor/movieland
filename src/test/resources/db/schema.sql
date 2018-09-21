SET MODE PostgreSQL;

CREATE TABLE role (
    id SERIAL PRIMARY KEY  NOT NULL,
    name TEXT NOT NULL
);

CREATE TABLE USERS (
  id SERIAL PRIMARY KEY,
  email VARCHAR(50)  UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role_id SMALLINT NOT NULL,
  nickname VARCHAR(100)
);

CREATE TABLE country (
    id  SERIAL PRIMARY KEY  NOT NULL,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE genre (
    id SERIAL PRIMARY KEY  NOT NULL,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE movie (
    id  SERIAL PRIMARY KEY  NOT NULL,
    name_ru VARCHAR(255) NOT NULL,
    name_eng VARCHAR(255) NOT NULL,
    year_release SMALLINT NOT NULL,
    description TEXT NOT NULL,
    rating REAL NOT NULL,
    price REAL NOT NULL,
    poster TEXT
);

CREATE TABLE movie_country (
    id SERIAL PRIMARY KEY  NOT NULL,
    movie_id INT NOT NULL,
    country_id INT NOT NULL
);

CREATE TABLE movie_genre (
    id SERIAL PRIMARY KEY  NOT NULL,
    movie_id INT NOT NULL,
    genre_id INT NOT NULL
);

CREATE TABLE review (
    id SERIAL PRIMARY KEY NOT NULL,
    movie_id INT NOT NULL,
    user_id INT NOT NULL,
    text TEXT NOT NULL
);