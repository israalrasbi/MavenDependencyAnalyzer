package com.isra.security.dependency_analyzer.Entities;

import javax.management.relation.Role;
import java.util.Date;
import java.util.UUID;

public class User {
    private boolean isActive;
    private Date createdDate;
    private Date updatedDate;
    private Integer id;
    private String username;
    private String password;
    private Role role;
}
