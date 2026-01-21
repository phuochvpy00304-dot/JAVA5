package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.FlightDAO;
import web.entity.Flight;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightDAO dao;

    @Transactional(readOnly = true)
    public List<Flight> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Flight> searchByAirline(String q) {
        return (q == null || q.isBlank()) ? dao.findAll()
                : dao.findByAirlineContainingIgnoreCase(q);
    }

    @Transactional(readOnly = true)
    public Optional<Flight> findById(Integer id) {
        return dao.findById(id);
    }

    public Flight create(Flight f) {
        return dao.save(f);
    }

    public Flight update(Flight f) {
        return dao.save(f);
    }

    public void delete(Integer id) {
        dao.deleteById(id);
    }
}
