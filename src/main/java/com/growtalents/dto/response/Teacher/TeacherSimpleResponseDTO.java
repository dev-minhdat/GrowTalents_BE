package com.growtalents.dto.response.Teacher;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherSimpleResponseDTO {
    private String teacherId;
    private String fullName;
}
