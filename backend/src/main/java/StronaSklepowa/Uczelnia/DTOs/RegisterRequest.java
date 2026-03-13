package StronaSklepowa.Uczelnia.DTOs;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String fullName;
    
    private String phoneNumber;
    private String address;
    private String city;
    private String zipCode;
}