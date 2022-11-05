CREATE TABLE "agent"
(
    "id"           BIGSERIAL PRIMARY KEY,
    "name"         VARCHAR(255),
    "email"        VARCHAR(255) UNIQUE NOT NULL,
    "password"     VARCHAR(1024)       NOT NULL,
    "deleted"      BOOL DEFAULT FALSE  NOT NULL,
    "activated"    BOOL DEFAULT FALSE  NOT NULL,
    "created_time" TIMESTAMP WITH TIME ZONE,
    "updated_time" TIMESTAMP WITH TIME ZONE
)


