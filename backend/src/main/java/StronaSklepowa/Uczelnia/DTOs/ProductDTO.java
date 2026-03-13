package StronaSklepowa.Uczelnia.DTOs;

import lombok.Data;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Integer priceInGrosze;
    private Integer stockQuantity;
    private String imageUrl;
    private Long categoryId; 
    private String categoryName; 
    private List<ProductAttributeDTO> attributes;
}