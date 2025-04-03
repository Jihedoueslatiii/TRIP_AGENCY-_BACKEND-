package com.example.offresvoyage.Service;

import com.example.offresvoyage.Repository.OffreVoyageRepository;
import com.example.offresvoyage.entities.OffreVoyage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceOffreVoyage implements IServiceOffreVoyage {

OffreVoyageRepository offreVoyageRepository;


    @Override
    public OffreVoyage addOffreVoyage(OffreVoyage offreVoyage) {
        return offreVoyageRepository.save(offreVoyage);
    }

    @Override
    public List<OffreVoyage> getAllOffreVoyages() {
        return offreVoyageRepository.findAll();
    }

    @Override
    public OffreVoyage getOffreVoyage(Long idOffreVoyage) {
        return offreVoyageRepository.findById(idOffreVoyage).orElse(null); // Or throw an exception
    }

    @Override
    public OffreVoyage updateOffreVoyage(OffreVoyage offreVoyage) {
        if (offreVoyageRepository.existsById(offreVoyage.getIdOffreVoyage())) {
            return offreVoyageRepository.save(offreVoyage);
        }
        return null;
    }

    @Override
    public void deleteOffreVoyage(Long idOffreVoyage) {
        offreVoyageRepository.deleteById(idOffreVoyage);
    }
}
