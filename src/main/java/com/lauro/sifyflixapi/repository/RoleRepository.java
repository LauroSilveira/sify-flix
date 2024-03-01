package com.lauro.sifyflixapi.repository;

import com.lauro.sifyflixapi.model.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}
