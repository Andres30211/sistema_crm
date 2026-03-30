CREATE TABLE IF NOT EXISTS post_facebook (
    id VARCHAR(255) PRIMARY KEY,
    fecha_captura TIMESTAMP,
    created_time TIMESTAMP,
    story TEXT,
    message TEXT
);