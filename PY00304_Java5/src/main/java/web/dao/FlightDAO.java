package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import web.entity.Flight;

import java.util.List;

public interface FlightDAO extends JpaRepository<Flight, Integer> {

    List<Flight> findByAirlineContainingIgnoreCase(String airline);
}
