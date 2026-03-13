package StronaSklepowa.Uczelnia.DTOs;

import StronaSklepowa.Uczelnia.Entities.OrderStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private Integer totalAmountInGrosze;
    private OrderStatus status;
    private String shippingAddress;
    private String shippingCity;
    private String shippingZipCode;
    private List<OrderItemDTO> items;
}