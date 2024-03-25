CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    payer BIGINT NOT NULL,
    payee BIGINT NOT NULL,
    value NUMERIC(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
