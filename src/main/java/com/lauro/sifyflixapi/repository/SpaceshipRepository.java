package com.lauro.sifyflixapi.repository;

import com.lauro.sifyflixapi.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceshipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findAllByName(String name);
}
