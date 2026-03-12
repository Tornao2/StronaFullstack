package StronaSklepowa.Uczelnia.Services;

import StronaSklepowa.Uczelnia.Entities.User;
import StronaSklepowa.Uczelnia.Entities.Role;
import StronaSklepowa.Uczelnia.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail zajęty!");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        if (user.getRole() == null) {
            user.setRole(Role.ROLE_USER);
        }
        return userRepository.save(user);
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
                return userRepository.save(newUser);
            });
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));
    }

}