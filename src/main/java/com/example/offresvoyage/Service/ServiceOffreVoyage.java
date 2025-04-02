package com.example.offresvoyage.Service;

import com.example.offresvoyage.Repository.OffreVoyageRepository;
import com.example.offresvoyage.entities.Image;
import com.example.offresvoyage.entities.OffreVoyage;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceOffreVoyage implements IServiceOffreVoyage {

    @Autowired
OffreVoyageRepository offreVoyageRepository;
    public OffreVoyage addOffreVoyage(OffreVoyage offreVoyage) {
        return offreVoyageRepository.save(offreVoyage);
    }



    public List<OffreVoyage> getAllOffreVoyages() {
        List<OffreVoyage> offreVoyages=offreVoyageRepository.findAll();
        return offreVoyages;
    }

    public OffreVoyage getOffreVoyage(Long idOffreVoyage) {
        return offreVoyageRepository.findById(idOffreVoyage)
                .orElseThrow(() -> new EntityNotFoundException("OffreVoyage not found with id: " + idOffreVoyage));
    }

    public OffreVoyage updateOffreVoyage(OffreVoyage offreVoyage) {
        return offreVoyageRepository.save(offreVoyage);
    }

    public void deleteOffreVoyage(Long idOffreVoyage) {
        offreVoyageRepository.deleteById(idOffreVoyage);
    }
}
