package com.esprit.tripagency.vol_management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByAeroportDepartAndAeroportArrivee(String aeroportDepart, String aeroportArrivee);
    List<Flight> findByDateVol(LocalDate dateVol);
    List<Flight> findByEtatVol(FlightStatus etatVol);
}