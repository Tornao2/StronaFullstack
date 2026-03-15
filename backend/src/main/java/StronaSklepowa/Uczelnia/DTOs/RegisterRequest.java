package StronaSklepowa.Uczelnia.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Email jest wymagany")
    @Email(message = "Błędny format email")
    private String email;
    @NotBlank(message = "Hasło jest wymagane")
    @Size(min = 6, message = "Hasło musi mieć min. 6 znaków")
    private String password;
    @NotBlank(message = "Imię i nazwisko są wymagane")
    private String fullName;
    
    private String phoneNumber;
    private String address;
    private String city;
    private String zipCode;
}