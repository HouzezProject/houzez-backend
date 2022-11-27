package com.eta.houzezbackend.model;


import com.eta.houzezbackend.util.PropertyType;
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
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType propertyType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int livingRoom;

    @Column(nullable = false)
    private int bedroom;

    @Column(nullable = false)
    private int bathroom;

    @Column(nullable = false)
    private int garage;

    @Column(nullable = false)
    private int landSize;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String suburb;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private int postcode;

    @Column(nullable = false)
    private boolean preowned;

    private Double latitude;
    private Double longitude;
    private String indoor;
    private String outdoor;
    private String status;

    @JoinColumn(name = "agent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Agent agent;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(orphanRemoval = true, mappedBy = "property")
    private List<Image> image = new ArrayList<>();

    @CreatedDate
    private Date createdTime;
    @LastModifiedDate
    private Date updatedTime;

}
