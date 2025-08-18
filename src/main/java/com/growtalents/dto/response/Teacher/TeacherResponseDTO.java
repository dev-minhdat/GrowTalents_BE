package com.growtalents.dto.response.Teacher;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeacherResponseDTO {
    private String teacherId;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String email;
    private String phoneNumber;
    private String specialization;
    private String address;
    private String educationLevel;
    private String teachingExperience;
    private String appUserId; // Linked AppUser ID (if any)
    private String appUserName; // Linked AppUser name (if any)
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
