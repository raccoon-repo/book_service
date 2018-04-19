DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS book;

CREATE TABLE book (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(112) NOT NULL,
  genre VARCHAR(32),
  sub_genre VARCHAR(32),
  publish_date DATE,
  rating FLOAT(16),
  version INTEGER NOT NULL DEFAULT 0,
  description VARCHAR(1536),
  cover BLOB
);

CREATE TABLE author (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(32),
  last_name VARCHAR(32),
  version INTEGER NOT NULL DEFAULT 0,
  photo BLOB
);

CREATE TABLE book_author (
  book_id BIGINT,
  author_id BIGINT
);

CREATE TABLE tag (
  book_id BIGINT NOT NULL,
  tag VARCHAR(32)
);

ALTER TABLE tag
    ADD FOREIGN KEY (book_id) REFERENCES book(id);