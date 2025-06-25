package com.growtalents.dto.request.AppUser;

import com.growtalents.enums.UserRole;
import com.growtalents.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserUpdateRequestDTO {

    private String id;
    private String userName;

    @Email(message = "Email không hợp lệ")
    private String userEmail;

    // Có thể thêm validate độ mạnh nếu cần
    private String userPassword;

    private UserRole userRole;

    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")
    private String userPhone;

    private UserStatus userStatus;

    private String userParentName;

    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại phụ huynh không hợp lệ")
    private String userParentPhone;

    private String description;
}
