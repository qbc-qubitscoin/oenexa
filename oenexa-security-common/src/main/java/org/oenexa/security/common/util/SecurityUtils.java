package org.oenexa.security.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtils {

    private SecurityUtils() {
        // Utility class
    }

    public static UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null && !authentication.getName().equals("anonymousUser")) {
             try {
                 return UUID.fromString(authentication.getName());
             } catch (IllegalArgumentException e) {
                 throw new RuntimeException("User ID in token is not a valid UUID: " + authentication.getName(), e);
             }
        }
        throw new RuntimeException("User not authenticated or invalid token");
    }
}
