package StronaSklepowa.Uczelnia.DTOs;

import StronaSklepowa.Uczelnia.Entities.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String fullName;
    private Role role;
}