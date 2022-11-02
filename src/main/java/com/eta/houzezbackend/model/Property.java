package com.eta.houzezbackend.model;

import com.eta.houzezbackend.util.Type;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "property")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer livingRoom;

    @Column(nullable = false)
    private Integer bedroom;

    @Column(nullable = false)
    private Integer bathroom;

    @Column(nullable = false)
    private Integer garage;

    @Column(nullable = false)
    private Integer landSize;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String suburb;

    @Column(nullable = false)
    private Integer postcode;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String indoor;

    @Column(nullable = false)
    private String outdoor;

    @Column(nullable = false)
    private Boolean propertyIsNew;

    @Column(nullable = false)
    private String status;

    @JoinColumn(name = "agent_id")
    @ManyToOne
    private Agent agent;


    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;


}