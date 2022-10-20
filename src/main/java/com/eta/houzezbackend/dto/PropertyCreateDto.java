package com.eta.houzezbackend.dto;

import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.util.Type;
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
public class PropertyCreateDto {
    @NotNull(message = "Type must not be empty")
    private Type type;

    @NotEmpty(message = "Title must not be empty")
    private String title;

    @NotNull(message = "Price must not be empty")
    private Integer price;

    @NotNull(message = "LivingRoom must not be empty")
    private Integer living_room;

    @NotNull(message = "LivingRoom must not be empty")
    private Integer bedroom;

    @NotNull(message = "bathroom must not be empty")
    private Integer bathroom;
    private Integer garage;

    @NotNull(message = "Land_size must not be empty")
    private Integer land_size;

    @NotEmpty(message = "State must not be empty")
    private String state;

    @NotEmpty(message = "Suburb must not be empty")
    private String suburb;

    @NotNull(message = "Postcode must not be empty")
    private Integer postcode;

    private String description;
    private Double latitude;
    private Double longitude;
    private String status;
    private String indoor;
    private String outdoor;
    private Boolean property_is_new;

    @NotNull(message = "agent_id must not be empty")
    private Agent agent;
}
