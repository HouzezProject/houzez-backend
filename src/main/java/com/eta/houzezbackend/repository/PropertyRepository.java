package com.eta.houzezbackend.repository;

import com.eta.houzezbackend.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query(value = "select * from property as a left join agent as b on a.agent_id=b.id", nativeQuery = true)
    Page<Property> findAByAgentOrderById(long agent_id, Pageable pageable);

}
