package StronaSklepowa.Uczelnia.PaymentMicroservice.Services;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import StronaSklepowa.Uczelnia.DTOs.ProcessedPaymentDTO;
import StronaSklepowa.Uczelnia.OrderMicroservice.Entities.Order;
import StronaSklepowa.Uczelnia.OrderMicroservice.Repositories.OrderRepository;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    private final OrderRepository orderRepository;
    private final KafkaPaymentService kafkaPaymentService;

    @KafkaListener(topics = "order-events", groupId = "payment-group")
    public void createCheckoutSession(OrderDTO orderDTO) throws Exception {
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/payment-success?orderId=" + orderDTO.getId())
                .setCancelUrl("http://localhost:5173/payment-failed")
                .putMetadata("orderId", orderDTO.getId().toString())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("pln")
                                                .setUnitAmount(Long.valueOf(orderDTO.getTotalAmountInGrosze()))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Zamówienie nr #" + orderDTO.getId())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
        Session session = Session.create(params);
        ProcessedPaymentDTO processedPaymentDTO = new ProcessedPaymentDTO();
        processedPaymentDTO.setOrder(orderDTO);
        processedPaymentDTO.setUrl(session.getUrl());
        kafkaPaymentService.sendPaymentEvent(processedPaymentDTO);
    }
}