package com.growtalents.dto.response.Student;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentListResponseDTO {
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userParentName;
    private String userParentPhone;
    private String userStatus;
    private String description;
}
