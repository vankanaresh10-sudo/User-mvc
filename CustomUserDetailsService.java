package com.example.securemvc.security;

import com.example.securemvc.model.AppUser;
import com.example.securemvc.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository users;

    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = users.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(user.getUsername())
            .password(user.getPasswordHash())
            .authorities(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
            .build();
    }
}
