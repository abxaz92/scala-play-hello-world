# Paypack SCHEMA

# --- !Ups
CREATE SEQUENCE paypack_id_seq;
CREATE TABLE paypack (
  id        INTEGER NOT NULL DEFAULT nextval('paypack_id_seq'),
  name      VARCHAR(2000),
  cost      DOUBLE PRECISION,
  payPeriod REAL
);