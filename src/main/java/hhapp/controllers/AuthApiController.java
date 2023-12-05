package hhapp.controllers;

import hhapp.domain.jpa.User;
import hhapp.services.impl.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Map;

@Log
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {
    private final UserService userService;

    @PostMapping("/loginUser")
    private LinkedList<Object> loginUser(@AuthenticationPrincipal User user) {
       LinkedList<Object> payload = new LinkedList<>();
       payload.add(user);
       return payload;
    }

    @PostMapping("/user/registration")
    private ResponseEntity<?> registration(@RequestBody Map<String, String> userCredentials) {
        return userService.registerUser(userCredentials);
    }

    @PostMapping("/noUser")
    private ResponseEntity<String> noUser(@AuthenticationPrincipal User user) {
        return user == null ? new ResponseEntity<>(HttpStatus.UNAUTHORIZED) : new ResponseEntity<>("ok",HttpStatus.OK);
    }

    @PostMapping("/checkCorrectLogin")
    private boolean checkCorrectLogin(@AuthenticationPrincipal User user) {
        return user == null;
    }
}
