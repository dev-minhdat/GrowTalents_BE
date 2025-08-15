package com.growtalents.dto.request.AppUser;

import com.growtalents.enums.UserRole;
import com.growtalents.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserCreateRequestDTO {

    @NotBlank(message = "Tên không được để trống")
    private String userName;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String userEmail;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String userPassword;

    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")
    private String userPhone;

    private String description;

    // ID của người tạo (để kiểm tra quyền)
    @NotBlank(message = "ID người tạo không được để trống")
    private String createdById;

}
