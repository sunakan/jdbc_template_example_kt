-- spring.sql.init.data-locations=classpath:hoge-data.sql
-- hoge-data.sql
INSERT INTO customers ( last_name, first_name ) VALUES ( 'resources-hoge-data.sqlから入れた', '田中' );


INSERT INTO products
(code, name, price, cost)
VALUES
('a-code', 'バナナ', 100, 1111),
('b-code', 'リンゴ', 200, 2222),
('c-code', 'ブドウ', 300, 3333),
('d-code', 'スイカ', 400, 4444),
('e-code', 'メロン', 500, 5555),
('f-code', 'レモン', 600, 6666),
('g-code', 'ミカン', 700, 7777);