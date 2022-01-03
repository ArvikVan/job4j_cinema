CREATE TABLE if not exists account (
    id SERIAL PRIMARY KEY,
    username varchar,
    phone varchar
);

CREATE TABLE if not exists ticket (
    id SERIAL PRIMARY KEY,
    row_ticket int,
    cell int,
    account_id int REFERENCES account(id)
);