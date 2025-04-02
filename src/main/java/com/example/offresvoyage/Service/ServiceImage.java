package com.example.offresvoyage.Service;

import com.example.offresvoyage.Repository.ImageRepository;
import com.example.offresvoyage.entities.Image;
import com.example.offresvoyage.entities.OffreVoyage;
import com.netflix.discovery.converters.Auto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceImage implements IServiceImage{

    ImageRepository imageRepository;
    public Image addImage(Image image) {
        return imageRepository.save(image);
    }


    public List<Image> getAllImages() {
        List<Image> images=imageRepository.findAll();
        return images;
    }


    public Image getImage(Long idImage) {
        return imageRepository.findById(idImage)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + idImage));
    }


    public Image updateImage(Image image) {
        return imageRepository.save(image);
    }


    public void deleteImage(Long idImage) {
        imageRepository.deleteById(idImage);
    }
}
