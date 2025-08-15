package com.growtalents.repository;

import com.growtalents.enums.UserRole;
import com.growtalents.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    List<AppUser> findAllByUserRole(UserRole role);
    boolean existsByUserEmail(String userEmail);

    Optional<AppUser> findByUserRole(UserRole role);
    Optional<AppUser> findByUserEmailOrAccountName(String email, String accountName);
}
