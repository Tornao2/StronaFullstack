package StronaSklepowa.Uczelnia.Controllers;

import StronaSklepowa.Uczelnia.DTOs.OrderDTO;
import StronaSklepowa.Uczelnia.DTOs.UserDTO;
import StronaSklepowa.Uczelnia.Services.OrderService;
import StronaSklepowa.Uczelnia.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") 
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Musisz być zalogowany, aby pobrać dane profilu."));
        }
        try {
            UserDTO userDto = userService.getUserDtoByEmail(authentication.getName());
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Nie znaleziono profilu użytkownika."));
        }
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userUpdates, Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();
        
        try {
            UserDTO updatedUser = userService.updateUserDetails(authentication.getName(), userUpdates);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Błąd podczas aktualizacji: " + e.getMessage()));
        }
    }

    @GetMapping("/me/orders")
    public ResponseEntity<List<OrderDTO>> getMyOrders(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();
        
        List<OrderDTO> orders = orderService.getOrdersByUserEmail(authentication.getName());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/me/orders/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId, Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();
        try {
            OrderDTO order = orderService.getOrderByIdForUser(orderId, authentication.getName());
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteAccount(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();
        try {
            userService.deleteUserByEmail(authentication.getName());
            return ResponseEntity.ok(Map.of("message", "Twoje konto i wszystkie dane zostały pomyślnie usunięte."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Nie udało się usunąć konta. Spróbuj ponownie później."));
        }
    }
}