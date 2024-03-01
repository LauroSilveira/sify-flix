package com.lauro.sifyflixapi.restcontroller;

import com.lauro.sifyflixapi.exception.RecordNotFoundException;
import com.lauro.sifyflixapi.dto.ship.ShipDto;
import com.lauro.sifyflixapi.service.spaceship.SpaceshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Integration test of RestController
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpaceshipRestControllerITest {

    @Autowired
    private SpaceshipService spaceshipService;

    @Autowired
    private WebTestClient webTestClient;

    private static void accept(HttpHeaders httpHeaders) {
        httpHeaders.setBasicAuth("lauro", "1234");
    }

    @Test
    void getAllTest() {
        //Then
        final var content = this.webTestClient.get()
                .uri("/ship")
                .headers(SpaceshipRestControllerITest::accept)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.numberOfElements").isEqualTo(8)
                .jsonPath("$.content");

        assertThat(content).isNotNull();
    }

    @Test
    void getShipByIdTest() {
        //Then
        final var responseBody = this.webTestClient.get()
                .uri("/ship/{id}", 1)
                .headers(SpaceshipRestControllerITest::accept)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isFound()
                .expectBody(ShipDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody)
                .usingRecursiveAssertion()
                .isNotNull();
    }

    @Test
    void getShipsByNameTest() {
        //then
        final var content = this.webTestClient.get()
                .uri("/ship/name/{name}", "star")
                .headers(SpaceshipRestControllerITest::accept)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isFound()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$.content");

        assertThat(content).isNotNull();
    }

    @Test
    void saveShipTest() {
        final var response = this.webTestClient.post()
                .uri("/ship")
                .headers(SpaceshipRestControllerITest::accept)
                .body(Mono.just(new ShipDto(null, "USS Enterprise", "USS Enterprise", 45.32)), ShipDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectAll(responseSpec -> responseSpec.expectHeader().contentType(MediaType.APPLICATION_JSON))
                .expectBody(ShipDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(response)
                .usingRecursiveAssertion()
                .isNotNull();
    }

    @Test
    void updateShipTest() {
        //Given
        final var shipById = this.spaceshipService.getShipById(1L);
        //Then
        final var response = this.webTestClient.put()
                .uri("/ship")
                .headers(SpaceshipRestControllerITest::accept)
                .body(Mono.just(new ShipDto(1L, "Firefly-Updated", "Firefly-Updated", 123.0)), ShipDto.class)
                .exchange()
                .expectAll(responseSpec -> {
                    responseSpec.expectHeader().contentType(MediaType.APPLICATION_JSON);
                    responseSpec.expectStatus().isOk();
                })
                .expectBody(ShipDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(response)
                .isNotNull();
        assertThat(response)
                .usingRecursiveAssertion()
                .isNotEqualTo(shipById);
    }

    @Test
    void deleteShipTest() {
        //Then
        this.webTestClient.delete()
                .uri("/ship/{id}", 5)
                .headers(SpaceshipRestControllerITest::accept)
                .exchange()
                .expectStatus().isOk();

        //After delete we verify that does not exist
        assertThatExceptionOfType(RecordNotFoundException.class)
                .isThrownBy(() -> this.spaceshipService.getShipById(5L))
                .withMessage("Ship not found with id 5");

    }
}
