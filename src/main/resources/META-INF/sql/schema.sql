CREATE TABLE book (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  genre VARCHAR(255),
  sub_genre VARCHAR(255),
  publish_date DATE,
  rating VARCHAR(32)
);

CREATE TABLE author(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name VARCHAR(255)
);

CREATE TABLE book_author (
  book_id BIGINT,
  author_id BIGINT
);