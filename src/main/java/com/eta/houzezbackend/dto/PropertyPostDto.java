package com.eta.houzezbackend.dto;

import com.eta.houzezbackend.util.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PropertyPostDto {
    @NotNull(message = "Property type must not be empty")
    private PropertyType propertyType;

    @NotEmpty(message = "Title must not be empty")
    private String title;

    @NotNull(message = "Price must not be empty")
    private int price;

    @NotNull(message = "LivingRoom must not be empty")
    private int livingRoom;

    @NotNull(message = "LivingRoom must not be empty")
    private int bedroom;

    @NotNull(message = "bathroom must not be empty")
    private int bathroom;
    private Integer garage;

    @NotNull(message = "Land_size must not be empty")
    private int landSize;

    @NotEmpty(message = "State must not be empty")
    private String state;

    @NotEmpty(message = "Suburb must not be empty")
    private String suburb;

    @NotNull(message = "Postcode must not be empty")
    private int postcode;

    @NotNull(message = "Pre-owned must not be empty")
    private boolean preowned;

    private String description;
    private Double latitude;
    private Double longitude;
    private String status;
    private String indoor;
    private String outdoor;

}
