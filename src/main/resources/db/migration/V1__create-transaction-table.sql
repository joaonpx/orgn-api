CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE transaction (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             date DATE NOT NULL,
                             type VARCHAR(255) NOT NULL,
                             description VARCHAR(255) NOT NULL,
                             amount NUMERIC(10, 2) NOT NULL
);