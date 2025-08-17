package com.growtalents.configuration;

import com.growtalents.enums.UserRole;
import com.growtalents.helper.IdGenerator;
import com.growtalents.model.AppUser;
import com.growtalents.repository.AppUserRepository;
import com.growtalents.service.id.IdSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppInitConfig {

    private final AppUserRepository appUserRepository;
    private final IdSequenceService idSequenceService;

    @Bean
    ApplicationRunner init(){
        return args -> {
            if(appUserRepository.findByUserRole(UserRole.ADMIN).isEmpty()){
                AppUser appUser = new AppUser();
                int index = idSequenceService.getNextIndex("ADMIN");
                String id = IdGenerator.generateAdminId(index);
                appUser.setUserId(id);
                appUser.setUserName("admin");
                appUser.setUserEmail("admin@growtalents.com");
                appUser.setUserPassword("admin");
                appUser.setUserRole(UserRole.ADMIN);
                appUserRepository.save(appUser);
            }
        };
    }
}
