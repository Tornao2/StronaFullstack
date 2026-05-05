package StronaSklepowa.Uczelnia.PaymentMicroservice.Services;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPaymentService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentEvent(OrderDTO orderDto) {
        kafkaTemplate.send("mail-events", orderDto);
    }
}