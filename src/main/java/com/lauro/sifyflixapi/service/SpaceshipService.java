package com.lauro.sifyflixapi.service;

import com.lauro.sifyflixapi.restcontroller.dto.ShipDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SpaceshipService {

    Page<ShipDto> getAll();

    ShipDto getShipById(Long id);

    List<ShipDto> getShipsByName(String name);

    ShipDto saveShip(ShipDto shipDto);

    ShipDto updateShip(ShipDto shipDto);

    void deleteShiById(Long id);
}
