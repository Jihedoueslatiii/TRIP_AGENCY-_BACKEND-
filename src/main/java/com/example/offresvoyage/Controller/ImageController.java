package com.example.offresvoyage.Controller;

import com.example.offresvoyage.Service.IServiceImage;
import com.example.offresvoyage.entities.Image;
import com.example.offresvoyage.entities.OffreVoyage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Imagess")
@RestController
@AllArgsConstructor
@RequestMapping("/images")
public class ImageController {

    IServiceImage serviceImage;
    @Operation(description = "récupérer toutes les offres de voyages de la base de données")

    @GetMapping("/getAllImages")
    public List<Image> getImages() {
        List<Image> listImages = serviceImage.getAllImages();
        return listImages;
    }

    @PostMapping("/add-image")
    public Image addImage(@RequestBody Image i) {
        Image image = serviceImage.addImage(i);
        return image;
    }

    @DeleteMapping("/remove-image/{image-id}")
    public void removeImage(@PathVariable("image-id") Long idImage) {
        serviceImage.deleteImage(idImage);
    }

    @PutMapping("/modify-image")
    public Image modifyImage(@RequestBody Image i) {
        Image image = serviceImage.updateImage(i);
        return image;
    }

    @GetMapping("/{image-id}")
    public ResponseEntity<Image> getImageById(@PathVariable("image-id") Long idImage) {
        try {
            Image image = serviceImage.getImage(idImage);
            return ResponseEntity.ok(image);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
