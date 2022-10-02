package com.eta.houzezbackend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DuplicateEmailCheckDto {
    private boolean DuplicateEmailCheckResultDto = true;
    private String email;
}
