package com.lauro.sifyflixapi.model;

import com.lauro.sifyflixapi.restcontroller.dto.ShipDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "sify_schema", name = "SHIP")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String model;
    private Double size;

    public static Ship toEntity(ShipDto shipDto) {
        return Ship.builder()
                .name(shipDto.name())
                .model(shipDto.model())
                .size(shipDto.size())
                .build();
    }

}
