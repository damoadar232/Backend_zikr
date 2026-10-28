INSERT INTO lyric_categories (id, name)
SELECT 'dua', 'Dua'
WHERE NOT EXISTS (
    SELECT 1 FROM lyric_categories WHERE id = 'dua' AND name = 'Dua'
);

INSERT INTO lyric_categories (id, name)
SELECT 'other-kalam', 'Other Kalam'
WHERE NOT EXISTS (
    SELECT 1 FROM lyric_categories WHERE id = 'other-kalam' AND name = 'Other Kalam'
);

INSERT INTO lyric_categories (id, name)
SELECT 'tazmeen', 'Tazmeen'
WHERE NOT EXISTS (
    SELECT 1 FROM lyric_categories WHERE id = 'tazmeen' AND name = 'Tazmeen'
);

INSERT INTO category_numeric_mappings (category_key)
SELECT 'dua'
WHERE NOT EXISTS (
    SELECT 1 FROM category_numeric_mappings WHERE category_key = 'dua'
);

INSERT INTO category_numeric_mappings (category_key)
SELECT 'other-kalam'
WHERE NOT EXISTS (
    SELECT 1 FROM category_numeric_mappings WHERE category_key = 'other-kalam'
);

INSERT INTO category_numeric_mappings (category_key)
SELECT 'tazmeen'
WHERE NOT EXISTS (
    SELECT 1 FROM category_numeric_mappings WHERE category_key = 'tazmeen'
);
