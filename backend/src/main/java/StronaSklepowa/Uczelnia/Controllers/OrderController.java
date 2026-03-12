package StronaSklepowa.Uczelnia.Controllers;

import StronaSklepowa.Uczelnia.Entities.OrderItem;
import StronaSklepowa.Uczelnia.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders") 
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody List<OrderItem> items) {
        try {
            Long testUserId = 1L; 
            String paymentUrl = orderService.placeOrderAndGetPaymentUrl(testUserId, items);
            return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}