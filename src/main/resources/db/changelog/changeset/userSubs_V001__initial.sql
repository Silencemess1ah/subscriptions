CREATE TABLE Users (
    user_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_name VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE Subscription (
    sub_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT NOT NULL,
    name VARCHAR(32) NOT NULL,
    description TEXT,
    type VARCHAR(32) NOT NULL,

    CONSTRAINT fk_subscription_user
        FOREIGN KEY (user_id)
        REFERENCES Users(user_id)
        ON DELETE CASCADE,

    CONSTRAINT uq_user_subscription
        UNIQUE (user_id, name)
);