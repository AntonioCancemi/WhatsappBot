package com.whatsbot.modules.auth;

import com.whatsbot.modules.auth.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class JwtServiceTest {

    @Autowired
    JwtService jwtService;

    @Test
    void roundTrip() {
        UUID uid = UUID.randomUUID();
        UUID tid = UUID.randomUUID();
        String token = jwtService.generateToken(uid, "user", tid);
        assertThat(jwtService.extractUser(token)).isEqualTo(uid);
        assertThat(jwtService.extractTenant(token)).isEqualTo(tid);
        assertThat(jwtService.extractUsername(token)).isEqualTo("user");
    }
}
