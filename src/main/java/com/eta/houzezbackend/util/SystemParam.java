package com.eta.houzezbackend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SystemParam {
    @Value("${system-param.secret-key}")
    private String SECRET_KEY;
}
