CREATE TABLE if not exists ACCOUNT
(
    id IDENTITY,
    account_id LONG NOT NULL,
    account_type NVARCHAR(30)  NOT NULL,
    client_id NVARCHAR(40)  NOT NULL,
    balance DOUBLE NOT NULL,
    withdraw_allowed BOOLEAN NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE if not exists TRANSACTION
(
    transact_id IDENTITY NOT NULL PRIMARY KEY,
    client_id NVARCHAR(40) NOT NULL,
    acc_id LONG NOT NULL,
    account_type NVARCHAR(30) NOT NULL,
    amount DOUBLE NOT NULL,
    PRIMARY KEY(transact_id)
);
