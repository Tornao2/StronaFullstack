package StronaSklepowa.Uczelnia.Services;

import StronaSklepowa.Uczelnia.DTOs.RegisterRequest;
import StronaSklepowa.Uczelnia.DTOs.UserDTO;
import StronaSklepowa.Uczelnia.Entities.User;
import StronaSklepowa.Uczelnia.Entities.Role;
import StronaSklepowa.Uczelnia.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public UserDTO registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-mail jest już zajęty!");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setZipCode(request.getZipCode());
        if (userRepository.count() == 0) {
            user.setRole(Role.ROLE_ADMIN);
        } else {
            user.setRole(Role.ROLE_USER);
        }
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public User processOAuthPostLogin(String email, String googleId, String name) {
        return userRepository.findByEmail(email)
            .map(existingUser -> {
                existingUser.setGoogleId(googleId);
                return userRepository.save(existingUser);
            })
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setFullName(name);
                newUser.setGoogleId(googleId);
                newUser.setRole(Role.ROLE_USER);
                newUser.setPassword(passwordEncoder.encode("OAUTH2_USER_" + java.util.UUID.randomUUID()));
                return userRepository.save(newUser);
            });
    }

    public UserDTO getUserDtoByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje"));
        return mapToDTO(user);
    }

    @Transactional
    public UserDTO updateUserDetails(String email, UserDTO updates) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje"));
        if (updates.getFullName() != null) user.setFullName(updates.getFullName());
        if (updates.getPhoneNumber() != null) user.setPhoneNumber(updates.getPhoneNumber());
        if (updates.getAddress() != null) user.setAddress(updates.getAddress());
        if (updates.getCity() != null) user.setCity(updates.getCity());
        if (updates.getZipCode() != null) user.setZipCode(updates.getZipCode());
        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje"));
        
        userRepository.delete(user);
    }
    
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setZipCode(user.getZipCode());
        dto.setRole(user.getRole());
        return dto;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}