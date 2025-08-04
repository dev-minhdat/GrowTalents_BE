package com.growtalents.controller;

import com.growtalents.dto.request.AppUser.AppUserCreateRequestDTO;
import com.growtalents.dto.request.AppUser.AppUserUpdateRequestDTO;
import com.growtalents.dto.response.AppUser.AppUserResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.enums.UserRole;
import com.growtalents.service.Interfaces.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAppController {

    private final AppUserService appUserService;

    @PostMapping
    public ResponseEntity<GlobalResponse<Void>> createUser(@Valid @RequestBody AppUserCreateRequestDTO dto) {
        appUserService.addAppUser(dto);
        return ResponseEntity.ok(GlobalResponse.success("User created successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<AppUserResponseDTO>> getUserById(@PathVariable String id) {
        AppUserResponseDTO dto = appUserService.getAppUserById(id);
        return ResponseEntity.ok(GlobalResponse.success(dto));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<GlobalResponse<List<AppUserResponseDTO>>> getUsersByRole(@PathVariable UserRole role) {
        List<AppUserResponseDTO> list = appUserService.getAllAppUsersByRole(role);
        return ResponseEntity.ok(GlobalResponse.success(list));
    }

    @PutMapping
    public ResponseEntity<GlobalResponse<Void>> updateUser(@Valid @RequestBody AppUserUpdateRequestDTO dto) {
        appUserService.updateAppUser(dto);
        return ResponseEntity.ok(GlobalResponse.success("User updated successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<Void>> deleteUser(@PathVariable String id) {
        appUserService.deleteAppUser(id);
        return ResponseEntity.ok(GlobalResponse.success("User deleted successfully", null));
    }
}
