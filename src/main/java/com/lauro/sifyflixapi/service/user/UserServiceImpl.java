package com.lauro.sifyflixapi.service.user;

import com.lauro.sifyflixapi.model.user.AppUser;
import com.lauro.sifyflixapi.model.user.roles.Role;
import com.lauro.sifyflixapi.model.user.roles.RoleName;
import com.lauro.sifyflixapi.repository.UserRepository;
import com.lauro.sifyflixapi.restcontroller.dto.user.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(UserDto userDto) {
        final var entity = toEntity(userDto);
        this.userRepository.save(entity);
        return userDto;
    }

    public AppUser toEntity(UserDto dto) {
        return AppUser.builder()
                .username(dto.username())
                .password(this.passwordEncoder.encode(dto.password()))
                .roles(dto.roles()
                        .stream()
                        .map(r -> new Role(null, RoleName.valueOf(r)))
                        .collect(Collectors.toSet()))
                .build();
    }
}
