package com.lauro.sifyflixapi.service;

import com.lauro.sifyflixapi.exception.RecordNotFoundException;
import com.lauro.sifyflixapi.model.Ship;
import com.lauro.sifyflixapi.repository.SpaceshipRepository;
import com.lauro.sifyflixapi.restcontroller.dto.ShipDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SpaceshipServiceImpl implements SpaceshipService {

    private final SpaceshipRepository repository;

    public SpaceshipServiceImpl(SpaceshipRepository repository) {
        this.repository = repository;
    }

    @Cacheable("getAllCache")
    @Override
    public Page<ShipDto> getAll() {
        log.info("[SpaceshipServiceImpl] - Request getAll ships");
        final var entity = this.repository.findAll(Sort.by("name").descending());
        final var spaceshipDtoList = entity.stream()
                .map(ship -> new ShipDto(ship.getId(), ship.getName(), ship.getModel(), ship.getSize()))
                .toList();
        return new PageImpl<>(spaceshipDtoList);
    }

    @Override
    public ShipDto getShipById(Long id) {
        final var entityOptional = this.repository.findById(id);
        log.info("[SpaceshipServiceImpl] - Found ship by id: {}", id);
        return entityOptional.map(e -> new ShipDto(e.getId(), e.getName(), e.getModel(), e.getSize()))
                .orElseThrow(() -> new RecordNotFoundException("Ship not found with id " + id));
    }

    @Override
    public List<ShipDto> getShipsByName(String name) {
        log.info("[SpaceshipServiceImpl] - Request get ship by name: {}", name);
        final var entities = this.repository.findAllByName(name);
        if (entities.isEmpty()) {
            throw new RecordNotFoundException("Ship not found with name " + name);
        } else {
            return entities.stream()
                    .map(e -> new ShipDto(e.getId(), e.getName(), e.getModel(), e.getSize()))
                    .toList();
        }
    }

    @Override
    @Transactional
    public void saveShip(ShipDto shipDto) {
        this.repository.save(Ship.toEntity(shipDto));
    }

    @Override
    @CachePut("updateCache")
    @Transactional
    public ShipDto updateShip(ShipDto shipDto) {
        final var entityOptional = this.repository.findById(shipDto.id());
        log.info("[SpaceshipServiceImpl] - Found Ship with id: {}", shipDto.id());
        final var entity = entityOptional.map(e -> Ship.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .model(e.getName())
                        .size(e.getSize())
                        .build())
                .orElseThrow(() -> new RecordNotFoundException("Ship not found with Id " + shipDto.id()));
        this.repository.save(entity);
        return shipDto;
    }

    @Override
    public void deleteShiById(Long id) {
        final var entityOptional = this.repository.findById(id);
        log.info("[SpaceshipServiceImpl] - Found ship with id {}", id);
        if (entityOptional.isPresent()) {
            this.repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Ship not found with id " + id);
        }
    }
}
