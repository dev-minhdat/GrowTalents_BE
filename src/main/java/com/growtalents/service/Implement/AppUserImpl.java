package com.growtalents.service.Implement;

import com.growtalents.dto.request.AppUser.AppUserCreateRequestDTO;
import com.growtalents.dto.request.AppUser.AppUserUpdateRequestDTO;
import com.growtalents.dto.response.AppUser.AppUserResponseDTO;
import com.growtalents.enums.UserRole;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.AppUserMapper;
import com.growtalents.model.AppUser;
import com.growtalents.repository.AppUserRepository;
import com.growtalents.service.Interfaces.AppUserService;
import com.growtalents.service.id.IdSequenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final IdSequenceService idSequenceService;


    @Override
    public AppUserResponseDTO getAppUserById(String id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + id));
        return AppUserMapper.toResponseDTO(user);
    }

    @Override
    public List<AppUserResponseDTO> getAllAppUsersByRole(UserRole role) {
        List<AppUser> users = appUserRepository.findAllByUserRole(role);
        return users.stream()
                .map(AppUserMapper::toResponseDTO)
                .toList();
    }


    @Override
    public void addAppUser(AppUserCreateRequestDTO dto) {
        // Kiểm tra quyền của người tạo
        validateCreatorPermission(dto.getCreatedById(), dto.getUserRole());
        
        // Kiểm tra email đã tồn tại chưa
        if (appUserRepository.existsByUserEmail(dto.getUserEmail())) {
            throw new RuntimeException("Email đã tồn tại trong hệ thống");
        }
        
        int index = idSequenceService.getNextIndex("AppUser");
        String id = IdGenerator.generateUserId(index);
        AppUser appUser = AppUserMapper.toEntity(dto, id);
        appUserRepository.save(appUser);
    }
    
    /**
     * Kiểm tra quyền tạo user dựa trên role của người tạo và role của user được tạo
     */
    private void validateCreatorPermission(String creatorId, UserRole targetRole) {
        AppUser creator = appUserRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người tạo với ID: " + creatorId));
        
        UserRole creatorRole = creator.getUserRole();
        
        // Kiểm tra quyền cơ bản: chỉ ADMIN và TEACHER mới có quyền tạo user
        if (creatorRole != UserRole.ADMIN && creatorRole != UserRole.TEACHER) {
            throw new RuntimeException("Chỉ Admin và Teacher mới có quyền tạo tài khoản mới");
        }
        
        // Kiểm tra quyền tạo theo role cụ thể
        if (targetRole != null) {
            validateRoleCreationPermission(creatorRole, targetRole);
        }
    }
    
    /**
     * Kiểm tra quyền tạo user theo role cụ thể
     */
    private void validateRoleCreationPermission(UserRole creatorRole, UserRole targetRole) {
        switch (creatorRole) {
            case ADMIN:
                // ADMIN có thể tạo tất cả các role
                break;
            case TEACHER:
                // TEACHER chỉ có thể tạo STUDENT và ASSISTANT
                if (targetRole == UserRole.ADMIN || targetRole == UserRole.TEACHER || targetRole == UserRole.ACCOUNTANT) {
                    throw new RuntimeException("Teacher chỉ có thể tạo tài khoản Student và Assistant");
                }
                break;
            default:
                throw new RuntimeException("Không có quyền tạo tài khoản với role: " + targetRole.getDisplayName());
        }
    }

    @Override
    public void updateAppUser(AppUserUpdateRequestDTO dto) {
        AppUser existingUser = appUserRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + dto.getId()));

        // Cập nhật các trường được phép thay đổi
        existingUser.setUserName(dto.getUserName());
        existingUser.setUserEmail(dto.getUserEmail());
        existingUser.setUserPassword(dto.getUserPassword());
        existingUser.setUserPhone(dto.getUserPhone());
        existingUser.setUserRole(dto.getUserRole());
        existingUser.setUserStatus(dto.getUserStatus());
        existingUser.setUserParentName(dto.getUserParentName());
        existingUser.setUserParentPhone(dto.getUserParentPhone());

        appUserRepository.save(existingUser);
    }


    @Override
    public void deleteAppUser(String id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + id));

        appUserRepository.delete(user);
    }

}
