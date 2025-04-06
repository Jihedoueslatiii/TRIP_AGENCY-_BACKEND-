package com.example.offresvoyage.Service;

import com.example.offresvoyage.entities.OffreVoyage;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

public interface IServiceOffreVoyage {
    OffreVoyage addOffreVoyage(OffreVoyage offreVoyage);
    List<OffreVoyage> getAllOffreVoyages();
    OffreVoyage getOffreVoyage(Long idOffreVoyage);
    OffreVoyage updateOffreVoyage(OffreVoyage offreVoyage);
    void deleteOffreVoyage(Long idOffreVoyage);
    public void sendOffreVoyageEmail(String to, OffreVoyage offre) throws MessagingException, UnsupportedEncodingException;

    public byte[] generatePDF(OffreVoyage offre) throws DocumentException, IOException;

    public Page<OffreVoyage> searchOffers(String destination, LocalDate startDate, LocalDate endDate,
                                          Double minPrice, Double maxPrice, String transport,
                                          String hebergement, LocalDate reservationDeadline, Pageable pageable);

    public byte[] generatePdfForSearchResults(String destination, LocalDate startDate, LocalDate endDate,
                                              Double minPrice, Double maxPrice, String transport,
                                              String hebergement, LocalDate reservationDeadline, Pageable pageable) throws DocumentException, IOException;


}
