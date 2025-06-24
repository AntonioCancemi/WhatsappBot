package com.whatsbot.modules.auth;

import com.whatsbot.modules.auth.model.*;
import com.whatsbot.modules.auth.repository.*;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TenantRepository tenantRepository;
    private final RoleRepository roleRepository;
    private final AccessLevelRepository accessLevelRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (tenantRepository.count() > 0) {
            return;
        }

        Tenant tenant = tenantRepository.save(new Tenant(null, "demo"));

        AccessLevel full = accessLevelRepository.save(new AccessLevel(null, "FULL_ACCESS"));
        AccessLevel readOnly = accessLevelRepository.save(new AccessLevel(null, "READ_ONLY"));
        accessLevelRepository.save(new AccessLevel(null, "CUSTOM"));

        Role superAdmin = roleRepository.save(new Role(null, "SUPER_ADMIN", Set.of(full)));
        Role tenantAdmin = roleRepository.save(new Role(null, "TENANT_ADMIN", Set.of(full)));
        Role userRole = roleRepository.save(new Role(null, "USER", Set.of(readOnly)));
 

        User admin = new User();
        admin.setEmail("admin@demo.com");
        admin.setFullName("Admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setTenant(tenant);
         admin.setRole(superAdmin);
        admin.setAccessLevel(full);
 
        userRepository.save(admin);
    }
}
