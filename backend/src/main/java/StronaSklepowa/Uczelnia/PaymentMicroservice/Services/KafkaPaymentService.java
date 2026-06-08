package StronaSklepowa.Uczelnia.PaymentMicroservice.Services;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import StronaSklepowa.Uczelnia.DTOs.ProcessedPaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPaymentService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentEvent(ProcessedPaymentDTO processedPaymentDTO) {
        kafkaTemplate.send("processedpayment-event", processedPaymentDTO);
    }

    public void sendInventoryEvent(OrderDTO orderDto) {
        kafkaTemplate.send("inventory-events", orderDto);
    }

    public void sendMailEvent(OrderDTO orderDTO) {
        kafkaTemplate.send("mail-events", orderDTO);
    }
}