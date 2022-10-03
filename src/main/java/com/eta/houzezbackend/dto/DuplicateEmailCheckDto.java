package com.eta.houzezbackend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DuplicateEmailCheckDto {
    private boolean duplicateEmailCheckResultDto = true;
    private String email;
}
