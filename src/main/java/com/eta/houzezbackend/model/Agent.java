package com.eta.houzezbackend.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
@Table(name = "agent")
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
    private Boolean ifDelete;

    private String activeLink;

    @Column( nullable = false)
    private Boolean status;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;



}
