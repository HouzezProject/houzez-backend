package com.eta.houzezbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
    private String error;
    private List<String> details;
}