ALTER TABLE "agent"
    ADD property_id INTEGER,
    ADD FOREIGN KEY(property_id) REFERENCES property (id);