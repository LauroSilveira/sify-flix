package com.lauro.sifyflixapi.restcontroller.spaceship;


import com.lauro.sifyflixapi.dto.ship.ShipDto;
import com.lauro.sifyflixapi.service.spaceship.SpaceshipService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@SecurityRequirement(name = "basicAuth")
@RestController
@RequestMapping("/ship")
public class SpaceshipRestController {

    private final SpaceshipService spaceshipService;

    public SpaceshipRestController(SpaceshipService spaceshipService) {
        this.spaceshipService = spaceshipService;
    }

    @GetMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<ShipDto>> getAll() {
        log.info("[SpaceshipRestController] - Request to getAll");
        return ResponseEntity.ok(this.spaceshipService.getAll());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ShipDto> getShipById(@Positive @PathVariable Long id) {
        log.info("[SpaceshipRestController] - Request to get ships by Id: {}", id);
        return ResponseEntity.status(HttpStatus.FOUND).body(this.spaceshipService.getShipById(id));
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ShipDto>> getShipsByName(@NotBlank @PathVariable String name) {
        log.info("[SpaceshipRestController] - Request to get all ships by Id: {}", name);
        return ResponseEntity.status(HttpStatus.FOUND).body(this.spaceshipService.getShipsByName(name));
    }

    @PutMapping(produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ShipDto> updateShip(@Valid @RequestBody ShipDto shipDto) {
        log.info("[SpaceshipRestController] - Request to update ship with Id: {}", shipDto.id());
        return ResponseEntity.ok(this.spaceshipService.updateShip(shipDto));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ShipDto> saveShip(@Valid @RequestBody ShipDto shipDto) {
        log.info("[SpaceshipRestController] - Request to save a new ship with Id: {}", shipDto.id());
        return ResponseEntity.ok(this.spaceshipService.saveShip(shipDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteShip(@NotNull @PathVariable Long id) {
        log.info("[SpaceshipRestController] - Request to delete ship with Id: {}", id);
        this.spaceshipService.deleteShiById(id);
        return ResponseEntity.ok().build();
    }
}
