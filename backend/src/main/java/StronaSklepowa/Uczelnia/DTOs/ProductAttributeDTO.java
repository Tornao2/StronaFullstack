package StronaSklepowa.Uczelnia.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class ProductAttributeDTO {
    private String name;
    private String value;
}