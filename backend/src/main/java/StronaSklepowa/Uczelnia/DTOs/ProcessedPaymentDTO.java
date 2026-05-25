package StronaSklepowa.Uczelnia.DTOs;

import lombok.Data;

@Data
public class ProcessedPaymentDTO {
    private OrderDTO order;
    private String url;
}
