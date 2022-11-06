CREATE TABLE "property"
(
    "id"            BIGSERIAL PRIMARY KEY,
    "title"         VARCHAR(64),
    "property_type" VARCHAR(64),
    "price"         INTEGER,
    "living_room"   INTEGER,
    "bedroom"       INTEGER,
    "bathroom"      INTEGER,
    "garage"        INTEGER,
    "land_size"     INTEGER,
    "description"   VARCHAR(128),
    "state"         VARCHAR(64),
    "suburb"        VARCHAR(64),
    "postcode"      INTEGER,
    "latitude"      Numeric,
    "longitude"     Numeric,
    "indoor"        VARCHAR(64),
    "outdoor"       VARCHAR(64),
    "preowned"      Boolean,
    "status"        VARCHAR(64),
    "agent_id"      BIGINT                   NOT NULL REFERENCES "agent" (id),
    "created_time"  TIMESTAMP WITH TIME ZONE NOT NULL,
    "updated_time"  TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE "property_image"
(
    "id"           BIGSERIAL PRIMARY KEY,
    "url"          VARCHAR(512),
    "tag"          VARCHAR(64),
    "property_id"  BIGINT                   NOT NULL REFERENCES "property" (id),
    "created_time" TIMESTAMP WITH TIME ZONE NOT NULL,
    "updated_time" TIMESTAMP WITH TIME ZONE NOT NULL
);
