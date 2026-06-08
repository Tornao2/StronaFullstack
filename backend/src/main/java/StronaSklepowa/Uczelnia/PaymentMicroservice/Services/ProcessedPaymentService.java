package StronaSklepowa.Uczelnia.PaymentMicroservice.Services;

import StronaSklepowa.Uczelnia.DTOs.ProcessedPaymentDTO;
import StronaSklepowa.Uczelnia.OrderMicroservice.Entities.Order;
import StronaSklepowa.Uczelnia.OrderMicroservice.Repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessedPaymentService {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "processedpayment-event", groupId = "payment-group")
    public void createCheckoutSession(ProcessedPaymentDTO processedPaymentDTO) {
        updateOrderWithPaymentUrl(processedPaymentDTO.getOrder().getId(), processedPaymentDTO.getUrl());
    }

    private void updateOrderWithPaymentUrl(Long orderId, String url) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono zamowienia: " + orderId));
        order.setPaymentUrl(url);
        //order.setStatus();
        orderRepository.save(order);
    }
}
