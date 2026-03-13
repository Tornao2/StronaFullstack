package StronaSklepowa.Uczelnia.Repositories;

import StronaSklepowa.Uczelnia.Entities.Order;
import StronaSklepowa.Uczelnia.Entities.OrderStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
    List<Order> findByStatus(OrderStatus status);
}