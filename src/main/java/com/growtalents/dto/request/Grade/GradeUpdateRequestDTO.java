package com.growtalents.dto.request.Grade;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GradeUpdateRequestDTO {
    private String gradeId;
    private Float score;
    private String comment;
}
