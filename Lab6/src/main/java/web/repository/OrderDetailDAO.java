package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail,Integer> {
}
