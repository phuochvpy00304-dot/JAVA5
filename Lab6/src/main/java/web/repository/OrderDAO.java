package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.entity.Order;

public interface OrderDAO extends JpaRepository<Order,Integer> {
}
