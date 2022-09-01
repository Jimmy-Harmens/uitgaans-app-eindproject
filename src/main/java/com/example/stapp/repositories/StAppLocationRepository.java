package com.example.stapp.repositories;

import com.example.stapp.models.StAppLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface StAppLocationRepository extends JpaRepository<StAppLocation, Long> {
    List<StAppLocation> findByCity(String city);

    StAppLocation findByLocationId(Long id);
}
