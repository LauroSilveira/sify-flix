package com.lauro.sifyflixapi.controller;

import com.lauro.sifyflixapi.dto.ship.ShipDto;
import com.lauro.sifyflixapi.exception.RecordNotFoundException;
import com.lauro.sifyflixapi.jsonutils.CustomPageImpl;
import com.lauro.sifyflixapi.service.spaceship.SpaceshipService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Integration test of RestController
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpaceshipRestControllerITest {

    private static final String URL = "http://localhost:";

    private static TestRestTemplate restClientTest;

    @Autowired
    private SpaceshipService spaceshipService;

    @LocalServerPort
    private Integer port;

    @BeforeAll
    public static void setup() {
        restClientTest = new TestRestTemplate("admin", "1234");
    }

    @Test
    void getAllTest() {
        final var response = restClientTest.exchange(URL + port + "/ship",
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<ShipDto>>() {});

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(8);

    }

    @Test
    void getShipByIdTest() {
        //Then
        final var responseBody = restClientTest.getForEntity(URL + port + "/ship/{id}",
                ShipDto.class, 1);

        assertThat(responseBody)
                .usingRecursiveAssertion()
                .isNotNull();
    }

    @Test
    void getShipsByNameTest() {
        //then
        final var response = restClientTest.exchange(URL + port + "/ship/name/{name}", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<ShipDto>>() {}, "star");

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode().is3xxRedirection()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(3);
    }

    @Test
    void saveShipTest() {
        final var response = restClientTest.postForEntity(URL + port + "/ship",
                new ShipDto(null, "USS Enterprise", "USS Enterprise", 45.32), ShipDto.class);

        assertThat(response)
                .usingRecursiveAssertion()
                .isNotNull();
    }

    @Test
    void updateShipTest() {
        final var shipDto = this.spaceshipService.getShipById(1L);
        //Then
        final var response = restClientTest.exchange(URL + port + "/ship/{id}", HttpMethod.PUT,
                        new HttpEntity<>(new ShipDto(1L, "Firefly-Updated", "Firefly-Updated", 123.0)), ShipDto.class, 1)
                .getBody();

        assertThat(response)
                .isNotNull();
        assertThat(response)
                .usingRecursiveAssertion()
                .isNotEqualTo(shipDto);
    }

    @Test
    void deleteShipTest() {
        //Then
        restClientTest.delete(URL + port + "/ship/{id}", 5L);

        //After delete we verify that does not exist
        assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> this.spaceshipService.getShipById(5L))
                .withMessage("Ship not found with id 5");

    }
}
