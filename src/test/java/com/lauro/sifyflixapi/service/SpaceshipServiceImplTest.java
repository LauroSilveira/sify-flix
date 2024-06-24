package com.lauro.sifyflixapi.service;

import com.lauro.sifyflixapi.dto.ship.ShipDto;
import com.lauro.sifyflixapi.exception.RecordNotFoundException;
import com.lauro.sifyflixapi.jsonutils.ParseJson;
import com.lauro.sifyflixapi.model.ship.Ship;
import com.lauro.sifyflixapi.repository.SpaceshipRepository;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpaceshipServiceImplTest extends ParseJson {

    @InjectMocks
    private SpaceshipServiceImpl spaceshipServiceImpl;

    @Mock
    private SpaceshipRepository spaceshipRepository;


    @Test
    void getAll() {
        //Given
        final var jsonFile = getJsonFile("getAll_200_OK.json");
        final var ships = parseToList(jsonFile, Ship.class);
        when(this.spaceshipRepository.findAll(Sort.by("name").ascending())).thenReturn(ships);

        //When
        final var spacesShips = this.spaceshipServiceImpl.getAll();

        //then
        BDDAssertions.then(spacesShips).isNotNull();
        BDDAssertions.then(spacesShips.getContent()).hasSize(5);
        verify(this.spaceshipRepository, times(1)).findAll(Sort.by("name").ascending());
    }

    @Test
    void getShipById() {
        //Given
        final var jsonFile = getJsonFile("getById_200_OK.json");
        final var ship = parseToJavaObject(jsonFile, Ship.class);
        when(this.spaceshipRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(ship));
        //When
        final var shipDto = this.spaceshipServiceImpl.getShipById(ship.getId());
        //Then
        BDDAssertions.then(shipDto).isNotNull();
        verify(this.spaceshipRepository, times(1)).findById(ArgumentMatchers.anyLong());
    }

    @Test
    void getShipsByName() {
        //Given
        final var jsonFile = getJsonFile("getByName_200_OK.json");
        final var ships = parseToList(jsonFile, Ship.class);
        when(this.spaceshipRepository.findAllByNameContainingIgnoreCase(Mockito.anyString()))
                .thenReturn(ships);
        //When
        final var response = this.spaceshipServiceImpl.getShipsByName("firefly");
        //Then
        BDDAssertions.then(response).isNotNull();
        BDDAssertions.then(response).hasSameSizeAs(ships);
        BDDAssertions.then(response).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(ships);
    }

    @Test
    void saveShip() {
        //Given
        final var jsonFile = getJsonFile("save_new_ship_200_Ok.json");
        final var shipDto = parseToJavaObject(jsonFile, ShipDto.class);
        when(this.spaceshipRepository.save(any())).thenReturn(new Ship(shipDto.id(),
                shipDto.name(), shipDto.model(), shipDto.size()));

        //When
        final var newShip = this.spaceshipServiceImpl.saveShip(shipDto);

        //Then
        BDDAssertions.then(newShip).isNotNull();
        BDDAssertions.then(newShip).usingRecursiveAssertion().isEqualTo(shipDto);
        verify(this.spaceshipRepository, times(1)).save(any(Ship.class));
    }

    @Test
    void updateShip() {
        //Given
        final var jsonFile = getJsonFile("update_ship_200_OK.json");
        final var ship = parseToJavaObject(jsonFile, Ship.class);
        final var shipDto = parseToJavaObject(jsonFile, ShipDto.class);
        when(this.spaceshipRepository.findById(shipDto.id())).thenReturn(Optional.of(ship));

        //When
        final var shipUpdated = this.spaceshipServiceImpl.updateShip(shipDto);

        //Then
        BDDAssertions.then(shipUpdated).isNotNull();
        verify(this.spaceshipRepository, times(1)).save(any(Ship.class));
    }

    @Test
    void updateShipThrowsException() {
        //Given
        final var shipDto = new ShipDto(null, null, null, null);
        when(this.spaceshipRepository.findById(shipDto.id())).thenReturn(Optional.empty());
        //When
        assertThrows(RecordNotFoundException.class, () ->
                this.spaceshipServiceImpl.updateShip(shipDto));
    }

    @Test
    void deleteShiById() {
        final var jsonFile = getJsonFile("getById_200_OK.json");
        final var ship = parseToJavaObject(jsonFile, Ship.class);
        when(this.spaceshipRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(ship));
        //When
        this.spaceshipServiceImpl.deleteShiById(1L);
        //Then
        verify(this.spaceshipRepository, times(1))
                .deleteById(ArgumentMatchers.anyLong());
    }
}