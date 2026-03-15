package StronaSklepowa.Uczelnia.Services;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderUpdate(OrderDTO orderDto) {
        kafkaTemplate.send("order-events", orderDto);
        System.out.println("Kafka: Wysłano zdarzenie dla zamówienia #" + orderDto.getId());
    }
}