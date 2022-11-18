package com.eta.houzezbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ImagePostDto {
    @NotEmpty(message = "url type must not be empty")
    private String url;

    @NotEmpty(message = "tag must not be empty")
    private String tag;
}
