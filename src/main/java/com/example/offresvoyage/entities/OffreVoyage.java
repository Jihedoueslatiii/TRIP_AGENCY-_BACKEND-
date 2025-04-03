package com.example.offresvoyage.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class OffreVoyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffreVoyage;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDate dateDepart;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false)
    private Double prix;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Transport transport;

    @Column(nullable = false)
    private Hebergement hebergement;

    @Column(nullable = false)
    private Integer capacite;

    @Column(nullable = false)
    private LocalDate dateLimiteReservation;
    @Enumerated(EnumType.STRING)
    Statut statut;
    @ElementCollection
    @CollectionTable(name = "offre_voyage_images", joinColumns = @JoinColumn(name = "offre_voyage_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

}
