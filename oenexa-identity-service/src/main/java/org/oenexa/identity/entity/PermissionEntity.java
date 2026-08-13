package org.oenexa.identity.entity;

import jakarta.persistence.*;

@Entity
@Table(name="permissions")
public class PermissionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String resource;
    private String action;
    private String description;
}
