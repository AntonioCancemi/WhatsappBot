package com.whatsbot.modules.auth.security;

import com.whatsbot.modules.auth.exception.AuthException;
import com.whatsbot.modules.auth.model.User;
import com.whatsbot.modules.auth.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public AuthUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("Tenant required");
    }

    public UserDetails loadByEmailAndTenant(String email, String tenant) {
        User user = repository.findByEmailAndTenant_Name(email, tenant)
                .orElseThrow(() -> new AuthException("Invalid credentials"));
        List<GrantedAuthority> auths = List.of(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), auths);
    }
}
