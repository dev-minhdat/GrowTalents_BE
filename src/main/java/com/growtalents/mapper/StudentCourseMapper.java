package com.growtalents.mapper;

import com.growtalents.dto.request.StudentCourse.StudentCourseCreateRequestDTO;
import com.growtalents.dto.response.StudentCourse.StudentCourseResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Course;
import com.growtalents.model.StudentCourse;

public class StudentCourseMapper {
    public static StudentCourse toEntity(StudentCourseCreateRequestDTO dto, String generatedId, AppUser student, Course course) {
        StudentCourse sc = new StudentCourse();
        sc.setStudentCourseId(generatedId);
        sc.setStudent(student);
        sc.setCourse(course);
        sc.setRegistrationDate(dto.getRegistrationDate() != null ? java.time.LocalDate.parse(dto.getRegistrationDate()) : null);
        sc.setDroppedOutDate(dto.getDroppedOutDate() != null ? java.time.LocalDate.parse(dto.getDroppedOutDate()) : null);
        sc.setStatus(dto.getStatus() != null ? com.growtalents.enums.StudentCourseStatus.valueOf(dto.getStatus()) : null);
        return sc;
    }

    public static StudentCourseResponseDTO toResponseDTO(StudentCourse sc) {
        return StudentCourseResponseDTO.builder()
                .studentCourseId(sc.getStudentCourseId())
                .studentId(sc.getStudent() != null ? sc.getStudent().getUserId() : null)
                .courseId(sc.getCourse() != null ? sc.getCourse().getCourseId() : null)
                .registrationDate(sc.getRegistrationDate() != null ? sc.getRegistrationDate().toString() : null)
                .droppedOutDate(sc.getDroppedOutDate() != null ? sc.getDroppedOutDate().toString() : null)
                .status(sc.getStatus() != null ? sc.getStatus().name() : null)
                .build();
    }
}
