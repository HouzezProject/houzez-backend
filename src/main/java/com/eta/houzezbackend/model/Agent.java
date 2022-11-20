package com.eta.houzezbackend.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean deleted;

    @Column(nullable = false)
    private Boolean activated;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(orphanRemoval = true, mappedBy = "agent")
    private List<Property> property = new ArrayList<>();

}
