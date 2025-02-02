CREATE TABLE transaction (
                             id UUID PRIMARY KEY NOT NULL,
                             date DATE NOT NULL,
                             type VARCHAR(255) NOT NULL,
                             description VARCHAR(255) NOT NULL,
                             amount NUMERIC NOT NULL
);