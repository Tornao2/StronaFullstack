package StronaSklepowa.Uczelnia.Controllers;

import StronaSklepowa.Uczelnia.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createOrder(
            @RequestBody List<Long> productIds, 
            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Musisz być zalogowany, aby złożyć zamówienie."));
        }
        try {
            String userEmail = authentication.getName();
            String paymentUrl = orderService.placeOrderAndGetPaymentUrl(userEmail, productIds);
            return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Wystąpił problem techniczny podczas procesowania zamówienia."));
        }
    }
}