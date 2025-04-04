package com.esprit.tripagency.vol_management;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface IFlightService  {


    List<Flight> retrieveAllFlights();

    Flight addFlight(Flight flight);

    Flight updateFlight(Flight flight);

    Flight retrieveFlight(Long id);

    void removeFlight(Long id);
    public List<Flight> searchFlights(String depart, String arrivee) ;
    public List<Flight> getFlightsByDate(LocalDate date);

    Duration calculateFlightDuration(Long flightId);
}
