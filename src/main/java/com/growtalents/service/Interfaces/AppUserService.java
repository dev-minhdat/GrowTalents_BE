package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.AppUser.AppUserCreateRequestDTO;
import com.growtalents.dto.request.AppUser.AppUserUpdateRequestDTO;
import com.growtalents.dto.request.AppUser.AuthenticationRequest;
import com.growtalents.dto.response.AppUser.AppUserResponseDTO;
import com.growtalents.dto.response.AppUser.AuthenticationResponse;
import com.growtalents.enums.UserRole;
import com.growtalents.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AppUserService {
    public AppUserResponseDTO getAppUserById(String id);
    public List<AppUserResponseDTO> getAllAppUsersByRole(UserRole role);
    public void addAppUser(AppUserCreateRequestDTO dto);
    public void updateAppUser(AppUserUpdateRequestDTO dto);
    public void deleteAppUser(String id);
    AuthenticationResponse login(AuthenticationRequest request);
}
