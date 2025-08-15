package com.growtalents.service.Implement;

import com.growtalents.dto.request.Course.CourseCreateRequestDTO;
import com.growtalents.dto.response.Course.CourseResponseDTO;
import com.growtalents.enums.CourseStatus;
import com.growtalents.enums.CourseType;
import com.growtalents.exception.BadRequestException;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.CourseMapper;
import com.growtalents.model.AppUser;
import com.growtalents.model.Course;
import com.growtalents.repository.AppUserRepository;
import com.growtalents.repository.CourseRepository;
import com.growtalents.service.Interfaces.CourseService;
import com.growtalents.service.id.IdSequenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final AppUserRepository appUserRepository;
    private final IdSequenceService idSequenceService;
    private final CourseMapper courseMapper;

    @Override
    @Transactional
    public CourseResponseDTO create(CourseCreateRequestDTO dto) {
        //Lấy người tạo
        AppUser admin = appUserRepository.findById(dto.getCreatedByAdminId())
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found: " + dto.getCreatedByAdminId()));

        //Sinh ID theo IdSequence + IdGenerator
        int index = idSequenceService.getNextIndex("Course");
        String subjectCode = dto.getCourseType() != null ? dto.getCourseType().name() : "COURSE";
        String courseId = IdGenerator.generateCourseId(subjectCode, index);

        //Map sang entity
        Course entity = courseMapper.toEntity(dto, courseId, admin);

        // Set default status nếu null
        if (entity.getStatus() == null) {
            entity.setStatus(CourseStatus.ACTIVE);
        }

        // Lưu + trả response
        Course saved = courseRepository.save(entity);
        return courseMapper.toResponseDTO(saved);
    }


    @Override
    @Transactional
    public CourseResponseDTO getById(String courseId) {
        Course c = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
        return courseMapper.toResponseDTO(c);
    }

    @Override
    @Transactional
    public List<CourseResponseDTO> list(String keyword, CourseStatus status, CourseType type) {
        List<Course> items;

        if (keyword != null && !keyword.isBlank()) {
            items = courseRepository.findByNameContainingIgnoreCase(keyword.trim());
        } else if (status != null && type != null) {
            items = courseRepository.findByStatusAndType(status, type);
        } else if (status != null) {
            items = courseRepository.findByStatus(status);
        } else if (type != null) {
            items = courseRepository.findByType(type);
        } else {
            items = courseRepository.findAll();
        }

        return items.stream().map(courseMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional
    public CourseResponseDTO updateStatus(String courseId, CourseStatus status) {
        if (status == null) throw new BadRequestException("Status must not be null");

        Course c = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        // Idempotent: nếu trùng status hiện tại thì vẫn trả về (không lỗi)
        if (status != c.getStatus()) {
            c.setStatus(status);
            c.setLastModified(LocalDate.now());
            c = courseRepository.save(c);
        }
        return courseMapper.toResponseDTO(c);
    }

}
