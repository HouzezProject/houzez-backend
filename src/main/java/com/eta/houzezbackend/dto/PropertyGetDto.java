package com.eta.houzezbackend.dto;

import com.eta.houzezbackend.util.PropertyType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyGetDto {

    private long id;
    private PropertyType propertyType;

    private String title;
    private int price;
    private int livingRoom;
    private int bedroom;
    private int bathroom;
    private int garage;
    private int landSize;
    private String description;
    private String state;
    private String suburb;
    private int postcode;
    private Double latitude;
    private Double longitude;
    private String indoor;
    private String outdoor;
    private boolean propertyIsNew;
    private String status;
    private Date createdTime;
    private Date updatedTime;
    private AgentGetDto agent;

}
