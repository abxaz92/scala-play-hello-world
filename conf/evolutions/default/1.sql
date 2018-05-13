# Tasks schema

# --- !Ups
CREATE SEQUENCE task_id_seq;
CREATE TABLE task (
  id INTEGER NOT NULL DEFAULT nextval('task_id_seq'),
  label varchar(2000),
  who varchar(4),
  mytime varchar(100),
  ready integer
)
DROP TABLE task;
DROP TABLE task_id_seq;