DROP TABLE IF EXISTS credit_cards;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS clients;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE clients
(
    id         INTEGER   DEFAULT global_seq.nextval PRIMARY KEY,
    name       VARCHAR                 NOT NULL,
    email      VARCHAR                 NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON clients (email);

CREATE TABLE accounts
(
    id         INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
    clients_id INTEGER NOT NULL,
    number     VARCHAR NOT NULL,
    amount     DOUBLE NOT NULL,
    currency   VARCHAR NOT NULL,
    FOREIGN KEY (clients_id) REFERENCES clients (id) ON DELETE CASCADE
);

CREATE TABLE credit_cards
(
    id         INTEGER   DEFAULT global_seq.nextval PRIMARY KEY,
    account_id INTEGER                 NOT NULL,
    number     VARCHAR                 NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE
);