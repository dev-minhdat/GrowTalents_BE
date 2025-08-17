package com.growtalents.service.Implement;

import com.growtalents.dto.request.Student.StudentCreateRequestDTO;
import com.growtalents.dto.response.Student.StudentResponseDTO;
import com.growtalents.exception.BadRequestException;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.StudentDetailMapper;
import com.growtalents.model.Student;
import com.growtalents.repository.StudentRepository;
import com.growtalents.service.Interfaces.StudentDetailService;
import com.growtalents.service.id.IdSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentDetailServiceImpl implements StudentDetailService {
    
    private final StudentRepository studentRepository;
    private final StudentDetailMapper studentDetailMapper;
    private final IdSequenceService idSequenceService;
    
    @Override
    public StudentResponseDTO createStudent(StudentCreateRequestDTO dto) {
        // Validate email uniqueness
        if (studentRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email đã tồn tại");
        }
        
        // Validate AppUser linking (if provided)
        if (dto.getAppUserId() != null && !dto.getAppUserId().isEmpty()) {
            if (studentRepository.existsByAppUser_UserId(dto.getAppUserId())) {
                throw new BadRequestException("AppUser này đã được liên kết với Student khác");
            }
        }
        
        // Generate ID
        int nextIndex = idSequenceService.getNextIndex("Student");
        String studentId = IdGenerator.generateStudentId(nextIndex);
        
        // Convert to entity and save
        Student student = studentDetailMapper.toEntity(dto, studentId);
        Student savedStudent = studentRepository.save(student);
        
        return studentDetailMapper.toResponseDTO(savedStudent);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getAllStudents(String keyword, Pageable pageable) {
        Page<Student> students = studentRepository.findByKeyword(keyword, pageable);
        return students.map(studentDetailMapper::toResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy học sinh với ID: " + studentId));
        
        return studentDetailMapper.toResponseDTO(student);
    }
}
