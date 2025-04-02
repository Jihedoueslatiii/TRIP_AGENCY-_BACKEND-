package com.example.offresvoyage.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    long idOffreVoyage;
    String nomOffre;
    @Lob  // For potentially long descriptions
    String description;
    String destination;
    Integer duree;
    BigDecimal prix;
    //@JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateDepart;
    //@JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateRetour;
    @Enumerated(EnumType.STRING)
    Transport transport;
    @Enumerated(EnumType.STRING)
    Hebergement hebergement;

    @OneToMany(mappedBy = "offreVoyage")
    List<Image> images;

    Integer capacite;
    Integer nombreInscrits;
    @Enumerated(EnumType.STRING)
    Statut statut; // Consider enum
    //@JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateLimiteReservation;


}
