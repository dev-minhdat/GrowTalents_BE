package com.growtalents.mapper;

import com.growtalents.dto.request.AppUser.AppUserCreateRequestDTO;
import com.growtalents.dto.request.AppUser.AppUserUpdateRequestDTO;
import com.growtalents.dto.response.AppUser.AppUserResponseDTO;
import com.growtalents.enums.UserRole;
import com.growtalents.enums.UserStatus;
import com.growtalents.model.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    public static AppUser toEntity(AppUserCreateRequestDTO dto, String generatedId) {
        return AppUser.builder()
                .userId(generatedId)
                .userName(dto.getUserName())
                .userEmail(dto.getUserEmail())
                .userPassword(dto.getUserPassword())
                .userPhone(dto.getUserPhone())
                .userRole(dto.getUserRole() != null ? dto.getUserRole() : UserRole.STUDENT) // Sử dụng role từ request hoặc mặc định STUDENT
                .userStatus(UserStatus.ACTIVE) // Default status là ACTIVE
                .description(dto.getDescription())
                .build();
    }

    public static void updateEntity(AppUser user, AppUserUpdateRequestDTO dto) {
        user.setUserName(dto.getUserName());
        user.setUserEmail(dto.getUserEmail());
        user.setUserPassword(dto.getUserPassword());
        user.setUserRole(dto.getUserRole());
        user.setUserPhone(dto.getUserPhone());
        user.setUserStatus(dto.getUserStatus());
        user.setUserParentName(dto.getUserParentName());
        user.setUserParentPhone(dto.getUserParentPhone());
        user.setDescription(dto.getDescription());
    }

    public static AppUserResponseDTO toResponseDTO(AppUser user) {
        return AppUserResponseDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .userRole(user.getUserRole())
                .userPhone(user.getUserPhone())
                .userStatus(user.getUserStatus())
                .userParentName(user.getUserParentName())
                .userParentPhone(user.getUserParentPhone())
                .description(user.getDescription())
                .build();
    }
}
