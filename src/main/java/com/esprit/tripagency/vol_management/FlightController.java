package com.esprit.tripagency.vol_management;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/flights")
@CrossOrigin(origins = "http://localhost:4200")

public class FlightController {
@Autowired
    private IFlightService flightService;

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.retrieveAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    // Retrieve flight by ID
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.retrieveFlight(id);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    // Add a new flight
    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight createdFlight = flightService.addFlight(flight);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    // Update an existing flight
    @PutMapping("/update")
    public ResponseEntity<?> updateFlight(@RequestBody Flight flight) {
        try {
            Flight updatedFlight = flightService.updateFlight(flight);
            return ResponseEntity.ok(updatedFlight);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the flight.");
        }
    }


    // Remove a flight
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.removeFlight(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
