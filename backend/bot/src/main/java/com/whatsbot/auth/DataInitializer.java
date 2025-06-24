package com.whatsbot.auth;

import com.whatsbot.auth.model.*;
import com.whatsbot.auth.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TenantRepository tenantRepository;
    private final RoleRepository roleRepository;
    private final AuthUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (tenantRepository.count() > 0) {
            return;
        }

        Tenant tenant = new Tenant(null, "demo");
        tenant = tenantRepository.save(tenant);

        Role superAdmin = new Role(null, "SUPER_ADMIN", Set.of());
        superAdmin = roleRepository.save(superAdmin);

        Role tenantAdmin = new Role(null, "TENANT_ADMIN", Set.of());
        tenantAdmin = roleRepository.save(tenantAdmin);

        Role userRole = new Role(null, "USER", Set.of());
        userRole = roleRepository.save(userRole);

        AuthUser admin = new AuthUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setTenant(tenant);
        admin.setRoles(Set.of(superAdmin));
        userRepository.save(admin);
    }
}
