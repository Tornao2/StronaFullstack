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
    private Long categoryId;
    private List<ProductAttributeDTO> attributes;
}