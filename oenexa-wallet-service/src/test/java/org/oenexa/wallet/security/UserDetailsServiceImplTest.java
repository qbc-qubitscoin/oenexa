package org.oenexa.wallet.security;

import org.junit.jupiter.api.Test;
import org.oenexa.wallet.entity.Role;
import org.oenexa.wallet.entity.User;
import org.oenexa.wallet.repository.RoleRepository;
import org.oenexa.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(UserDetailsServiceImpl.class)
class UserDetailsServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void testLoadUserByUsername_Success() {
        // Given
        Role role = new Role(null, "ROLE_USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setEmail("testuser@test.com");
        user.setPassword("hashedpassword");
        user.setRoles(Set.of(role));
        userRepository.save(user);

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser@test.com");

        // Then
        assertNotNull(userDetails);
        assertEquals("testuser@test.com", userDetails.getUsername());
        assertEquals("hashedpassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("unknown@test.com");
        });
    }
}
