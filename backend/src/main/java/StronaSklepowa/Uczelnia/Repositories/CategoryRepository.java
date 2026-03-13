package StronaSklepowa.Uczelnia.Repositories;

import StronaSklepowa.Uczelnia.Entities.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIsNull(); 
    boolean existsByName(String name);   
}