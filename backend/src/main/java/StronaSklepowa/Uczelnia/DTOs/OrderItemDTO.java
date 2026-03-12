package StronaSklepowa.Uczelnia.DTOs;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer priceAtPurchaseInGrosze;
}
