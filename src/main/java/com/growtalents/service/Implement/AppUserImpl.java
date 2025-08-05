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
import org.springframework.beans.factory.annotation.Autowired;
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
        int index = idSequenceService.getNextIndex("AppUser");
        String id = IdGenerator.generateUserId(index);
        AppUser appUser = AppUserMapper.toEntity(dto, id);
        appUserRepository.save(appUser);
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
