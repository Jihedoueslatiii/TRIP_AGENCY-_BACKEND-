package com.example.offresvoyage.Service;

import com.example.offresvoyage.entities.OffreVoyage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IServiceOffreVoyage {
    OffreVoyage addOffreVoyage(OffreVoyage offreVoyage);
    List<OffreVoyage> getAllOffreVoyages();
    OffreVoyage getOffreVoyage(Long idOffreVoyage);
    OffreVoyage updateOffreVoyage(OffreVoyage offreVoyage);
    void deleteOffreVoyage(Long idOffreVoyage);
}
