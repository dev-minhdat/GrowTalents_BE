package com.growtalents.model;

import com.growtalents.enums.UserRole;
import com.growtalents.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "AppUser")
public class AppUser {
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "name")
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "password", nullable = false)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole userRole;

    @Column(name = "phone")
    private String userPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus userStatus;

    @Column(name = "parent_name")
    private String userParentName;

    @Column(name = "parent_phone", length = 10)
    private String userParentPhone;

    @PrePersist
    public void prePersist() {
        if (this.userStatus == null) {
            this.userStatus = UserStatus.ACTIVE;
        }
        if (this.userRole == null) {
            this.userRole = UserRole.STUDENT;
        }
    }

}
