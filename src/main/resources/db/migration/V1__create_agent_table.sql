CREATE TABLE "agent"
(
    "id"           BIGSERIAL PRIMARY KEY,
    "name"         VARCHAR(255),
    "avatar"       VARCHAR(512),
    "company_name" VARCHAR(255),
    "company_logo" VARCHAR(512),
    "email"        VARCHAR(255) UNIQUE NOT NULL,
    "password"     VARCHAR(64)         NOT NULL,
    "phone_number" VARCHAR(255),
    "deleted"      BOOL DEFAULT FALSE  NOT NULL,
    "activated"    BOOL DEFAULT FALSE  NOT NULL,
    "created_time" TIMESTAMP WITH TIME ZONE,
    "updated_time" TIMESTAMP WITH TIME ZONE
)