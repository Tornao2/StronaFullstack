package StronaSklepowa.Uczelnia.Controllers;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import StronaSklepowa.Uczelnia.OrderMicroservice.Services.OrderService;
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
    public ResponseEntity<Map<String, Object>> createOrder(
            @RequestBody List<Long> productIds,
            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Musisz być zalogowany, aby złożyć zamówienie."));
        }
        try {
            String userEmail = authentication.getName();
            Long orderId = orderService.placeOrder(userEmail, productIds);
            return ResponseEntity.ok(Map.of("message", "Zamówienie wysłane.", "orderId", orderId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Wystąpił problem techniczny podczas procesowania zamówienia."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        OrderDTO order = orderService.getOrderByIdForUser(id, authentication.getName());
        return ResponseEntity.ok(order);
    }
}