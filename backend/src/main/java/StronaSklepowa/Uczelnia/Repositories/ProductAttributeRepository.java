package StronaSklepowa.Uczelnia.Repositories;

import StronaSklepowa.Uczelnia.Entities.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
}