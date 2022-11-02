package com.eta.houzezbackend.dto;

import com.eta.houzezbackend.util.Type;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyGetDto {

    private long id;
    private Type type;

    private String title;
    private Integer price;
    private Integer livingRoom;
    private Integer bedroom;
    private Integer bathroom;
    private Integer garage;
    private Integer landSize;
    private String description;
    private String state;
    private String suburb;
    private Integer postcode;
    private Double latitude;
    private Double longitude;
    private String indoor;
    private String outdoor;
    private Boolean propertyIsNew;
    private String status;
    private Date createdTime;
    private Date updatedTime;
    private AgentGetDto agent;

}
