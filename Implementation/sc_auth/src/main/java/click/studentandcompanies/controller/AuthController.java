package click.studentandcompanies.controller;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
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

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        System.out.println("Sto facendo cose....");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Missing or invalid Authorization header");
        }

        String idToken = authHeader.substring(7);

        try {
            // Verifica il token tramite Firebase Admin SDK
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return ResponseEntity.ok("Token is valid");
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("No user found with this token");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("No project or null token");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("No uid found with this token");
        }
    }

    @GetMapping("/get-uuid")
    public ResponseEntity<?> getUUID(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Missing or invalid Authorization header");
        }
        String idToken = authHeader.substring(7);
        try {
            // Verifica il token tramite Firebase Admin SDK
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            // Ottieni l'UUID dell'utente
            String uuid = decodedToken.getUid();
            System.out.println("User UUID: " + uuid);
            // Crea il body originale (simulato)
            Map<String, Object> responseBody = new HashMap<>();
            // Aggiungi l'UUID al body
            responseBody.put("uuid", uuid);
            return ResponseEntity.ok(responseBody);
        } catch (FirebaseAuthException | IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Invalid Token or no uuid found");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected server error");
        }
    }
}
