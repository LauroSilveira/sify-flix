package com.lauro.sifyflixapi.restcontroller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lauro.sifyflixapi.jsonutils.ParseJson;
import com.lauro.sifyflixapi.model.Ship;
import com.lauro.sifyflixapi.restcontroller.dto.ShipDto;
import com.lauro.sifyflixapi.service.SpaceshipService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class SpaceshipRestControllerTest {
    private static final String ROOT_MAPPING = "/ship";

    private static ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceshipService spaceshipService;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll() throws Exception {
        //Given
        final var json = ParseJson.getJsonFile("getAll_200_OK.json");
        final var ships = Arrays.stream(ParseJson.parseToJavaObject(json, ShipDto[].class)).toList();
        when(this.spaceshipService.getAll()).thenReturn(new PageImpl<>(ships));

        //When
        final var mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(ROOT_MAPPING))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //Then
        Assertions.
    }

    @Test
    void getShipById() {
    }

    @Test
    void getShipsByName() {
    }

    @Test
    void saveShip() {
    }

    @Test
    void updateShip() {
    }

    @Test
    void deleteShip() {
    }
}