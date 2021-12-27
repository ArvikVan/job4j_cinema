CREATE TABLE if not exists account (
                         id SERIAL PRIMARY KEY,
                         username VARCHAR NOT NULL,
                         email VARCHAR NOT NULL UNIQUE,
                         phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE if not exists ticket (
                        id SERIAL PRIMARY KEY,
                        session_id INT NOT NULL,
                        row INT NOT NULL,
                        cell INT NOT NULL,
                        account_id INT NOT NULL REFERENCES account(id)
);