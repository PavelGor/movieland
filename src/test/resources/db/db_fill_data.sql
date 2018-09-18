INSERT INTO role (name) VALUES ('GUEST');
INSERT INTO role (name) VALUES ('USER');
INSERT INTO role (name) VALUES ('ADMIN');

INSERT INTO users (role_id, nickname, email, password) VALUES (3, 'Рональд Рейнольдс', 'ronald.reynolds66@example.com', 'paco');
INSERT INTO users (role_id, nickname, email, password) VALUES (2, 'Дарлин Эдвардс', 'darlene.edwards15@example.com', 'bricks');
INSERT INTO users (role_id, nickname, email, password) VALUES (2, 'Габриэль Джексон', 'gabriel.jackson91@example.com', 'hjkl');
INSERT INTO users (role_id, nickname, email, password) VALUES (2, 'Дэрил Брайант', 'daryl.bryant94@example.com', 'exodus');
INSERT INTO users (role_id, nickname, email, password) VALUES (2, 'Нил Паркер', 'neil.parker43@example.com', '878787');
INSERT INTO users (role_id, nickname, email, password) VALUES (2, 'Трэвис Райт', 'travis.wright36@example.com', 'smart');
INSERT INTO users (role_id, nickname, email, password) VALUES (2, 'Амелия Кэннеди', 'amelia.kennedy58@example.com', 'beaker');
INSERT INTO users (role_id, nickname, email, password) VALUES (2, 'Айда Дэвис', 'ida.davis80@example.com', 'pepsi1');
INSERT INTO users (role_id, nickname, email, password) VALUES (2, 'Джесси Паттерсон', 'jessie.patterson68@example.com', 'tommy');
INSERT INTO users (role_id, nickname, email, password) VALUES (2, 'Деннис Крейг', 'dennis.craig82@example.com', 'coldbeer');

INSERT INTO country (name) VALUES ('Франция');
INSERT INTO country (name) VALUES ('Италия');
INSERT INTO country (name) VALUES ('Испания');
INSERT INTO country (name) VALUES ('Великобритания');
INSERT INTO country (name) VALUES ('США');
INSERT INTO country (name) VALUES ('Япония');
INSERT INTO country (name) VALUES ('Германия');

INSERT INTO genre (name) VALUES ('драма');
INSERT INTO genre (name) VALUES ('криминал');
INSERT INTO genre (name) VALUES ('фэнтези');
INSERT INTO genre (name) VALUES ('детектив');
INSERT INTO genre (name) VALUES ('мелодрама');
INSERT INTO genre (name) VALUES ('биография');
INSERT INTO genre (name) VALUES ('комедия');
INSERT INTO genre (name) VALUES ('фантастика');
INSERT INTO genre (name) VALUES ('боевик');
INSERT INTO genre (name) VALUES ('триллер');
INSERT INTO genre (name) VALUES ('приключения');
INSERT INTO genre (name) VALUES ('аниме');
INSERT INTO genre (name) VALUES ('мультфильм');
INSERT INTO genre (name) VALUES ('семейный');
INSERT INTO genre (name) VALUES ('вестерн');

INSERT INTO movie (name_ru, name_eng, year_release, description, rating, price)
VALUES ('Побег из Шоушенка', 'The Shawshank Redemption', 1994, 'Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.', 8.9, 123.45);
INSERT INTO movie (name_ru, name_eng, year_release, description, rating, price)
VALUES ('Начало', 'Inception', 2010, 'Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. Редкие способности Кобба сделали его ценным игроком в привычном к предательству мире промышленного шпионажа, но они же превратили его в извечного беглеца и лишили всего, что он когда-либо любил.', 8.6, 130.0);

INSERT INTO movie_country (movie_id, country_id) VALUES ((select id from movie m where m.name_ru = 'Побег из Шоушенка'),(select id from country c where c.name = 'США'));
INSERT INTO movie_country (movie_id, country_id) VALUES ((select id from movie m where m.name_ru = 'Начало'),(select id from country c where c.name = 'Великобритания'));
INSERT INTO movie_country (movie_id, country_id) VALUES ((select id from movie m where m.name_ru = 'Начало'),(select id from country c where c.name = 'США'));

INSERT INTO movie_genre(movie_id, genre_id) VALUES ((select id from movie m where m.name_ru = 'Побег из Шоушенка'),(select id from genre c where c.name = 'драма'));
INSERT INTO movie_genre(movie_id, genre_id) VALUES ((select id from movie m where m.name_ru = 'Побег из Шоушенка'),(select id from genre c where c.name = 'криминал'));
INSERT INTO movie_genre(movie_id, genre_id) VALUES ((select id from movie m where m.name_ru = 'Начало'),(select id from genre c where c.name = 'фантастика'));
INSERT INTO movie_genre(movie_id, genre_id) VALUES ((select id from movie m where m.name_ru = 'Начало'),(select id from genre c where c.name = 'боевик'));
INSERT INTO movie_genre(movie_id, genre_id) VALUES ((select id from movie m where m.name_ru = 'Начало'),(select id from genre c where c.name = 'триллер'));
INSERT INTO movie_genre(movie_id, genre_id) VALUES ((select id from movie m where m.name_ru = 'Начало'),(select id from genre c where c.name = 'драма'));
INSERT INTO movie_genre(movie_id, genre_id) VALUES ((select id from movie m where m.name_ru = 'Начало'),(select id from genre c where c.name = 'детектив'));

UPDATE movie SET poster = 'https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg' WHERE name_ru = 'Побег из Шоушенка';
UPDATE movie SET poster = 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1._SY209_CR0,0,140,209_.jpg' WHERE name_ru = 'Начало';

INSERT INTO review (movie_id, user_id, text) VALUES ((select id from movie m where m.name_ru = 'Побег из Шоушенка'),(select id from users u where u.nickname = 'Дарлин Эдвардс'),'Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.');
INSERT INTO review (movie_id, user_id, text) VALUES ((select id from movie m where m.name_ru = 'Побег из Шоушенка'),(select id from users u where u.nickname = 'Габриэль Джексон'),'Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.');
