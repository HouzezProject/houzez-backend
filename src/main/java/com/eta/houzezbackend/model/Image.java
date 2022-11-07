package com.eta.houzezbackend.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "property_image")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private String tag;
    @JoinColumn(name = "property_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Property property;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;
}
