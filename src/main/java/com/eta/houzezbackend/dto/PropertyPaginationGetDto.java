package com.eta.houzezbackend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyPaginationGetDto {
    private List<PropertyGetDto> propertyGetDtoList;
    private int totalPageNumber;
}
