package com.growtalents.controller;

import com.growtalents.dto.request.AppUser.AppUserCreateRequestDTO;
import com.growtalents.dto.request.AppUser.AppUserUpdateRequestDTO;
import com.growtalents.dto.response.AppUser.AppUserResponseDTO;
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
    public ResponseEntity<Void> createUser(@Valid @RequestBody AppUserCreateRequestDTO dto) {
        appUserService.addAppUser(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponseDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(appUserService.getAppUserById(id));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<AppUserResponseDTO>> getUsersByRole(@PathVariable UserRole role) {
        return ResponseEntity.ok(appUserService.getAllAppUsersByRole(role));
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@Valid @RequestBody AppUserUpdateRequestDTO dto) {
        appUserService.updateAppUser(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        appUserService.deleteAppUser(id);
        return ResponseEntity.ok().build();
    }
}
