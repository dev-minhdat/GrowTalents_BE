package com.growtalents.dto.response.Student;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentResponseDTO {
    private String studentId;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String appUserId; // Linked AppUser ID (if any)
    private String appUserName; // Linked AppUser name (if any)
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
