package web.service;

import web.entity.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<Flight> findAll();
    List<Flight> searchByAirline(String q);
    Optional<Flight> findById(Integer id);
    Flight create(Flight f);
    Flight update(Flight f);
    void delete(Integer id);
}
