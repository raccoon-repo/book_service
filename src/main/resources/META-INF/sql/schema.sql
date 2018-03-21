CREATE TABLE book (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  genre VARCHAR(255),
  sub_genre VARCHAR(255),
  publish_date DATE,
  rating VARCHAR(32),
  version BIGINT NOT NULL DEFAULT 0,
  cover BLOB
);

CREATE TABLE author (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  version BIGINT NOT NULL DEFAULT 0,
  photo BLOB
);

CREATE TABLE book_author (
  book_id BIGINT,
  author_id BIGINT
);

CREATE TABLE tags (
  book_id BIGINT NOT NULL,
  tag VARCHAR(32)
);

ALTER TABLE tags
    ADD FOREIGN KEY (book_id) REFERENCES book(id);