package com.esprit.tripagency.vol_management;


import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/flights")
@CrossOrigin(origins = "http://localhost:4200")

public class FlightController {
@Autowired
    private IFlightService flightService;



    // PDF Endpoints


    // Excel Endpoints



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
    @GetMapping("/search")
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam String depart,
            @RequestParam String arrivee) {
        return ResponseEntity.ok(flightService.searchFlights(depart, arrivee));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<Flight>> getFlightsByDate(@RequestParam LocalDate date) {
        return ResponseEntity.ok(flightService.getFlightsByDate(date));
    }
    @GetMapping("/{id}/duration")
    public ResponseEntity<String> getFlightDuration(@PathVariable Long id) {
        Duration duration = flightService.calculateFlightDuration(id);
        return ResponseEntity.ok("Flight duration: " + duration.toHours() + " hours " + (duration.toMinutesPart()) + " minutes");
    }

    @GetMapping("/advanced-search")
    public ResponseEntity<List<Flight>> advancedSearch(
            @RequestParam(required = false) String departureAirport,
            @RequestParam(required = false) String arrivalAirport,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String airline,
            @RequestParam(required = false) FlightStatus status) {

        List<Flight> flights = flightService.searchFlightsAdvanced(
                departureAirport,
                arrivalAirport,
                startDate,
                endDate,
                airline,
                status
        );
        return ResponseEntity.ok(flights);
    }





}
