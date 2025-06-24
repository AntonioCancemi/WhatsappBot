package com.whatsbot.auth;

import com.whatsbot.auth.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JwtServiceInvalidTest {

    @Autowired
    JwtService jwtService;

    @Test
    void invalidTokenReturnsNull() {
        String token = "invalid";
        assertThat(jwtService.extractUser(token)).isNull();
    }
}
