package com.lauro.sifyflixapi.service.spaceship;

import com.lauro.sifyflixapi.dto.ship.ShipDto;
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
