package com.whatsbot.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsbot.auth.model.AuthUser;
import com.whatsbot.auth.model.Tenant;
import com.whatsbot.auth.repository.AuthUserRepository;
import com.whatsbot.auth.repository.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private AuthUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;

    private Tenant tenant;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        tenantRepository.deleteAll();
        tenant = tenantRepository.save(new Tenant(null, "acme"));
        AuthUser u = new AuthUser();
        u.setUsername("john");
        u.setPassword(passwordEncoder.encode("pwd"));
        u.setTenant(tenant);
        userRepository.save(u);
    }

    @Test
    void loginSuccess() throws Exception {
        var req = new Login("john", "pwd", "acme");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    private record Login(String username, String password, String tenant) {}
}
