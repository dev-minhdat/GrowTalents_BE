package com.growtalents.repository;

import com.growtalents.enums.UserRole;
import com.growtalents.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    List<AppUser> findAllByUserRole(UserRole role);
}
