package com.esprit.tripagency.vol_management;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class FlightService implements IFlightService {

    private final FlightRepository flightRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> retrieveAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight addFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight cannot be null");
        }

        // Save the flight first
        Flight savedFlight = flightRepository.save(flight);

        // Send email with the saved flight details
        try {
            emailService.sendFlightDetailsEmail(savedFlight); // Pass the saved flight to the email service
        } catch (MessagingException e) {
            // Log the error or handle it gracefully
            System.err.println("Failed to send email: " + e.getMessage());
            // Optionally, you can notify the user or take other actions
        }

        // Return the saved flight
        return savedFlight;
    }

    @Override
    public Flight updateFlight(Flight flight) {
        if (flight == null || flight.getIdVol() == null) {
            throw new IllegalArgumentException("Flight or ID cannot be null");
        }
        if (!flightRepository.existsById(flight.getIdVol())) {
            throw new RuntimeException("Flight not found with ID: " + flight.getIdVol());
        }
        return flightRepository.save(flight);





    }

    @Override
    public Flight retrieveFlight(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
    }

    @Override
    public void removeFlight(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Flight not found with ID: " + id);
        }
        flightRepository.deleteById(id);
    }
    @Override
    public List<Flight> searchFlights(String depart, String arrivee) {
        return flightRepository.findByAeroportDepartAndAeroportArrivee(depart, arrivee);
    }
    @Override

    public List<Flight> getFlightsByDate(LocalDate date) {
        return flightRepository.findByDateVol(date);
    }
    @Override

    public Duration calculateFlightDuration(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        return Duration.between(flight.getHeureDepart(), flight.getHeureArrivee());
    }

}