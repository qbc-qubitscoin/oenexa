package org.oenexa.identity.entity;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class RoleEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
}
