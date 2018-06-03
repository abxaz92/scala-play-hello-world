# Payment SCHEMA

# --- !Ups
CREATE SEQUENCE payment_id_seq;
CREATE TABLE payment (
  id           INTEGER NOT NULL DEFAULT nextval('payment_id_seq'),
  payTimestamp TIMESTAMPTZ,
  userId       VARCHAR(2000),
  comment      VARCHAR(5000),
  paymentType  VARCHAR(255),
  amount       DOUBLE PRECISION,
  paypackId    INTEGER,
  status       VARCHAR(255),
  balance      DOUBLE PRECISION

);