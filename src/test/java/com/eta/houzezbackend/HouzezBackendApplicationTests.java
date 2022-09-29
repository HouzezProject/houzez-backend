package com.eta.houzezbackend;

import com.eta.houzezbackend.service.AgentServiceTest;
import com.eta.houzezbackend.service.JwtServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JwtServiceTest.class,
        AgentServiceTest.class
})

@SpringBootTest
class HouzezBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
