# Users SCHEMA

# --- !Ups
CREATE SEQUENCE users_id_seq;
CREATE TABLE users (
  id     INTEGER NOT NULL DEFAULT nextval('users_id_seq'),
  name   VARCHAR(100),
  fio    VARCHAR(200),
  accountId INTEGER
);

