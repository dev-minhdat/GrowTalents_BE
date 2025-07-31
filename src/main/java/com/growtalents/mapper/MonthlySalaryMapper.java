package com.growtalents.mapper;

import com.growtalents.dto.request.MonthlySalary.MonthlySalaryCreateRequestDTO;
import com.growtalents.dto.response.MonthlySalary.MonthlySalaryResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.MonthlySalary;
import org.springframework.stereotype.Component;

@Component
public class MonthlySalaryMapper {
    public static MonthlySalary toEntity(MonthlySalaryCreateRequestDTO dto, String generatedId, AppUser teacher) {
        MonthlySalary salary = new MonthlySalary();
        salary.setSalaryId(generatedId);
        salary.setTeacher(teacher);
        salary.setMonth(dto.getMonth());
        salary.setTotalHours(dto.getTotalHours());
        salary.setTotalAmount(dto.getTotalAmount());
        salary.setPaymentDate(dto.getPaymentDate() != null ? java.time.LocalDate.parse(dto.getPaymentDate()) : null);
        salary.setStatus(dto.getStatus() != null ? com.growtalents.enums.SalaryStatus.valueOf(dto.getStatus()) : null);
        return salary;
    }

    public static MonthlySalaryResponseDTO toResponseDTO(MonthlySalary salary) {
        return MonthlySalaryResponseDTO.builder()
                .salaryId(salary.getSalaryId())
                .teacherId(salary.getTeacher() != null ? salary.getTeacher().getUserId() : null)
                .month(salary.getMonth())
                .totalHours(salary.getTotalHours())
                .totalAmount(salary.getTotalAmount())
                .paymentDate(salary.getPaymentDate() != null ? salary.getPaymentDate().toString() : null)
                .status(salary.getStatus() != null ? salary.getStatus().name() : null)
                .build();
    }
}
