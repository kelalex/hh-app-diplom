package hhapp.services.impl;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import hhapp.domain.jpa.User;
import hhapp.domain.categories.Role;
import hhapp.repos.UserRepo;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Log
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username: " + username);
        return userRepo.findByUsername(username);
    }

    public ResponseEntity<?> registerUser(Map<String, String> userDetails) {
        if (userRepo.findByUsername(userDetails.get("username")) != null) return new ResponseEntity<>(null, HttpStatus.CONFLICT);

        User user = new User();
        user.setPassword(passwordEncoder.encode(userDetails.get("password")));
        user.setUsername(userDetails.get("username").trim());
        user.setFirstName(userDetails.get("firstName").trim());
        user.setLastName(userDetails.get("lastName").trim());
        user.setPatronymic(userDetails.get("patronymic").trim());
        user.setEmail(userDetails.get("email").trim());
        user.setActive(true);
        user.setRegistrationDate(LocalDateTime.now());
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
