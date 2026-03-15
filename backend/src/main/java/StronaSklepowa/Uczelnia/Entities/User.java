package StronaSklepowa.Uczelnia.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Błędny format email")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true)
    private String password; 

    @NotBlank(message = "Imię i nazwisko jest wymagane")
    private String fullName;
    private String googleId;
    
    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Błędny numer telefonu")
    private String phoneNumber;
    private String address;
    private String city;
    private String zipCode;

    @Enumerated(EnumType.STRING)
    private Role role;
}