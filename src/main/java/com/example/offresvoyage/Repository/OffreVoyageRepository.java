package com.example.offresvoyage.Repository;

import com.example.offresvoyage.entities.OffreVoyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreVoyageRepository extends JpaRepository<OffreVoyage,Long> {
}
