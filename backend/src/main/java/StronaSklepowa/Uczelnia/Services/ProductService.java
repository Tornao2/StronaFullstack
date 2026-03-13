package StronaSklepowa.Uczelnia.Services;

import StronaSklepowa.Uczelnia.DTOs.ProductDTO;
import StronaSklepowa.Uczelnia.DTOs.ProductAttributeDTO;
import StronaSklepowa.Uczelnia.Entities.Category;
import StronaSklepowa.Uczelnia.Entities.Product;
import StronaSklepowa.Uczelnia.Entities.ProductAttribute;
import StronaSklepowa.Uczelnia.Repositories.CategoryRepository;
import StronaSklepowa.Uczelnia.Repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductDTO> getAllActiveProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Nie można usunąć produktu, który nie istnieje.");
        }
        productRepository.deleteById(id);
    }
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO saveProduct(ProductDTO dto) {
        Product product = new Product();
        if (dto.getId() != null) {
            product = productRepository.findById(dto.getId())
                    .orElse(new Product());
        }
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPriceInGrosze(dto.getPriceInGrosze());
        product.setStockQuantity(dto.getStockQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setActive(true);
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Kategoria nie istnieje"));
        product.setCategory(category);
        if (dto.getAttributes() != null) {
            product.getAttributes().clear(); 
            for (ProductAttributeDTO attrDto : dto.getAttributes()) {
                ProductAttribute attr = new ProductAttribute();
                attr.setName(attrDto.getName());
                attr.setValue(attrDto.getValue());
                product.addAttribute(attr); 
            }
        }
        Product saved = productRepository.save(product);
        return mapToDTO(saved);
    }

    public ProductDTO getProductDtoById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Produkt o ID " + id + " nie istnieje."));
    }
    
    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPriceInGrosze(product.getPriceInGrosze());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
        if (product.getAttributes() != null) {
            dto.setAttributes(product.getAttributes().stream()
                    .map(a -> new ProductAttributeDTO(a.getName(), a.getValue()))
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}