package StronaSklepowa.Uczelnia.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import StronaSklepowa.Uczelnia.Entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}