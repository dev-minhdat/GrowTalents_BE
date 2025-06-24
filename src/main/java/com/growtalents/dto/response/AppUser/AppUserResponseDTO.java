package com.growtalents.dto.response.AppUser;

import com.growtalents.enums.UserRole;
import com.growtalents.enums.UserStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserResponseDTO {
    private String userId;
    private String userName;
    private String userEmail;
    private UserRole userRole;
    private String userPhone;
    private UserStatus userStatus;
    private String userParentName;
    private String userParentPhone;
}
