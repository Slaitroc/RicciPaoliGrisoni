package click.studentandcompanies.service;

import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@Service
public class AuthService {
    
    public FirebaseToken verifyToken(String idToken) throws FirebaseAuthException{
        return FirebaseAuth.getInstance().verifyIdToken(idToken);
    }
}
