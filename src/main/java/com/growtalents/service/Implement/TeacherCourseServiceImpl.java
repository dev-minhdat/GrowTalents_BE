package com.growtalents.service.Implement;

import com.growtalents.dto.request.TeacherCourse.TeacherCourseCreateRequestDTO;
import com.growtalents.dto.response.Course.CourseResponseDTO;
import com.growtalents.enums.UserRole;
import com.growtalents.mapper.TeacherCourseMapper;
import com.growtalents.model.AppUser;
import com.growtalents.model.Course;
import com.growtalents.model.TeacherCourse;
import com.growtalents.repository.AppUserRepository;
import com.growtalents.repository.CourseRepository;
import com.growtalents.repository.TeacherCourseRepository;
import com.growtalents.service.Interfaces.TeacherCourseService;
import com.growtalents.service.id.IdSequenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherCourseServiceImpl implements TeacherCourseService {
    
    private final TeacherCourseRepository teacherCourseRepository;
    private final AppUserRepository appUserRepository;
    private final CourseRepository courseRepository;
    private final IdSequenceService idSequenceService;
    
    @Override
    public List<CourseResponseDTO> getActiveCoursesByTeacher(String teacherId) {
        return teacherCourseRepository.findActiveCoursesByTeacher(teacherId);
    }
    
    @Override
    public void assignTeacherToCourse(TeacherCourseCreateRequestDTO dto) {
        // Validate teacher exists and has correct role
        AppUser teacher = appUserRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên với ID: " + dto.getTeacherId()));
        
        if (teacher.getUserRole() != UserRole.TEACHER) {
            throw new RuntimeException("User này không phải là giáo viên");
        }
        
        // Validate course exists
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học với ID: " + dto.getCourseId()));
        
        // Check if teacher is already assigned to this course
        boolean isAlreadyAssigned = teacherCourseRepository
                .existsByTeacherUserIdAndCourseCourseId(dto.getTeacherId(), dto.getCourseId());
        
        if (isAlreadyAssigned) {
            throw new RuntimeException("Giáo viên đã được phân công vào khóa học này");
        }
        
        // Create teacher-course assignment
        int index = idSequenceService.getNextIndex("TeacherCourse");
        TeacherCourse teacherCourse = TeacherCourseMapper.toEntity(dto, index, teacher, course);
        
        teacherCourseRepository.save(teacherCourse);
    }
}
