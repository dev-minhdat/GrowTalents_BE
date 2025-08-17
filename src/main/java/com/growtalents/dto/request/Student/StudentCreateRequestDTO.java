package com.growtalents.dto.request.Student;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentCreateRequestDTO {
    private String fullName;
    private String birthDate; // dd/MM/yyyy format
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String appUserId; // Optional - link to existing AppUser
}
