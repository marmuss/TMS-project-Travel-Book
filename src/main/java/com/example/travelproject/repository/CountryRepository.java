package com.example.travelproject.repository;

import com.example.travelproject.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findTopByOrderByTouristsDesc();

    @Transactional
    @Modifying
    @Query("update Country c set c.tourists = :tourists WHERE c.id = :id")
    void addTourist(@Param("id") Long id, @Param("tourists") int tourists);
}
