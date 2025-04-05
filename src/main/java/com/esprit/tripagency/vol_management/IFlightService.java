package com.esprit.tripagency.vol_management;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface IFlightService  {


    List<Flight> retrieveAllFlights();

    Flight addFlight(Flight flight);

    Flight updateFlight(Flight flight);

    Flight retrieveFlight(Long id);

    void removeFlight(Long id);
    public List<Flight> searchFlights(String depart, String arrivee) ;
    public List<Flight> getFlightsByDate(LocalDate date);

    Duration calculateFlightDuration(Long flightId);

    List<Flight> searchFlightsAdvanced(
            String departureAirport,
            String arrivalAirport,
            LocalDate startDate,
            LocalDate endDate,
            String airline,
            FlightStatus status);


}
