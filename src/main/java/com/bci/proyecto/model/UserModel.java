package com.bci.proyecto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "USER_ID")
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "AUDIT_CREATED_DATE")
    private LocalDateTime auditCreatedDate;

    @Column(name = "AUDIT_USER_CREATOR")
    private String auditUserCreator;

    @Column(name = "AUDIT_MODIFICATION_DATE")
    private LocalDateTime auditModificationDate;

    @Column(name = "AUDIT_USER_MODIFIER")
    private String auditUserModifier;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private String token;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PhoneModel> phones;

    @PrePersist
    protected void onCreate() {
        auditCreatedDate = LocalDateTime.now();
        lastLogin = auditCreatedDate;
        active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        auditModificationDate = LocalDateTime.now();
    }
}
