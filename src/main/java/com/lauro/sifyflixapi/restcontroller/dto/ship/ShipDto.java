package com.lauro.sifyflixapi.restcontroller.dto.ship;

import jakarta.validation.constraints.NotNull;

public record ShipDto(Long id, @NotNull String name, @NotNull String model, @NotNull Double size) {
}
