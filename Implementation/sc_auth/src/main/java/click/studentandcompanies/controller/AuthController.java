package click.studentandcompanies.controller;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import click.studentandcompanies.service.AuthService;

@RestController
@RequestMapping("auth-api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // @PostMapping("/validatev2")
    // public ResponseEntity<?> validateTokenv2(@RequestHeader("Authorization")
    // String authHeader) {
    // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    // return ResponseEntity.status(400).body("Missing or invalid Authorization
    // header");
    // }

    // String idToken = authHeader.substring(7);

    // try {
    // FirebaseToken decodedToken = authService.verifyToken(idToken);

    // // Extract claims
    // Map<String, Object> response = new HashMap<>();
    // response.put("uid", decodedToken.getUid());
    // response.put("email", decodedToken.getEmail());
    // response.put("claims", decodedToken.getClaims());

    // return ResponseEntity.ok(response);
    // } catch (FirebaseAuthException e) {
    // return ResponseEntity.status(401).body("Invalid or expired token");
    // }

    // }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Missing or invalid Authorization header");
        }

        String idToken = authHeader.substring(7);

        try {
            System.out.println(idToken);
            return ResponseEntity.ok(idToken);

        } catch (Error e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@)

}
