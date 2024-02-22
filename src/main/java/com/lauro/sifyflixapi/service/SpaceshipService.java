package com.lauro.sifyflixapi.service;

import com.lauro.sifyflixapi.restcontroller.dto.SpaceshipDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SpaceshipService {

    Page<SpaceshipDto> getAll();

    SpaceshipDto getShipById(Long id);

    List<SpaceshipDto> getShipsByName(String name);

    void saveShip(SpaceshipDto spaceshipDto);

    SpaceshipDto updateShip(SpaceshipDto spaceshipDto);

    void deleteShiById(Long id);
}
