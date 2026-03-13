package StronaSklepowa.Uczelnia.DTOs;

import lombok.Data;
import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String iconUrl;
    private Long parentId; 
    private List<CategoryDTO> children; 
}