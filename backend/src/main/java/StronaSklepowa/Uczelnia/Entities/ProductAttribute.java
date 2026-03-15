package StronaSklepowa.Uczelnia.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_attributes", indexes = {
    @Index(name = "idx_attribute_name_value", columnList = "name, value")
})
@Getter @Setter @NoArgsConstructor
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nazwa atrybutu nie może być pusta")
    @Column(nullable = false)
    private String name;  

    @NotBlank(message = "Wartość atrybutu nie może być pusta")
    @Column(nullable = false)
    private String value; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductAttribute(String name, String value, Product product) {
        this.name = name;
        this.value = value;
        this.product = product;
    }
}