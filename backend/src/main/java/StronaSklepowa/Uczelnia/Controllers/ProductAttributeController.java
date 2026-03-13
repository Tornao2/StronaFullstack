package StronaSklepowa.Uczelnia.Controllers;

import StronaSklepowa.Uczelnia.DTOs.ProductAttributeDTO;
import StronaSklepowa.Uczelnia.Entities.ProductAttribute;
import StronaSklepowa.Uczelnia.Services.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attributes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductAttributeController {

    private final ProductAttributeService attributeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductAttributeDTO> addAttribute(@RequestBody ProductAttribute attribute) {
        ProductAttribute saved = attributeService.addAttributeToProduct(attribute);
        return ResponseEntity.ok(new ProductAttributeDTO(saved.getName(), saved.getValue()));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductAttributeDTO>> getAttributesByProductId(@PathVariable Long productId) {
        List<ProductAttributeDTO> dtos = attributeService.getAttributesByProductId(productId)
                .stream()
                .map(attr -> new ProductAttributeDTO(attr.getName(), attr.getValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}