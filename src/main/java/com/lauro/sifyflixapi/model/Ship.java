package com.lauro.sifyflixapi.model;

import com.lauro.sifyflixapi.restcontroller.dto.SpaceshipDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Long id;
    private String name;
    private String model;
    private Double size;

    public static Ship toEntity(SpaceshipDto spaceshipDto) {
        return Ship.builder()
                .id(spaceshipDto.id())
                .name(spaceshipDto.name())
                .model(spaceshipDto.model())
                .size(spaceshipDto.size())
                .build();
    }

}
