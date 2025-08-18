package com.growtalents.service.Implement;

import com.growtalents.dto.request.AppUser.AppUserCreateRequestDTO;
import com.growtalents.dto.request.AppUser.AppUserUpdateRequestDTO;
import com.growtalents.dto.request.AppUser.AuthenticationRequest;
import com.growtalents.dto.response.AppUser.AppUserResponseDTO;
import com.growtalents.dto.response.AppUser.AuthenticationResponse;
import com.growtalents.enums.UserRole;
import com.growtalents.exception.BadRequestException;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.AppUserMapper;
import com.growtalents.model.AppUser;
import com.growtalents.repository.AppUserRepository;
import com.growtalents.service.Interfaces.AppUserService;
import com.growtalents.service.id.IdSequenceService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final IdSequenceService idSequenceService;
    protected String signedKey = "anhemgrowtalentsmaidinhmaidinhmaidinhluon";


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
    public Page<AppUserResponseDTO> getAllAppUsersByRoleWithPagination(UserRole role, String keyword, org.springframework.data.domain.Pageable pageable) {
        Page<AppUser> users = appUserRepository.findByUserRoleWithKeyword(role, keyword, pageable);
        return users.map(AppUserMapper::toResponseDTO);
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

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        AppUser user = appUserRepository.findByUserEmailOrAccountName(request.getUsername(), request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Tên đăng nhập hoặc email không đúng"));
        if(!user.getUserPassword().equals(request.getPassword())){
            throw new BadRequestException("Mật khẩu không đúng");
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    private String generateToken(AppUser user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserId())
                .claim("role", user.getUserRole().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try{
            jwsObject.sign(new MACSigner(signedKey.getBytes(StandardCharsets.UTF_8)));
            return jwsObject.serialize();
        } catch (KeyLengthException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
