        package com.growtalents.service.Implement;

        import com.growtalents.dto.request.Syllabus.SyllabusCreateRequestDTO;
        import com.growtalents.dto.response.Syllabus.SyllabusResponseDTO;
        import com.growtalents.exception.ResourceNotFoundException;
        import com.growtalents.helper.IdGenerator;
        import com.growtalents.mapper.SyllabusMapper;
        import com.growtalents.model.Course;
        import com.growtalents.model.Syllabus;
        import com.growtalents.repository.CourseRepository;
        import com.growtalents.repository.SyllabusRepository;
        import com.growtalents.service.Interfaces.SyllabusService;
        import com.growtalents.service.id.IdSequenceService;
        import lombok.RequiredArgsConstructor;
        import org.springframework.stereotype.Service;

        @Service
        @RequiredArgsConstructor
        public class SyllabusServiceImpl implements SyllabusService {
            private final SyllabusRepository syllabusRepository;
            private final CourseRepository courseRepository;
            private final SyllabusMapper syllabusMapper;
            private final IdSequenceService idSequenceService;
            @Override
            public SyllabusResponseDTO getSyllabus(String courseId) {
                Syllabus syllabus = syllabusRepository.findByCourse_CourseId(courseId);
//                        .orElseThrow(() -> new ResourceNotFoundException("Syllabus not found for course: " + courseId));
                return syllabusMapper.toResponseDTO(syllabus);
            }

            @Override
            public SyllabusResponseDTO createSyllabus(SyllabusCreateRequestDTO requestDTO) {
                // Lấy Course
                Course course = courseRepository.findById(requestDTO.getCourseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
                // Sinh index
                int index = idSequenceService.getNextIndex("Syllabus");

                // Sinh ID theo định dạng
                String syllabusId = IdGenerator.generateSyllabusId(course.getType().name(), index);

                Syllabus syllabus = Syllabus.builder()
                        .syllabusId(syllabusId)
                        .course(course)
                        .title(requestDTO.getTitle())
                        .build();
                syllabusRepository.save(syllabus);
                return syllabusMapper.toResponseDTO(syllabus);
            }
        }
