package org.oenexa.security.model;

import java.util.List;

public record AuthenticatedUser(
    Long userId,
    String uuid,
    String email,
    List<String> roles
) {
}
