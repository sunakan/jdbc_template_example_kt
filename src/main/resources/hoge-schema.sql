-- spring.sql.init.schema-locations=classpath:hoge-schema.sql
-- hoge-schema.sql
CREATE TABLE IF NOT EXISTS customers (
  id SERIAL,
  last_name VARCHAR(255),
  first_name VARCHAR(255)
);