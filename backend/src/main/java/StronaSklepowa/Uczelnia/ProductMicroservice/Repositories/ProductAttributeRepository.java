package StronaSklepowa.Uczelnia.ProductMicroservice.Repositories;

import StronaSklepowa.Uczelnia.ProductMicroservice.Entities.ProductAttribute;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    List<ProductAttribute> findByProductId(Long productId);
}