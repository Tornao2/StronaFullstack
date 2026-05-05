package StronaSklepowa.Uczelnia.DTOs;

import lombok.Data;
import StronaSklepowa.Uczelnia.UserMicroservice.Entities.Role;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String city;
    private String zipCode;
    private Role role;
}