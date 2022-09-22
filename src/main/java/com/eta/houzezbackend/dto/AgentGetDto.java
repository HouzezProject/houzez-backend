package com.eta.houzezbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AgentGetDto {


    private long id;
    private String name;
    private String email;
    private String photo;
    private String company;
    private String companyLogo;
    private String phone;
    private String activeLink;
    private Boolean ifDelete;
    private Boolean status;
    private Date createdTime;
    private Date updatedTime;


}
