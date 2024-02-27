package com.lauro.sifyflixapi.repository;

import com.lauro.sifyflixapi.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<UserDetails> findByUsername(String username);
}
