package com.eta.houzezbackend.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "agent")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String photo;

    @Column(nullable = true)
    private String company;

    @Column(nullable = true)
    private String companyLogo;

    //@Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column( nullable = false)
    private String password;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = false)
    private Integer ifDelete;

    private String activeLink;

    @Column( nullable = false)
    private String status;

    @CreatedDate
    @Column( nullable = true)
    private Date createdTime;

    @LastModifiedDate
    @Column( nullable = true)
    private Date updatedTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Agent agent = (Agent) o;
        return id != null && Objects.equals(id, agent.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
