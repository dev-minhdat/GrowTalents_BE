package com.growtalents.dto.request.Teacher;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeacherCreateRequestDTO {
    private String fullName;
    private String birthDate; // dd/MM/yyyy format
    private String gender;
    private String email;
    private String phoneNumber;
    private String specialization;
    private String address;
    private String educationLevel;
    private String teachingExperience;
    private String appUserId; // Optional - link to existing AppUser
}
