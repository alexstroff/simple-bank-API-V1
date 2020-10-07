DELETE
FROM credit_cards;
DELETE
FROM accounts;
DELETE
FROM clients;

INSERT INTO clients (name, email)
VALUES ('Vasay', 'vasyaTheGreat@mail.ru'), //100_000
       ('Petya', 'petayTheBest@yandex.ru');//100_001

INSERT INTO accounts (clients_id, number, amount, currency)
VALUES ('100000', '1111111111', '1000', 'RUB'), //100_002
       ('100001', '2222222222', '2000', 'RUB'); //100_003

INSERT INTO credit_cards (account_id, number)
VALUES ('100002', '9991111111'), //100_004
       ('100003', '7772222222'); //100_005