package com.growtalents.mapper;

import com.growtalents.dto.request.Student.StudentCreateRequestDTO;
import com.growtalents.dto.response.Student.StudentResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Student;
import com.growtalents.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class StudentDetailMapper {
    
    private final AppUserRepository appUserRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public Student toEntity(StudentCreateRequestDTO dto, String studentId) {
        LocalDate birthDate = null;
        if (dto.getBirthDate() != null && !dto.getBirthDate().isEmpty()) {
            birthDate = LocalDate.parse(dto.getBirthDate(), formatter);
        }
        
        // Find AppUser if provided
        AppUser appUser = null;
        if (dto.getAppUserId() != null && !dto.getAppUserId().isEmpty()) {
            appUser = appUserRepository.findById(dto.getAppUserId()).orElse(null);
        }
        
        return Student.builder()
                .studentId(studentId)
                .fullName(dto.getFullName())
                .birthDate(birthDate)
                .gender(dto.getGender())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .appUser(appUser)
                .build();
    }
    
    public StudentResponseDTO toResponseDTO(Student student) {
        return StudentResponseDTO.builder()
                .studentId(student.getStudentId())
                .fullName(student.getFullName())
                .birthDate(student.getBirthDate())
                .gender(student.getGender())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .address(student.getAddress())
                .appUserId(student.getAppUser() != null ? student.getAppUser().getUserId() : null)
                .appUserName(student.getAppUser() != null ? student.getAppUser().getUserName() : null)
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
