package com.eta.houzezbackend.repository;

import com.eta.houzezbackend.model.Image;
import com.eta.houzezbackend.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ImageRepository extends JpaRepository<Image, Long> {
    Page<Image> findByPropertyId(long id, Pageable paging);

    Optional<Image> findByProperty_Id(long id);


}
