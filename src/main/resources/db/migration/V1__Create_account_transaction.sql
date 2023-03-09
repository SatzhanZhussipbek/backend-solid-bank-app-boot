CREATE TABLE if not exists PERSON
(
    client_id IDENTITY,
    client_name NVARCHAR(80) NOT NULL,
    client_password NVARCHAR(300) NOT NULL,
    primary key(client_id)
);

CREATE TABLE if not exists ACCOUNT
(
    id IDENTITY,
    account_id LONG NOT NULL,
    account_type NVARCHAR(30)  NOT NULL,
    client_id LONG  NOT NULL,
    balance DOUBLE NOT NULL,
    withdraw_allowed BOOLEAN NOT NULL,
    FOREIGN KEY(client_id) REFERENCES PERSON(client_id),
    PRIMARY KEY(id)
);

CREATE TABLE if not exists TRANSACTION
(
    transact_id IDENTITY NOT NULL PRIMARY KEY,
    client_id LONG NOT NULL,
    acc_id LONG NOT NULL,
    account_type NVARCHAR(30) NOT NULL,
    amount DOUBLE NOT NULL,
    PRIMARY KEY(transact_id)
);

