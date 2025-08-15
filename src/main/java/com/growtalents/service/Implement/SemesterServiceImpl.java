package com.growtalents.service.Implement;

import com.growtalents.dto.request.Semester.SemesterCreateRequestDTO;
import com.growtalents.dto.response.Semester.SemesterResponseDTO;
import com.growtalents.enums.SemesterStatus;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.SemesterMapper;
import com.growtalents.model.AcademicYear;
import com.growtalents.model.Semester;
import com.growtalents.repository.AcademicYearRepository;
import com.growtalents.repository.SemesterRepository;
import com.growtalents.service.Interfaces.SemesterService;
import com.growtalents.service.id.IdSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;
    private final AcademicYearRepository academicYearRepository;
    private final IdSequenceService idSequenceService;
    private final SemesterMapper semesterMapper;
    @Override
    public void createSemester(SemesterCreateRequestDTO dto) {
        // 1. Kiểm tra năm học tồn tại
        AcademicYear year = academicYearRepository.findById(dto.getYearId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy năm học"));
        int chosenYear = year.getYear();
        if (dto.getStartDate().getYear() != chosenYear || dto.getEndDate().getYear() != chosenYear) {
            throw new IllegalArgumentException("Ngày học kỳ phải nằm trong năm học đã chọn");
        }
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("End date phải sau hoặc bằng start date");
        }
        // 2. Lấy index mới cho SEMESTER
        int index = idSequenceService.getNextIndex("Semester");
        String generatedId = IdGenerator.generateSemesterId(index);

        // 3. Map DTO -> Entity
        Semester semester = semesterMapper.toEntity(dto, year, generatedId);

        // 4. Xác định status dựa vào ngày
        LocalDate now = LocalDate.now();
        if (now.isBefore(dto.getStartDate())) {
            semester.setStatus(SemesterStatus.UPCOMING);
        } else if (now.isAfter(dto.getEndDate())) {
            semester.setStatus(SemesterStatus.FINISHED);
        } else {
            semester.setStatus(SemesterStatus.ONGOING);
        }

        // 5. Lưu
        semesterRepository.save(semester);
    }


    @Override
    public List<SemesterResponseDTO> getSemesterByYear(int year) {
        List<Semester> semesters = semesterRepository.findByAcademicYear_Year(year);
        return semesters.stream()
                .map(semesterMapper::toResponseDTO)
                .toList();
    }


    @Override
    public List<SemesterResponseDTO> getAllSemesters() {
        return semesterRepository.findAll()
                .stream()
                .map(semesterMapper::toResponseDTO)
                .toList();
    }

}
