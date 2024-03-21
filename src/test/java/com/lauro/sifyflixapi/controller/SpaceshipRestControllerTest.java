package com.lauro.sifyflixapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lauro.sifyflixapi.jsonutils.ParseJson;
import com.lauro.sifyflixapi.dto.ship.ShipDto;
import com.lauro.sifyflixapi.service.spaceship.SpaceshipService;
import com.lauro.sifyflixapi.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@WithMockUser(value = "admin", username = "admin", password = "admin", roles = "ADMIN")
class SpaceshipRestControllerTest extends ParseJson {
    private static final String ROOT_MAPPING = "/ship";

    private static ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceshipService spaceshipService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should return 200 OK endpoint and the of all ships")
    void getAll() throws Exception {
        //Given
        final var json = getJsonFile("getAll_200_OK.json");
        final var responseExpected = stream(parseToJavaObject(json, ShipDto[].class)).toList();
        when(this.spaceshipService.getAll()).thenReturn(new PageImpl<>(responseExpected));

        //When
        final var mvcResult = this.mockMvc.perform(get(ROOT_MAPPING)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        final var content = objectMapper.readTree(mvcResult.getResponse().getContentAsString()).get("content");
        final var shipDtoList = stream(objectMapper.readValue(content.toString(), ShipDto[].class)).toList();


        //Then
        assertThat(shipDtoList)
                .usingRecursiveAssertion()
                .isEqualTo(responseExpected);
    }

    @Test
    @DisplayName("Should return 200 OK endpoint and the ship search by Id")
    void getShipById() throws Exception {
        //Given
        final var jsonFile = getJsonFile("getById_200_OK.json");
        final var responseExpected = parseToJavaObject(jsonFile, ShipDto.class);
        when(this.spaceshipService.getShipById(anyLong())).thenReturn(responseExpected);

        //When
        final var mvcResult = this.mockMvc.perform(get(ROOT_MAPPING + "/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isFound())
                .andReturn();

        //Then
        final var shipDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ShipDto.class);
        assertThat(shipDto)
                .usingRecursiveAssertion()
                .isEqualTo(responseExpected);

    }

    @Test
    @DisplayName("Should return 200 OK and all the ship with having the word search")
    void getShipsByName() throws Exception {
        //Given
        final var jsonFile = getJsonFile("getByName_200_OK.json");
        final var responseExpected = stream(parseToJavaObject(jsonFile, ShipDto[].class)).toList();

        when(this.spaceshipService.getShipsByName(anyString())).thenReturn(responseExpected);
        //When
        final var mvcResult = this.mockMvc.perform(get(ROOT_MAPPING + "/name/{name}", "star")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn();

        //Then
        final var shipDtoList = stream(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ShipDto[].class)).toList();
        assertThat(shipDtoList)
                .hasSameSizeAs(responseExpected)
                .usingRecursiveAssertion()
                .isEqualTo(responseExpected);
    }

    @Test
    @DisplayName("Should return 200 OK endpoint save new ship and the new entity")
    void saveShip() throws Exception {
        //Given
        final var jsonFile = getJsonFile("save_new_ship_200_Ok.json");
        final var newShipDto = parseToJavaObject(jsonFile, ShipDto.class);
        when(this.spaceshipService.saveShip(any())).thenReturn(newShipDto);

        //When
        final var mvcResult = this.mockMvc.perform(post(ROOT_MAPPING)
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(newShipDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        //Then
        final var response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ShipDto.class);
        assertThat(response)
                .usingRecursiveAssertion()
                .isEqualTo(newShipDto);
    }

    @Test
    @DisplayName("Should return 200 OK and return the Ship Updated")
    void updateShip() throws Exception {
        //Given
        final var jsonFile = getJsonFile("update_ship_200_OK.json");
        final var shipToUpdate = parseToJavaObject(jsonFile, ShipDto.class);
        when(this.spaceshipService.updateShip(any())).thenReturn(shipToUpdate);

        //When
        final var mvcResult = this.mockMvc.perform(put(ROOT_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(shipToUpdate))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        //Then
        final var response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ShipDto.class);
        assertThat(response)
                .usingRecursiveAssertion()
                .isEqualTo(shipToUpdate);
    }

    @Test
    @DisplayName("Should return 200 OK endpoint Delete ship By ID")
    void deleteShip() throws Exception {
        //Given
        this.mockMvc.perform(delete(ROOT_MAPPING + "/{id}", 2)
                        .with(SecurityMockMvcRequestPostProcessors.csrf().asHeader())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}