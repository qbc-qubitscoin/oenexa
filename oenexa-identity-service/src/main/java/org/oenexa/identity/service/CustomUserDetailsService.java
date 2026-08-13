package org.oenexa.identity.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.oenexa.identity.entity.UserEntity;
import org.oenexa.identity.repository.UserRepository;
import org.oenexa.security.model.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        List<SimpleGrantedAuthority> authorities = Arrays.stream(user.getRoles().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return UserPrincipal.builder()
                .id(user.getId())
                .uuid(user.getUuid())
                .email(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(authorities)
                .enabled("ACTIVE".equals(user.getAccountStatus())) // Using accountStatus to map active flag
                .build();
    }
}
