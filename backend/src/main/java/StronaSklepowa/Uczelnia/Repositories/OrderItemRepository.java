package StronaSklepowa.Uczelnia.Repositories;

import StronaSklepowa.Uczelnia.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}