package com.lauro.sifyflixapi.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserDto(@NotNull String username, @NotNull String password, @NotEmpty List<String> roles) {
}
