INSERT INTO Users (user_name) VALUES ('Alice');
INSERT INTO Users (user_name) VALUES ('Bob');

INSERT INTO Subscription (user_id, name, description, type) VALUES (
    1,
    'YouTube Premium',
    'Premium access to YouTube',
    'YOUTUBE_PREMIUM'
);

INSERT INTO Subscription (user_id, name, description, type) VALUES (
    1,
    'Spotify',
    'Music streaming service',
    'SPOTIFY'
);

INSERT INTO Subscription (user_id, name, description, type) VALUES (
    2,
    'Netflix',
    'Streaming video service',
    'NETFLIX'
);