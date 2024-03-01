package com.lauro.sifyflixapi.service.user;

import com.lauro.sifyflixapi.model.user.AppUser;
import com.lauro.sifyflixapi.model.roles.Role;
import com.lauro.sifyflixapi.model.roles.RoleName;
import com.lauro.sifyflixapi.repository.UserRepository;
import com.lauro.sifyflixapi.dto.user.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        final var newUser = this.userRepository.save(entity);
        return new UserDto(newUser.getUsername(), newUser.getPassword(), newUser.getRoles().stream()
                .map(Role::getRoleName).map(Enum::name).toList());
    }

    public List<UserDto> getAllUsers() {
        final var users = this.userRepository.findAll();
        return users.stream().map(user -> new UserDto(user.getUsername(), user.getPassword(),
                user.getRoles().stream().map(Role::getRoleName)
                        .map(String::valueOf).toList())).toList();
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
