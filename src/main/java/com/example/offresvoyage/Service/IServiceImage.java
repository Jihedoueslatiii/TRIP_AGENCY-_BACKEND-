package com.example.offresvoyage.Service;

import com.example.offresvoyage.entities.Image;
import com.example.offresvoyage.entities.OffreVoyage;

import java.util.List;

public interface IServiceImage {
    Image addImage(Image image);
    List<Image> getAllImages();
    Image getImage(Long idImage);
    Image updateImage(Image image);
    void deleteImage(Long idImage);
}
