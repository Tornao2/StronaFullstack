package StronaSklepowa.Uczelnia.Services;

import StronaSklepowa.Uczelnia.Entities.ProductAttribute;
import StronaSklepowa.Uczelnia.Repositories.ProductAttributeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {
    private final ProductAttributeRepository attributeRepository;

    public ProductAttribute addAttributeToProduct(ProductAttribute attribute) {
        return attributeRepository.save(attribute);
    }
}