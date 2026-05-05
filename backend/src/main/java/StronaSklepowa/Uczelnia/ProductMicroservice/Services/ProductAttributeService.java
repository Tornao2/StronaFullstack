package StronaSklepowa.Uczelnia.ProductMicroservice.Services;

import StronaSklepowa.Uczelnia.ProductMicroservice.Entities.ProductAttribute;
import StronaSklepowa.Uczelnia.ProductMicroservice.Repositories.ProductAttributeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {
    
    private final ProductAttributeRepository attributeRepository;

    @Transactional
    public ProductAttribute addAttributeToProduct(ProductAttribute attribute) {
        return attributeRepository.save(attribute);
    }

    @Transactional
    public List<ProductAttribute> addAttributes(List<ProductAttribute> attributes) {
        return attributeRepository.saveAll(attributes);
    }

    public List<ProductAttribute> getAttributesByProductId(Long productId) {
        return attributeRepository.findByProductId(productId);
    }

    @Transactional
    public void deleteAttributesByProductId(Long productId) {
        List<ProductAttribute> attributes = attributeRepository.findByProductId(productId);
        attributeRepository.deleteAll(attributes);
    }
}