package com.esprit.tripagency.vol_management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByAeroportDepartAndAeroportArrivee(String aeroportDepart, String aeroportArrivee);
    List<Flight> findByDateVol(LocalDate dateVol);
    List<Flight> findByEtatVol(FlightStatus etatVol);


    @Query("SELECT f FROM Flight f WHERE " +
            "(:departureAirport IS NULL OR f.aeroportDepart = :departureAirport) AND " +
            "(:arrivalAirport IS NULL OR f.aeroportArrivee = :arrivalAirport) AND " +
            "(:startDate IS NULL OR f.dateVol >= :startDate) AND " +
            "(:endDate IS NULL OR f.dateVol <= :endDate) AND " +
            "(:airline IS NULL OR f.compagnie = :airline) AND " +
            "(:status IS NULL OR f.etatVol = :status)")
    List<Flight> searchFlightsAdvanced(
            @Param("departureAirport") String departureAirport,
            @Param("arrivalAirport") String arrivalAirport,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("airline") String airline,
            @Param("status") FlightStatus status);
}