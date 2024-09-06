INSERT INTO BOOK_CATEGORIES
(ID,
 NAME,
 PARENT_ID,
 DESCRIPTION,
 CREATED_AT)
VALUES (220010, 'Fiction', null, null, CURRENT_TIMESTAMP()),
       (220011, 'Non-Fiction', null, null, CURRENT_TIMESTAMP()),
       (220012, 'Romantic', 220010, 'Romantic stories.', CURRENT_TIMESTAMP()),
       (220013, 'History', 220011, 'History related.', CURRENT_TIMESTAMP()),
       (220014, 'Economic', 220011, 'Economic related.', CURRENT_TIMESTAMP()),
       (220016, 'Arts', 220010, null, CURRENT_TIMESTAMP());

INSERT INTO BOOK_AUTHORS
(ID,
 FIRSTNAME,
 MIDDLE_NAME,
 LASTNAME,
 CREATED_AT)
VALUES (310010, 'Dwaine', null, 'McMaugh', CURRENT_TIMESTAMP()),
    (310011, 'Bill', null, 'Bryson', CURRENT_TIMESTAMP()),
    (310012, 'Lorna', null, 'Scobie', CURRENT_TIMESTAMP()),
    (310013, 'Jana', null, 'Sawyer', CURRENT_TIMESTAMP()),
    (310016, 'Thomas', null, 'Sowell', CURRENT_TIMESTAMP());


INSERT INTO BOOKS
(ID,
 NAME,
 CATEGORY_ID,
 PUBLICATION_DATE,
 CREATED_AT)
VALUES (110010, 'The Fiction Police: Defending creativity in the age of artificial intelligence', 220010, '2024-07-15', CURRENT_TIMESTAMP()),
    (110011, 'Basic Economics', 220014, '2014-12-01', CURRENT_TIMESTAMP()),
    (110012, 'The Crimson Canvas: A Romantic Suspense Novel', 220012, '2014-08-12', CURRENT_TIMESTAMP()),
    (110013, 'A Short History of Nearly Everything', 220013, '2016-08-15', CURRENT_TIMESTAMP()),
    (110016, '365 Days of Art: A Creative Exercise for Every Day of the Year', 220016, '2017-11-01', CURRENT_TIMESTAMP());

INSERT INTO BOOKS_BOOKS_AUTHORS
(BOOK_ID, AUTHOR_ID)
VALUES (110010, 310016),
    (110011, 310010),
    (110012, 310013),
    (110013, 310011),
    (110016, 310012);
