CREATE TABLE customer
(
    id        BIGSERIAL,
    created   BIGINT       NOT NULL,
    updated   BIGINT       NOT NULL,
    full_name VARCHAR(50)  NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    phone     VARCHAR(14),
    is_active BOOLEAN DEFAULT true,

    CONSTRAINT customer_PK PRIMARY KEY (id)
);
