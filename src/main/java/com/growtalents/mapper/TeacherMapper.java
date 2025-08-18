package com.growtalents.mapper;

import com.growtalents.dto.request.Teacher.TeacherCreateRequestDTO;
import com.growtalents.dto.response.Teacher.TeacherResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Teacher;
import com.growtalents.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class TeacherMapper {
    
    private final AppUserRepository appUserRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public Teacher toEntity(TeacherCreateRequestDTO dto, String teacherId) {
        LocalDate birthDate = null;
        if (dto.getBirthDate() != null && !dto.getBirthDate().isEmpty()) {
            birthDate = LocalDate.parse(dto.getBirthDate(), formatter);
        }
        
        // Find AppUser if provided
        AppUser appUser = null;
        if (dto.getAppUserId() != null && !dto.getAppUserId().isEmpty()) {
            appUser = appUserRepository.findById(dto.getAppUserId()).orElse(null);
        }
        
        return Teacher.builder()
                .teacherId(teacherId)
                .fullName(dto.getFullName())
                .birthDate(birthDate)
                .gender(dto.getGender())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .specialization(dto.getSpecialization())
                .address(dto.getAddress())
                .educationLevel(dto.getEducationLevel())
                .teachingExperience(dto.getTeachingExperience())
                .appUser(appUser)
                .build();
    }
    
    public TeacherResponseDTO toResponseDTO(Teacher teacher) {
        return TeacherResponseDTO.builder()
                .teacherId(teacher.getTeacherId())
                .fullName(teacher.getFullName())
                .birthDate(teacher.getBirthDate())
                .gender(teacher.getGender())
                .email(teacher.getEmail())
                .phoneNumber(teacher.getPhoneNumber())
                .specialization(teacher.getSpecialization())
                .address(teacher.getAddress())
                .educationLevel(teacher.getEducationLevel())
                .teachingExperience(teacher.getTeachingExperience())
                .appUserId(teacher.getAppUser() != null ? teacher.getAppUser().getUserId() : null)
                .appUserName(teacher.getAppUser() != null ? teacher.getAppUser().getUserName() : null)
                .createdAt(teacher.getCreatedAt())
                .updatedAt(teacher.getUpdatedAt())
                .build();
    }
}
