package com.eta.houzezbackend.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

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

    private String photo;

    private String company;

    private String companyLogo;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column( nullable = false)
    private String password;

    private String phone;

    @Column(nullable = false)
    private Boolean deleted;

    private String activeLink;

    @Column( nullable = false)
    private Boolean activated;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;



}
