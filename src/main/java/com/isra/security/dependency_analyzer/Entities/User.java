package com.isra.security.dependency_analyzer.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Date;
@Entity
@Data
@Table(name = "users")
public class User {
    private boolean isActive;
    private Date createdDate;
    private Date updatedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Username is needed")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is needed")
    @Column(unique = true, nullable = false)
    private String password;

    @NotBlank(message = "Email is needed")
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
