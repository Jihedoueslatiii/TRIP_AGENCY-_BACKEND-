package com.esprit.tripagency.vol_management;

import java.util.List;

public interface IFlightService  {


    List<Flight> retrieveAllFlights();

    Flight addFlight(Flight flight);

    Flight updateFlight(Flight flight);

    Flight retrieveFlight(Long id);

    void removeFlight(Long id);
}
