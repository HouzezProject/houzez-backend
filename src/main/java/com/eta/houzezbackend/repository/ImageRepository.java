package com.eta.houzezbackend.repository;

import com.eta.houzezbackend.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Long> {
    Page<Image> findByPropertyId(long id, Pageable paging);

    List<Image> findByPropertyId(long id);


}
