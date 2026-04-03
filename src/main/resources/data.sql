INSERT INTO genres (name) VALUES ('Fantasy');
INSERT INTO genres (name) VALUES ('Detective');
INSERT INTO genres (name) VALUES ('Science');
INSERT INTO genres (name) VALUES ('Classic');

INSERT INTO books (title, publish_year, author, description, cover_url, rating, genre_id)
VALUES ('Harry Potter and the Philosopher''s Stone', 1997, 'J. K. Rowling', 'A boy discovers he is a wizard and joins Hogwarts.', 'https://covers.openlibrary.org/b/id/7884866-L.jpg', 4.8, 1);
INSERT INTO books (title, publish_year, author, description, cover_url, rating, genre_id)
VALUES ('The Hound of the Baskervilles', 1902, 'Arthur Conan Doyle', 'Sherlock Holmes investigates a legendary hound.', 'https://covers.openlibrary.org/b/id/8231856-L.jpg', 4.5, 2);
INSERT INTO books (title, publish_year, author, description, cover_url, rating, genre_id)
VALUES ('A Brief History of Time', 1988, 'Stephen Hawking', 'Popular science book about cosmology.', 'https://covers.openlibrary.org/b/id/240726-L.jpg', 4.4, 3);
