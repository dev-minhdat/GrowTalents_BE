package com.growtalents.controller;

import com.growtalents.dto.request.AppUser.AppUserCreateRequestDTO;
import com.growtalents.dto.request.AppUser.AppUserUpdateRequestDTO;
import com.growtalents.dto.response.AppUser.AppUserResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.enums.UserRole;
import com.growtalents.service.Interfaces.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserAppController {

    private final AppUserService appUserService;

    @Operation(
        summary = "Tạo tài khoản mới", 
        description = "Tạo tài khoản người dùng mới. Chỉ Admin và Teacher có quyền tạo tài khoản."
    )
    @PostMapping
    public ResponseEntity<GlobalResponse<Void>> createUser(
            @Parameter(description = "Thông tin người dùng mới", required = true)
            @Valid @RequestBody AppUserCreateRequestDTO dto) {
        appUserService.addAppUser(dto);
        return ResponseEntity.ok(GlobalResponse.success("Tạo tài khoản thành công", null));
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
