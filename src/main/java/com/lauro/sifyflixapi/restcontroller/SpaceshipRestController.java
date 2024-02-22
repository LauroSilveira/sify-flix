package com.lauro.sifyflixapi.restcontroller;


import com.lauro.sifyflixapi.restcontroller.dto.SpaceshipDto;
import com.lauro.sifyflixapi.service.SpaceshipService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ship")
@Slf4j
public class SpaceshipRestController {

    private final SpaceshipService spaceshipService;

    public SpaceshipRestController(SpaceshipService spaceshipService) {
        this.spaceshipService = spaceshipService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<SpaceshipDto>> getAll() {
        log.info("[SpaceshipRestController] - Request to getAll spaceships");
        return ResponseEntity.ok(this.spaceshipService.getAll());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<SpaceshipDto> getShipById(@Positive @PathVariable Long id) {
        log.info("[SpaceshipRestController] - Request to get ships by Id: {}", id);
        return ResponseEntity.ok(this.spaceshipService.getShipById(id));
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    public ResponseEntity<List<SpaceshipDto>> getShipsByName(@NotBlank @PathVariable String name) {
        log.info("[SpaceshipRestController] - Request to get all ships by Id: {}", name);
        return ResponseEntity.ok(this.spaceshipService.getShipsByName(name));
    }

    @PostMapping
    public ResponseEntity<SpaceshipDto> saveShip(@Valid @RequestBody SpaceshipDto spaceshipDto) {
        log.info("[SpaceshipRestController] - Request to save a new ship with Id: {}", spaceshipDto.id());
        this.spaceshipService.saveShip(spaceshipDto);
        return ResponseEntity.ok(spaceshipDto);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<SpaceshipDto> updateShip(@Valid @RequestBody SpaceshipDto spaceshipDto) {
        log.info("[SpaceshipRestController] - Request to update ship with Id: {}", spaceshipDto.id());
        return ResponseEntity.ok(this.spaceshipService.updateShip(spaceshipDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShip(@NotNull @PathVariable Long id) {
        log.info("[SpaceshipRestController] - Request to delete ship with Id: {}", id);
        this.spaceshipService.deleteShiById(id);
        return ResponseEntity.accepted().build();
    }
}
