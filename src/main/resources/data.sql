INSERT IGNORE  INTO "rate_mpa" ("id", "name", "is_active")
VALUES (1, 'G', true),
       (2, 'PG', true),
       (3, 'PG-13', true),
       (4, 'R', true),
       (5, 'NC-17', true);
INSERT IGNORE  INTO "genre" ("id", "name", "is_active")
VALUES (1, 'Комедия', true),
       (2, 'Драма', true),
       (3, 'Мультфильм', true),
       (4, 'Триллер', true),
       (5, 'Документальный', true),
       (6, 'Боевик', true);