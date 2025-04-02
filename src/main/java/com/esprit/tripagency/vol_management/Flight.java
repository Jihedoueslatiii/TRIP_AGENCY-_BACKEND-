package com.esprit.tripagency.vol_management;

import com.esprit.tripagency.vol_management.FlightStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVol;

    private String numVol;
    private String compagnie;
    private String aeroportDepart;
    private String aeroportArrivee;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate dateVol;

    @JsonFormat(pattern = "HH:mm:ss")
    @Temporal(TemporalType.TIME)
    private LocalTime heureDepart;

    @JsonFormat(pattern = "HH:mm:ss")
    @Temporal(TemporalType.TIME)
    private LocalTime heureArrivee;

    @Enumerated(EnumType.STRING)
    private FlightStatus etatVol;

    // Other fields and methods
}
