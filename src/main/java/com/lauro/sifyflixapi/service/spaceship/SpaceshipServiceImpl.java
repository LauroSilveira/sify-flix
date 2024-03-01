package com.lauro.sifyflixapi.service.spaceship;

import com.lauro.sifyflixapi.exception.RecordNotFoundException;
import com.lauro.sifyflixapi.model.ship.Ship;
import com.lauro.sifyflixapi.repository.SpaceshipRepository;
import com.lauro.sifyflixapi.dto.ship.ShipDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SpaceshipServiceImpl implements SpaceshipService {

    private static final String SORT_VALUE = "name";
    private final SpaceshipRepository repository;

    public SpaceshipServiceImpl(SpaceshipRepository repository) {
        this.repository = repository;
    }

    @Cacheable("getAllCache")
    @Override
    public Page<ShipDto> getAll() {
        final var ships = this.repository.findAll(Sort.by(SORT_VALUE).ascending())
                .stream()
                .toList();
        log.info("[SpaceshipServiceImpl] - Found: {} records", ships.size());
        final var spaceshipDtoList = ships.stream()
                .map(ship -> new ShipDto(ship.getId(), ship.getName(), ship.getModel(), ship.getSize()))
                .toList();
        return new PageImpl<>(spaceshipDtoList);
    }

    @Override
    public ShipDto getShipById(Long id) {
        final var entityOptional = this.repository.findById(id);
        log.info("[SpaceshipServiceImpl] - Found ship by id: {}", id);
        return entityOptional.map(e -> new ShipDto(e.getId(), e.getName(), e.getModel(), e.getSize()))
                .orElseThrow(() -> new RecordNotFoundException("Ship not found with id " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<ShipDto> getShipsByName(String name) {
        log.info("[SpaceshipServiceImpl] - Request get ship by name: {}", name);
        final var entities = this.repository.findAllByNameContainingIgnoreCase(name);
        if (entities.isEmpty()) {
            throw new RecordNotFoundException("Ship not found with name " + name, HttpStatus.NOT_FOUND);
        } else {
            return entities.stream()
                    .map(e -> new ShipDto(e.getId(), e.getName(), e.getModel(), e.getSize()))
                    .toList();
        }
    }

    @Override
    @Transactional
    public ShipDto saveShip(ShipDto shipDto) {
        final var newEntity = this.repository.save(Ship.toEntity(shipDto));
        log.info("[SpaceshipServiceImpl] - New Ship Saved with id: {}", shipDto.id());
        return new ShipDto(newEntity.getId(), newEntity.getName(), newEntity.getModel(), newEntity.getSize());
    }

    @Override
    @CachePut("updateCache")
    @Transactional
    public ShipDto updateShip(ShipDto shipDto) {
        final var entityOptional = this.repository.findById(shipDto.id());
        log.info("[SpaceshipServiceImpl] - Found Ship with id: {}", shipDto.id());
        final var entity = entityOptional.map(e -> new Ship(e.getId(), e.getName(), e.getName(),
                        e.getSize()))
                .orElseThrow(() -> new RecordNotFoundException("Ship not found with Id " + shipDto.id(), HttpStatus.CREATED));
        this.repository.save(entity);
        return shipDto;
    }

    @Override
    public void deleteShiById(Long id) {
        final var entityId = this.repository.findById(id)
                .map(Ship::getId)
                .orElseThrow(() -> new RecordNotFoundException("No record with id " + id, HttpStatus.NO_CONTENT));
        this.repository.deleteById(entityId);
    }
}
