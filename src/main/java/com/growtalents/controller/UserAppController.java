package com.growtalents.controller;

import com.growtalents.dto.request.AppUser.AppUserCreateRequestDTO;
import com.growtalents.dto.request.AppUser.AppUserUpdateRequestDTO;
import com.growtalents.dto.request.AppUser.AuthenticationRequest;
import com.growtalents.dto.response.AppUser.AppUserResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.enums.UserRole;
import com.growtalents.service.Interfaces.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        description = "Tạo tài khoản người dùng mới với role được chỉ định. " +
                     "ADMIN có thể tạo tất cả role. " +
                     "TEACHER chỉ có thể tạo STUDENT và ASSISTANT."
    )
    @PostMapping
    public ResponseEntity<GlobalResponse<Void>> createUser(
            @Parameter(description = "Thông tin người dùng mới bao gồm role", required = true)
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

    @GetMapping("/role/{role}/paginated")
    @Operation(
        summary = "Lấy danh sách user theo role với phân trang",
        description = "Lấy danh sách user theo role với hỗ trợ phân trang và tìm kiếm"
    )
    public ResponseEntity<GlobalResponse<Page<AppUserResponseDTO>>> getUsersByRoleWithPagination(
            @PathVariable UserRole role,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "userName") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<AppUserResponseDTO> result = appUserService.getAllAppUsersByRoleWithPagination(role, keyword, pageable);
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách " + role.getDisplayName() + " thành công", result));
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(GlobalResponse.success("Đăng nhập thành công", appUserService.login(request)));
    }
}
