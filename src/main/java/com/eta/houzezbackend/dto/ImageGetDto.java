package com.eta.houzezbackend.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageGetDto {
    private long id;
    private String url;
    private String tag;
    private Date createdTime;
    private Date updatedTime;
}
