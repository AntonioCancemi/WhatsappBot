package com.whatsbot.modules.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsbot.modules.auth.model.User;
import com.whatsbot.modules.auth.model.Tenant;
import com.whatsbot.modules.auth.model.Role;
import com.whatsbot.modules.auth.model.AccessLevel;
import com.whatsbot.modules.auth.repository.ContactRepository;
import com.whatsbot.modules.auth.repository.TenantRepository;
import com.whatsbot.modules.auth.repository.RoleRepository;
import com.whatsbot.modules.auth.repository.AccessLevelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("whatsbot_test")
            .withUsername("admin")
            .withPassword("admin");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private ContactRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccessLevelRepository accessLevelRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;

    private Tenant tenant;
    private Role role;
    private AccessLevel level;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        tenantRepository.deleteAll();
        roleRepository.deleteAll();
        accessLevelRepository.deleteAll();

        tenant = tenantRepository.save(new Tenant(null, "acme"));
        level = accessLevelRepository.save(new AccessLevel(null, "FULL_ACCESS"));
        role = roleRepository.save(new Role(null, "USER", java.util.Set.of(level)));

        User u = new User();
        u.setEmail("john@example.com");
        u.setFullName("John");
        u.setPassword(passwordEncoder.encode("pwd"));
        u.setTenant(tenant);
        u.setRole(role);
        u.setAccessLevel(level);
        userRepository.save(u);
    }

    @Test
    void loginSuccess() throws Exception {
        var req = new Login("john@example.com", "pwd");
        mockMvc.perform(post("/api/auth/login")
                        .header("X-Tenant-ID", tenant.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void loginFailure() throws Exception {
        var req = new Login("john@example.com", "wrong");
        mockMvc.perform(post("/api/auth/login")
                        .header("X-Tenant-ID", tenant.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    private record Login(String email, String password) {}
}
