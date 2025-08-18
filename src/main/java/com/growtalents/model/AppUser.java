package com.growtalents.model;

import com.growtalents.enums.UserRole;
import com.growtalents.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "password", nullable = false)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole userRole;

    @Column(name = "phone")
    private String userPhone;

    // Thêm các fields mới cho teacher/student detail
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "specialization") // Cho teacher - chuyên môn
    private String specialization;

    @Lob
    @Column(name = "address")
    private String address;

    @Column(name = "education_level") // Cho teacher - trình độ học vấn
    private String educationLevel;

    @Column(name = "teaching_experience") // Cho teacher - kinh nghiệm giảng dạy
    private String teachingExperience;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus userStatus;

    @Column(name = "parent_name")
    private String userParentName;

    @Column(name = "parent_phone", length = 10)
    private String userParentPhone;

    @Lob
    @Column(name = "description" )
    private String description;

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
