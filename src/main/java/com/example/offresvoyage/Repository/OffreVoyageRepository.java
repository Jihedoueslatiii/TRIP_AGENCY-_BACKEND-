package com.example.offresvoyage.Repository;

import com.example.offresvoyage.entities.OffreVoyage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OffreVoyageRepository extends JpaRepository<OffreVoyage,Long> {
    @Query("SELECT o FROM OffreVoyage o WHERE " +
            "(COALESCE(:destination, '') = '' OR o.destination LIKE %:destination%) AND " +
            "(o.dateDepart >= :startDate OR :startDate IS NULL) AND " +
            "(o.dateFin <= :endDate OR :endDate IS NULL) AND " +
            "(o.prix >= :minPrice OR :minPrice IS NULL) AND " +
            "(o.prix <= :maxPrice OR :maxPrice IS NULL) AND " +
            "(COALESCE(:transport, '') = '' OR o.transport = :transport) AND " +
            "(COALESCE(:hebergement, '') = '' OR o.hebergement = :hebergement) AND " +
            "(o.dateLimiteReservation <= :reservationDeadline OR :reservationDeadline IS NULL)")
    Page<OffreVoyage> findAllByFilters(String destination, LocalDate startDate, LocalDate endDate,
                                       Double minPrice, Double maxPrice, String transport,
                                       String hebergement, LocalDate reservationDeadline, Pageable pageable);

}
