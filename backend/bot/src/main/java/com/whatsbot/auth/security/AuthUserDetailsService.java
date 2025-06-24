package com.whatsbot.auth.security;

import com.whatsbot.auth.exception.AuthException;
import com.whatsbot.auth.model.AuthUser;
import com.whatsbot.auth.repository.AuthUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final AuthUserRepository repository;

    public AuthUserDetailsService(AuthUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("Tenant required");
    }

    public UserDetails loadByUsernameAndTenant(String username, String tenant) {
        AuthUser user = repository.findByUsernameAndTenant_Name(username, tenant)
                .orElseThrow(() -> new AuthException("Invalid credentials"));
        List<GrantedAuthority> auths = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), auths);
    }
}
