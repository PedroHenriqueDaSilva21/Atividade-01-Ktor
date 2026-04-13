-- V2__seed_data.sql
-- Inserts initial seed data for authors and books

INSERT INTO authors (name, nationality) VALUES
    ('Machado de Assis',    'Brasileiro'),
    ('Clarice Lispector',   'Brasileira'),
    ('Jorge Amado',         'Brasileiro'),
    ('J.R.R. Tolkien',      'Britânico'),
    ('George Orwell',       'Britânico')
ON CONFLICT DO NOTHING;

INSERT INTO books (title, isbn, year, author_id) VALUES
    ('Dom Casmurro',                     '978-8535902778',  1899, (SELECT id FROM authors WHERE name = 'Machado de Assis')),
    ('Memórias Póstumas de Brás Cubas',  '978-8535910285',  1881, (SELECT id FROM authors WHERE name = 'Machado de Assis')),
    ('A Hora da Estrela',                '978-8532510136',  1977, (SELECT id FROM authors WHERE name = 'Clarice Lispector')),
    ('Perto do Coração Selvagem',        '978-8532512253',  1943, (SELECT id FROM authors WHERE name = 'Clarice Lispector')),
    ('Gabriela, Cravo e Canela',         '978-8535911046',  1958, (SELECT id FROM authors WHERE name = 'Jorge Amado')),
    ('Capitães da Areia',                '978-8535916850',  1937, (SELECT id FROM authors WHERE name = 'Jorge Amado')),
    ('O Senhor dos Anéis - A Sociedade', '978-8533614291',  1954, (SELECT id FROM authors WHERE name = 'J.R.R. Tolkien')),
    ('O Hobbit',                         '978-8533605343',  1937, (SELECT id FROM authors WHERE name = 'J.R.R. Tolkien')),
    ('1984',                             '978-8535914849',  1949, (SELECT id FROM authors WHERE name = 'George Orwell')),
    ('A Revolução dos Bichos',           '978-8535906646',  1945, (SELECT id FROM authors WHERE name = 'George Orwell'))
ON CONFLICT DO NOTHING;
