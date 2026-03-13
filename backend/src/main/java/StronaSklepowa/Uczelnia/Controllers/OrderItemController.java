package StronaSklepowa.Uczelnia.Controllers;

import StronaSklepowa.Uczelnia.DTOs.OrderItemDTO;
import StronaSklepowa.Uczelnia.Repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/order-items") 
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") 
public class OrderItemController {

    private final OrderItemRepository orderItemRepository;
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItemDTO> items = orderItemRepository.findByOrderId(orderId).stream()
                .map(item -> new OrderItemDTO(
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getPriceAtPurchaseInGrosze()
                )).toList();
        
        return ResponseEntity.ok(items);
    }
}