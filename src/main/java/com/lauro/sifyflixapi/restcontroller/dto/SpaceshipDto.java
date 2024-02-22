package com.lauro.sifyflixapi.restcontroller.dto;

import jakarta.validation.constraints.NotNull;

public record SpaceshipDto(Long id, @NotNull String name, @NotNull String model, @NotNull Double size) {
}
