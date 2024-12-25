CREATE TABLE account (
    id UUID PRIMARY KEY,
    name VARCHAR(20),
    email VARCHAR(20),
    password VARCHAR(20),
    createdAt DATE
);

CREATE TABLE coin (
    id                 VARCHAR(50) PRIMARY KEY,
    symbol VARCHAR(50),
    name VARCHAR(50),
    image VARCHAR,
    market_cap         DECIMAL,
    market_cap_rank    NUMERIC,
    circulating_supply DECIMAL,
    total_supply       DECIMAL,
    max_supply         DECIMAL,
    ath                DECIMAL,
    ath_date           DATE,
    last_updated       DATE
);

CREATE TABLE balance (
    id         UUID PRIMARY KEY,
    account_id UUID NOT NULL,
    coin_id VARCHAR(50) NOT NULL,
    balance    DECIMAL(18, 8) DEFAULT 0,
    created_at TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE,
    FOREIGN KEY (coin_id) REFERENCES coin (id) ON DELETE CASCADE
);

CREATE TABLE trades (
    id                           UUID PRIMARY KEY,
    account_id                   UUID                                           NOT NULL,
    pair_cryptocurrency_left_id  VARCHAR(10)                                    NOT NULL,
    pair_cryptocurrency_right_id VARCHAR(10)                                    NOT NULL,
    side                         VARCHAR(10) CHECK (side IN ('BUY', 'SELL'))    NOT NULL,
    price                        DECIMAL(18, 8)                                 NOT NULL,
    quantity                     DECIMAL(18, 8)                                 NOT NULL,
    total_value                  DECIMAL(18, 8)                                 NOT NULL,
    role                         VARCHAR(18) CHECK (role IN ('MAKER', 'TAKER')) NOT NULL,
    created_at                   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE,
    FOREIGN KEY (pair_cryptocurrency_left_id) REFERENCES coin (id) ON DELETE CASCADE,
    FOREIGN KEY (pair_cryptocurrency_right_id) REFERENCES coin (id) ON DELETE CASCADE
);

