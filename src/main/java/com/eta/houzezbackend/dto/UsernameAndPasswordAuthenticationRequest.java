package com.eta.houzezbackend.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class UsernameAndPasswordAuthenticationRequest {
    private String email;
    private String password;
}
