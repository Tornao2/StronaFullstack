package StronaSklepowa.Uczelnia.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter 
@Setter 
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String fullName;

    @Column(unique = true)
    private String googleId;

    @Enumerated(EnumType.STRING)
    private Role role;
}