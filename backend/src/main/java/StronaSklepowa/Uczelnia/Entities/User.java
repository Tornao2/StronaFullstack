package StronaSklepowa.Uczelnia.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; 

    private String fullName;
    private String googleId;
    
    private String phoneNumber;
    private String address;
    private String city;
    private String zipCode;

    @Enumerated(EnumType.STRING)
    private Role role;
}