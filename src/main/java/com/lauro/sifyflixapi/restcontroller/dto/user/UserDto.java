package com.lauro.sifyflixapi.restcontroller.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserDto(@NotNull String username, @NotNull String password, @NotEmpty List<String> roles) {
}
