package com.eta.houzezbackend.repository;

import com.eta.houzezbackend.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
