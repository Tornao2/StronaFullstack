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
                Session session = getSession(event);
                fulfillOrder(session);
            } 
            else if ("payment_intent.payment_failed".equals(event.getType())) {
                PaymentIntent intent = (PaymentIntent) event.getData().getObject();
                updateStatus(intent.getMetadata().get("orderId"), OrderStatus.CANCELLED); 
            }
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            System.err.println("Błąd Webhooka: " + e.getMessage());
            return ResponseEntity.badRequest().body("Webhook Error");
        }
    }

    private void fulfillOrder(Session session) {
        String orderId = session.getMetadata().get("orderId");
        if (orderId != null) {
            updateStatus(orderId, OrderStatus.PAID);
        }
    }

    private void updateStatus(String orderIdStr, OrderStatus status) {
        try {
            Long orderId = Long.parseLong(orderIdStr);
            orderRepository.findById(orderId).ifPresent(order -> {
                order.setStatus(status);
                orderRepository.save(order);
            });
        } catch (Exception e) {
            System.err.println("Błąd aktualizacji statusu dla ID: " + orderIdStr);
        }
    }

    private Session getSession(Event event) {
        return (Session) event.getDataObjectDeserializer().getObject()
                .orElseGet(() -> (Session) event.getData().getObject());
    }
}