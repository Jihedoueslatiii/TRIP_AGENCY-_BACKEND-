package com.example.offresvoyage.Controller;

import com.example.offresvoyage.Service.IServiceOffreVoyage;
import com.example.offresvoyage.entities.OffreVoyage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Offres Voyages")
@RestController
@AllArgsConstructor
@RequestMapping("/offresvoyage")
public class OffreVoyageController {

IServiceOffreVoyage serviceOffreVoyage;


    @Operation(description = "récupérer toutes les offres de voyages de la base de données")

    @GetMapping("/getAllOffresVoyage")
    public List<OffreVoyage> getOffresVoyages() {
        List<OffreVoyage> listOffresVoyages = serviceOffreVoyage.getAllOffreVoyages();
        return listOffresVoyages;
    }

    @PostMapping("/add-offre-voyage")
    public OffreVoyage addOffreVoyage(@RequestBody OffreVoyage o) {
        OffreVoyage offreVoyage = serviceOffreVoyage.addOffreVoyage(o);
        return offreVoyage;
    }

    @DeleteMapping("/remove-offre-voyage/{offre-id}")
    public void removeOffreVoyage(@PathVariable("offre-id") Long idOffreVoyage) {
        serviceOffreVoyage.deleteOffreVoyage(idOffreVoyage);
    }

    @PutMapping("/modify-offre-voyage")
    public OffreVoyage modifyOffreVoyage(@RequestBody OffreVoyage o) {
        OffreVoyage offreVoyage = serviceOffreVoyage.updateOffreVoyage(o);
      return offreVoyage;
    }

    @GetMapping("/{offre-id}")
    public ResponseEntity<OffreVoyage> getOffreVoyageById(@PathVariable("offre-id") Long idOffreVoyage) {
        try {
            OffreVoyage offreVoyage = serviceOffreVoyage.getOffreVoyage(idOffreVoyage);
            return ResponseEntity.ok(offreVoyage);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
