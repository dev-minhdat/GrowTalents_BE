package com.growtalents.service.Implement;

import com.growtalents.dto.response.Course.CourseResponseDTO;
import com.growtalents.repository.TeacherCourseRepository;
import com.growtalents.service.Interfaces.TeacherCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TeacherCourseServiceImpl implements TeacherCourseService {
    private final TeacherCourseRepository teacherCourseRepository;
    public List<CourseResponseDTO> getActiveCoursesByTeacher(String teacherId) {
        return teacherCourseRepository.findActiveCoursesByTeacher(teacherId);
    }
}
