-- spring.sql.init.schema-locations=classpath:hoge-schema.sql
-- hoge-schema.sql
DROP TABLE IF EXISTS customers;
CREATE TABLE IF NOT EXISTS customers (
  id SERIAL,
  last_name VARCHAR(255),
  first_name VARCHAR(255)
);

DROP TABLE IF EXISTS products ;
CREATE TABLE IF NOT EXISTS products (
  id SERIAL NOT NULL,
  code VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  price INT NOT NULL,
  cost INT NOT NULL,
  PRIMARY KEY (id)
);