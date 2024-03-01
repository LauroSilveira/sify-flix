package com.lauro.sifyflixapi.model.ship;

import com.lauro.sifyflixapi.dto.ship.ShipDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "sify_schema", name = "ship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String model;
    private Double size;

    public static Ship toEntity(ShipDto shipDto) {
        return new Ship(null, shipDto.name(), shipDto.model(), shipDto.size());
    }

}
