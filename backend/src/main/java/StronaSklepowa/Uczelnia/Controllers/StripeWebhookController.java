package StronaSklepowa.Uczelnia.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import StronaSklepowa.Uczelnia.Entities.OrderStatus;
import StronaSklepowa.Uczelnia.Repositories.OrderRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
public class StripeWebhookController {

    private final OrderRepository orderRepository;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            if ("checkout.session.completed".equals(event.getType())) {
                fulfillOrder(getSession(event));
            } else if ("payment_intent.payment_failed".equals(event.getType())) {
                PaymentIntent intent = (PaymentIntent) event.getData().getObject();
                updateStatus(intent.getMetadata().get("orderId"), OrderStatus.CANCELLED); 
            }
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private void fulfillOrder(Session session) {
        if (session != null) {
            updateStatus(session.getMetadata().get("orderId"), OrderStatus.PAID);
        }
    }

    private void updateStatus(String orderIdStr, OrderStatus status) {
        if (orderIdStr != null) {
            orderRepository.findById(Long.parseLong(orderIdStr)).ifPresent(order -> {
                order.setStatus(status);
                orderRepository.saveAndFlush(order);
            });
        }
    }

    private Session getSession(Event event) {
        return (Session) event.getDataObjectDeserializer().getObject()
                .orElseGet(() -> (Session) event.getData().getObject());
    }
}