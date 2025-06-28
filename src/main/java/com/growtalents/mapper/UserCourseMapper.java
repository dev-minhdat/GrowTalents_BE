package com.growtalents.mapper;

import com.growtalents.dto.request.UserCourse.UserCourseCreateRequestDTO;
import com.growtalents.dto.response.UserCourse.UserCourseResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Course;
import com.growtalents.model.UserCourse;

public class UserCourseMapper {
    public static UserCourse toEntity(UserCourseCreateRequestDTO dto, String generatedId, AppUser user, Course course) {
        UserCourse uc = new UserCourse();
        uc.setId(generatedId);
        uc.setUser(user);
        uc.setCourse(course);
        uc.setRole(dto.getRole() != null ? com.growtalents.enums.UserCourseRole.valueOf(dto.getRole()) : null);
        return uc;
    }

    public static UserCourseResponseDTO toResponseDTO(UserCourse uc) {
        return UserCourseResponseDTO.builder()
                .id(uc.getId())
                .userId(uc.getUser() != null ? uc.getUser().getUserId() : null)
                .courseId(uc.getCourse() != null ? uc.getCourse().getCourseId() : null)
                .role(uc.getRole() != null ? uc.getRole().name() : null)
                .build();
    }
}
