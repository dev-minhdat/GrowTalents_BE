package com.growtalents.service.Implement;

import com.growtalents.dto.request.Teacher.TeacherCreateRequestDTO;
import com.growtalents.dto.response.Teacher.TeacherResponseDTO;
import com.growtalents.exception.BadRequestException;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.TeacherMapper;
import com.growtalents.model.Teacher;
import com.growtalents.repository.TeacherRepository;
import com.growtalents.service.Interfaces.TeacherService;
import com.growtalents.service.id.IdSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {
    
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final IdSequenceService idSequenceService;
    
    @Override
    public TeacherResponseDTO createTeacher(TeacherCreateRequestDTO dto) {
        // Validate email uniqueness
        if (teacherRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email đã tồn tại");
        }
        
        // Validate AppUser linking (if provided)
        if (dto.getAppUserId() != null && !dto.getAppUserId().isEmpty()) {
            if (teacherRepository.existsByAppUser_UserId(dto.getAppUserId())) {
                throw new BadRequestException("AppUser này đã được liên kết với Teacher khác");
            }
        }
        
        // Generate ID
        int nextIndex = idSequenceService.getNextIndex("Teacher");
        String teacherId = IdGenerator.generateTeacherId(nextIndex);
        
        // Convert to entity and save
        Teacher teacher = teacherMapper.toEntity(dto, teacherId);
        Teacher savedTeacher = teacherRepository.save(teacher);
        
        return teacherMapper.toResponseDTO(savedTeacher);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<TeacherResponseDTO> getAllTeachers(String keyword, Pageable pageable) {
        Page<Teacher> teachers = teacherRepository.findByKeyword(keyword, pageable);
        return teachers.map(teacherMapper::toResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public TeacherResponseDTO getTeacherById(String teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên với ID: " + teacherId));
        
        return teacherMapper.toResponseDTO(teacher);
    }
}
