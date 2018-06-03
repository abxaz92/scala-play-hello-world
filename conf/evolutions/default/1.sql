# Tasks SCHEMA

# --- !Ups
CREATE SEQUENCE task_id_seq;
CREATE TABLE task (
  id     INTEGER NOT NULL DEFAULT nextval('task_id_seq'),
  label  VARCHAR(2000),
  who    VARCHAR(4),
  mytime VARCHAR(100),
  ready  INTEGER
)
