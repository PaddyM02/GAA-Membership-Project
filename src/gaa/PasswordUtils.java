package gaa;

import java.security.MessageDigest;
<<<<<<< HEAD
import java.security.NoSuchAlgorithmException;
=======
>>>>>>> b76d39f (5th Commit test cases updated)
import java.util.Base64;

public class PasswordUtils {
    // NOTE: For demo purposes only. Use bcrypt/PBKDF2/Argon2 in production!
    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String password, String hash) {
        return hash(password).equals(hash);
    }
}
