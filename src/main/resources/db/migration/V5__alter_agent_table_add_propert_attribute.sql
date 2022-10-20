ALTER TABLE "agent"
    ADD property_id BIGINT,
    ADD FOREIGN KEY(property_id) REFERENCES property (id);