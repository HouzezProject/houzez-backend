package com.eta.houzezbackend.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class AgentGetDto {


    private long id;
    private String email;
    private String photo;
    public String company;
    public String companyLogo;
    public String phone;
    private String activeLink;
    private Integer ifDelete;
    private String status;
    private Date createdTime;
    private Date updatedTime;


}
