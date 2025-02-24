package com.isra.security.dependency_analyzer.Entities;

import com.isra.security.dependency_analyzer.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
@Entity
@Table(name = "users")
public class User {

    private boolean isActive;
    @Column(updatable = false)
    private Date createdDate;
    private Date updatedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Username is needed")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is needed")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Email is needed")
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
