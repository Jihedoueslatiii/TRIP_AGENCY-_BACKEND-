package com.esprit.tripagency.vol_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService implements IFlightService {

    private final FlightRepository flightRepository;

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
        return flightRepository.save(flight);
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
}