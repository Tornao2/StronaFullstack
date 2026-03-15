package StronaSklepowa.Uczelnia.Controllers;

import StronaSklepowa.Uczelnia.DTOs.RegisterRequest; 
import StronaSklepowa.Uczelnia.DTOs.UserDTO;      
import StronaSklepowa.Uczelnia.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") 
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            UserDTO registeredUser = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Wystąpił nieoczekiwany błąd"));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> checkStatus(java.security.Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(Map.of("username", principal.getName(), "authenticated", true));
    }
}