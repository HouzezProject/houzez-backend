package com.eta.houzezbackend.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentGetDto {

    private long id;
    private String name;
    private String email;
    private Boolean deleted;
    private Boolean activated;
    private Date createdTime;
    private Date updatedTime;
    private String icon;

}
