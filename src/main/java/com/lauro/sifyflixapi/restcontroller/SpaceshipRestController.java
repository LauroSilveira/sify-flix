package com.lauro.sifyflixapi.restcontroller;


import com.lauro.sifyflixapi.restcontroller.dto.ship.ShipDto;
import com.lauro.sifyflixapi.service.spaceship.SpaceshipService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ship")
@Slf4j
@SecurityRequirement(name = "basicAuth")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class SpaceshipRestController {

    private final SpaceshipService spaceshipService;

    public SpaceshipRestController(SpaceshipService spaceshipService) {
        this.spaceshipService = spaceshipService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<ShipDto>> getAll() {
        log.info("[SpaceshipRestController] - Request to getAll");
        return ResponseEntity.ok(this.spaceshipService.getAll());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ShipDto> getShipById(@Positive @PathVariable Long id) {
        log.info("[SpaceshipRestController] - Request to get ships by Id: {}", id);
        return ResponseEntity.status(HttpStatus.FOUND).body(this.spaceshipService.getShipById(id));
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    public ResponseEntity<List<ShipDto>> getShipsByName(@NotBlank @PathVariable String name) {
        log.info("[SpaceshipRestController] - Request to get all ships by Id: {}", name);
        return ResponseEntity.status(HttpStatus.FOUND).body(this.spaceshipService.getShipsByName(name));
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<ShipDto> updateShip(@Valid @RequestBody ShipDto shipDto) {
        log.info("[SpaceshipRestController] - Request to update ship with Id: {}", shipDto.id());
        return ResponseEntity.ok(this.spaceshipService.updateShip(shipDto));
    }

    @PostMapping
    public ResponseEntity<ShipDto> saveShip(@Valid @RequestBody ShipDto shipDto) {
        log.info("[SpaceshipRestController] - Request to save a new ship with Id: {}", shipDto.id());
        return ResponseEntity.ok(this.spaceshipService.saveShip(shipDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShip(@NotNull @PathVariable Long id) {
        log.info("[SpaceshipRestController] - Request to delete ship with Id: {}", id);
        this.spaceshipService.deleteShiById(id);
        return ResponseEntity.ok().build();
    }
}
