package com.example.offresvoyage.Controller;

import com.example.offresvoyage.Repository.OffreVoyageRepository;
import com.example.offresvoyage.Service.IServiceOffreVoyage;
import com.example.offresvoyage.entities.*;
import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Offres Voyages")
@RestController
@AllArgsConstructor
@RequestMapping("/offresvoyage")
public class OffreVoyageController {

    IServiceOffreVoyage serviceOffreVoyage;
    OffreVoyageRepository offreVoyageRepository;

    @Operation(description = "récupérer toutes les offres de voyages de la base de données")
    @GetMapping("/getAllOffresVoyage")
    public List<OffreVoyage> getOffresVoyages() {
        return serviceOffreVoyage.getAllOffreVoyages();
    }

    @PostMapping("/add-offre-voyage")
    public OffreVoyage addOffreVoyage(
            @RequestParam("nom") String nom,
            @RequestParam("destination") String destination,
            @RequestParam("dateDepart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDepart,
            @RequestParam("dateFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam("prix") Double prix,
            @RequestParam("description") String description,
            @RequestParam("transport") Transport transport,
            @RequestParam("hebergement") Hebergement hebergement,
            @RequestParam("capacite") Integer capacite,
            @RequestParam("dateLimiteReservation") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLimiteReservation,
            @RequestParam("statut") Statut statut,
            @RequestParam("images") MultipartFile[] images
    ) throws IOException {

        System.out.println("Received nom: " + nom);
        System.out.println("Received destination: " + destination);
        System.out.println("Received dateDepart: " + dateDepart);
        System.out.println("Received dateFin: " + dateFin);
        System.out.println("Received prix: " + prix);
        System.out.println("Received description: " + description);
        System.out.println("Received transport: " + transport);
        System.out.println("Received hebergement: " + hebergement);
        System.out.println("Received capacite: " + capacite);
        System.out.println("Received dateLimiteReservation: " + dateLimiteReservation);
        System.out.println("Received statut: " + statut);
        System.out.println("Received images: " + images.length);

        try{
            OffreVoyage offreVoyage = new OffreVoyage();
            offreVoyage.setNom(nom);
            offreVoyage.setDestination(destination);
            offreVoyage.setDateDepart(dateDepart);
            offreVoyage.setDateFin(dateFin);
            offreVoyage.setPrix(prix);
            offreVoyage.setDescription(description);
            offreVoyage.setTransport(transport);
            offreVoyage.setHebergement(hebergement);
            offreVoyage.setCapacite(capacite);
            offreVoyage.setDateLimiteReservation(dateLimiteReservation);
            offreVoyage.setStatut(statut);

            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile image : images) {
                imageUrls.add(saveImageAndGetUrl(image));
            }
            offreVoyage.setImageUrls(imageUrls);

            return serviceOffreVoyage.addOffreVoyage(offreVoyage);
        } catch (Exception e){
            System.out.println("Exception: " + e);
            return null;
        }
    }

    private String saveImageAndGetUrl(MultipartFile file) throws IOException {
        String uploadDir = "uploads";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, filename);
        Files.copy(file.getInputStream(), filePath);
        return "/uploads/" + filename;
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get("uploads").resolve(imageName).normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Change if using PNG or others
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/remove-offre-voyage/{offre-id}")
    public void removeOffreVoyage(@PathVariable("offre-id") Long idOffreVoyage) {
        serviceOffreVoyage.deleteOffreVoyage(idOffreVoyage);
    }

    @PutMapping("/modify-offre-voyage/{offre-id}")
    public ResponseEntity<OffreVoyage> modifyOffreVoyage(
            @PathVariable("offre-id") Long idOffreVoyage,
            @RequestBody OffreVoyage offreVoyage
    ) {
        try {
            offreVoyage.setIdOffreVoyage(idOffreVoyage);
            OffreVoyage updatedOffreVoyage = serviceOffreVoyage.updateOffreVoyage(offreVoyage);
            if (updatedOffreVoyage != null) {
                return ResponseEntity.ok(updatedOffreVoyage);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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


    @PostMapping("/share-offre/{id}")
    public ResponseEntity<String> shareOfferByEmail(
            @PathVariable ("id") Long idOffreVoyage,
            @RequestParam String email
    ) {
        OffreVoyage offre = serviceOffreVoyage.getOffreVoyage(idOffreVoyage);
        if (offre == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            serviceOffreVoyage.sendOffreVoyageEmail(email, offre);
            return ResponseEntity.ok("Offre envoyée avec succès à " + email);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de l'email.");
        }
    }


    @GetMapping("/offres/{offre-id}/export-pdf")
    public ResponseEntity<byte[]> exportOffreAsPDF(@PathVariable("offre-id") Long idOffreVoyage) throws DocumentException, IOException {
        OffreVoyage offre = serviceOffreVoyage.getOffreVoyage(idOffreVoyage);

        byte[] pdfBytes = serviceOffreVoyage.generatePDF(offre);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "offre_" + offre.getIdOffreVoyage() + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    @GetMapping("/offres-voyage/search")
    public Page<OffreVoyage> searchOffers(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String transport,
            @RequestParam(required = false) String hebergement,
            @RequestParam(required = false) LocalDate reservationDeadline,
            Pageable pageable) {

        return serviceOffreVoyage.searchOffers(destination, startDate, endDate, minPrice, maxPrice,
                transport, hebergement, reservationDeadline, pageable);
    }


    @GetMapping("/download/pdf/search")
    public ResponseEntity<byte[]> downloadSearchResultsPdf(@RequestParam(required = false) String destination,
                                                           @RequestParam(required = false) LocalDate startDate,
                                                           @RequestParam(required = false) LocalDate endDate,
                                                           @RequestParam(required = false) Double minPrice,
                                                           @RequestParam(required = false) Double maxPrice,
                                                           @RequestParam(required = false) String transport,
                                                           @RequestParam(required = false) String hebergement,
                                                           @RequestParam(required = false) LocalDate reservationDeadline,
                                                           Pageable pageable) throws DocumentException, IOException {
        // Call the service to generate the PDF
        byte[] pdfBytes = serviceOffreVoyage.generatePdfForSearchResults(destination, startDate, endDate, minPrice, maxPrice, transport, hebergement, reservationDeadline, pageable);

        // Prepare the response headers for the PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("search-offers-list.pdf").build());

        // Return the PDF as a response
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    @GetMapping("/advanced")
    public ResponseEntity<Map<String, Object>> getAdvancedStatistics() throws IOException {
        // Calculate statistics
        StatisticsDto stats = serviceOffreVoyage.getAdvancedStatistics();

        // Get yearly revenue map for the last 5 years
        Map<Integer, Double> yearlyRevenue = stats.getYearlyRevenue();

        // Save the revenue trend chart locally as an image
        serviceOffreVoyage.saveRevenueTrendChart(yearlyRevenue);

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("statistics", stats);
        response.put("message", "Yearly revenue chart for the last 5 years saved as yearly_revenue_trend_last_5_years.png");

        return ResponseEntity.ok(response);
    }
}
