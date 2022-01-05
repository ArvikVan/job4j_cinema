CREATE TABLE if not exists account (
    id SERIAL PRIMARY KEY,
    username varchar,
    phone varchar
);

CREATE TABLE if not exists ticket (
    id SERIAL PRIMARY KEY,
    row_ticket int unique,
    cell int unique,
    account_id int REFERENCES account(id)
);