package StronaSklepowa.Uczelnia.Repositories;

import StronaSklepowa.Uczelnia.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryIdAndActiveTrue(Long categoryId);
    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name);
    List<Product> findByActiveTrue();
}