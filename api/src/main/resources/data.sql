INSERT INTO USER_PERMISSIONS
(ID,
 NAME,
 PARENT_ID,
 DESCRIPTION,
 CREATED_AT)
VALUES (9999, 'users.accounts.almighty', NULL, NULL, CURRENT_TIMESTAMP()),
    (10000, 'users.accounts.admin', NULL, NULL, CURRENT_TIMESTAMP()),
    (10001, 'users.accounts.create', 10000, NULL, CURRENT_TIMESTAMP()),
    (10003, 'users.accounts.read', 10000, NULL, CURRENT_TIMESTAMP()),
    (10004, 'users.accounts.update', 10000, NULL, CURRENT_TIMESTAMP()),
    (10005, 'users.accounts.archive', 10000, NULL, CURRENT_TIMESTAMP()),
    (10007, 'users.accounts.profile', 10000, NULL, CURRENT_TIMESTAMP()),
    (10008, 'users.roles.admin', NULL, NULL, CURRENT_TIMESTAMP()),
    (10009, 'users.roles.create', 10008, NULL, CURRENT_TIMESTAMP()),
    (10010, 'users.roles.read', 10008, NULL, CURRENT_TIMESTAMP()),
    (10011, 'users.roles.update', 10008, NULL, CURRENT_TIMESTAMP()),
    (10012, 'users.roles.archive', 10008, NULL, CURRENT_TIMESTAMP()),
    (10013, 'users.permissions.admin', NULL, NULL, CURRENT_TIMESTAMP()),
    (10014, 'users.permissions.create', 10013, NULL, CURRENT_TIMESTAMP()),
    (10015, 'users.permissions.read', 10013, NULL, CURRENT_TIMESTAMP()),
    (10016, 'users.permissions.update', 10013, NULL, CURRENT_TIMESTAMP()),
    (10017, 'users.permissions.archive', 10013, NULL, CURRENT_TIMESTAMP()),
    (10028, 'books.categories.admin', NULL, NULL, CURRENT_TIMESTAMP()),
    (10029, 'books.categories.create', 10028, NULL, CURRENT_TIMESTAMP()),
    (10030, 'books.categories.read', 10028, NULL, CURRENT_TIMESTAMP()),
    (10031, 'books.categories.update', 10028, NULL, CURRENT_TIMESTAMP()),
    (10032, 'books.categories.archive', 10028, NULL, CURRENT_TIMESTAMP()),
    (10033, 'books.authors.admin', NULL, NULL, CURRENT_TIMESTAMP()),
    (10034, 'books.authors.create', 10033, NULL, CURRENT_TIMESTAMP()),
    (10035, 'books.authors.read', 10033, NULL, CURRENT_TIMESTAMP()),
    (10036, 'books.authors.update', 10033, NULL, CURRENT_TIMESTAMP()),
    (10037, 'books.authors.archive', 10033, NULL, CURRENT_TIMESTAMP()),
    (10038, 'books.books.admin', NULL, NULL, CURRENT_TIMESTAMP()),
    (10039, 'books.books.create', 10038, NULL, CURRENT_TIMESTAMP()),
    (10040, 'books.books.read', 10038, NULL, CURRENT_TIMESTAMP()),
    (10041, 'books.books.update', 10038, NULL, CURRENT_TIMESTAMP()),
    (10042, 'books.books.archive', 10038, NULL, CURRENT_TIMESTAMP());

INSERT INTO USER_ROLES
(ID,
 NAME,
 PARENT_ID,
 DESCRIPTION,
 CREATED_AT)
VALUES (10000, 'Administrator', NULL, NULL, CURRENT_TIMESTAMP()),
    (10001, 'Regular User', NULL, NULL, CURRENT_TIMESTAMP());

INSERT INTO USER_ROLES_PERMISSIONS
(ROLE_ID,
 PERMISSION_ID)
VALUES (10000, 10001),
    (10000, 10003),
    (10000, 10004),
    (10000, 10005),
    (10000, 10007),
    (10000, 10008),
    (10000, 10009),
    (10000, 10010),
    (10000, 10011),
    (10000, 10012),
    (10000, 10013),
    (10000, 10014),
    (10000, 10015),
    (10000, 10016),
    (10000, 10017),
    (10000, 10028),
    (10000, 10029),
    (10000, 10030),
    (10000, 10031),
    (10000, 10032),
    (10000, 10033),
    (10000, 10034),
    (10000, 10035),
    (10000, 10036),
    (10000, 10037),
    (10000, 10038),
    (10000, 10039),
    (10000, 10040),
    (10000, 10041),
    (10000, 10042),
    (10001, 10003),
    (10001, 10028),
    (10001, 10029),
    (10001, 10030),
    (10001, 10031),
    (10001, 10032),
    (10001, 10033),
    (10001, 10034),
    (10001, 10035),
    (10001, 10036),
    (10001, 10037),
    (10001, 10038),
    (10001, 10039),
    (10001, 10040),
    (10001, 10041),
    (10001, 10042);

INSERT INTO USER_ACCOUNTS
(ID,
 FIRSTNAME,
 LASTNAME,
 EMAIL,
 PASSWORD,
 STATUS,
 LAST_LOGIN_AT,
 CREATED_AT)
VALUES (1000, 'test', 'administrator', 'administrator@test.com', '$2a$10$yNYiGeUtd0tJloPwBJxnz.uNhhqkDPg./hjaFdbfFBDNPcbk.Ryz.', 'ACTIVE',
        NULL, CURRENT_TIMESTAMP()),
    (1001, 'test', 'user', 'user@test.com', 'to be changed', 'ACTIVE', NULL, CURRENT_TIMESTAMP()),
    (1002, 'test', 'disabled', 'disabled@test.com', 'to be changed', 'DISABLED', NULL, CURRENT_TIMESTAMP());

INSERT INTO USER_ACCOUNTS_ROLES
(ACCOUNT_ID,
 ROLE_ID)
VALUES (1000, 10000),
    (1001, 10001),
    (1002, 10001);

INSERT INTO USER_ACCOUNTS_PERMISSIONS
(ACCOUNT_ID,
 PERMISSION_ID)
VALUES (1000, 9999);

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
VALUES (110010, 'The Fiction Police: Defending creativity in the age of artificial intelligence', 220010, '2024-07-15',
        CURRENT_TIMESTAMP()),
    (110011, 'Basic Economics', 220014, '2014-12-01', CURRENT_TIMESTAMP()),
    (110012, 'The Crimson Canvas: A Romantic Suspense Novel', 220012, '2014-08-12', CURRENT_TIMESTAMP()),
    (110013, 'A Short History of Nearly Everything', 220013, '2016-08-15', CURRENT_TIMESTAMP()),
    (110016, '365 Days of Art: A Creative Exercise for Every Day of the Year', 220016, '2017-11-01', CURRENT_TIMESTAMP());



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

INSERT INTO BOOKS_BOOKS_AUTHORS
    (BOOK_ID, AUTHOR_ID)
VALUES (110010, 310016),
    (110011, 310010),
    (110012, 310013),
    (110013, 310011),
    (110016, 310012);
