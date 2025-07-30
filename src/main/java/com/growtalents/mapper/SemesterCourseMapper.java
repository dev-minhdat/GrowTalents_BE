package com.growtalents.mapper;

import com.growtalents.dto.request.SemesterCourse.SemesterCourseCreateRequestDTO;
import com.growtalents.dto.response.SemesterCourse.SemesterCourseResponseDTO;
import com.growtalents.model.Course;
import com.growtalents.model.Semester;
import com.growtalents.model.SemesterCourse;

public class SemesterCourseMapper {
    public static SemesterCourse toEntity(SemesterCourseCreateRequestDTO dto, String generatedId, Semester semester, Course course) {
        SemesterCourse sc = new SemesterCourse();
        sc.setSemesterCourseId(generatedId);
        sc.setSemester(semester);
        sc.setCourse(course);
        return sc;
    }

    public static SemesterCourseResponseDTO toResponseDTO(SemesterCourse sc) {
        return SemesterCourseResponseDTO.builder()
                .semesterCourseId(sc.getSemesterCourseId())
                .semesterId(sc.getSemester() != null ? sc.getSemester().getSemesterId() : null)
                .courseId(sc.getCourse() != null ? sc.getCourse().getCourseId() : null)
                .build();
    }
}
