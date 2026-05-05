package StronaSklepowa.Uczelnia.OrderMicroservice.Services;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaOrderService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderEvent(OrderDTO orderDto) {
        kafkaTemplate.send("order-events", orderDto);
    }
}