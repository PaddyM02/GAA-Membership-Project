package gaa;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
<<<<<<< HEAD

public class AuthService {
    private final User user; // demo app is single-user focused for now
    private final Map<String, String> resetTokens = new HashMap<>(); // token -> email
    private final Map<String, LocalDateTime> tokenExpiry = new HashMap<>();

    private static final int MAX_FAILED = 4;
=======
import java.util.Random;

public class AuthService {

    private final User user;

    private final Map<String, String> resetTokens = new HashMap<>();
    private final Map<String, LocalDateTime> tokenExpiry = new HashMap<>();

    private static final int MAX_FAILED = 4;
    private static final int LOCKOUT_MINUTES = 15;

    private final Random rand = new Random();
>>>>>>> b76d39f (5th Commit test cases updated)

    public AuthService(User user) {
        this.user = user;
    }

<<<<<<< HEAD
    public boolean signUp(String email, String plainPassword, String name) {
        if (email == null || !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (plainPassword == null || plainPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        user.setEmail(email);
        user.setPasswordHash(PasswordUtils.hash(plainPassword));
        user.setName(name);
        EmailService.sendEmail(email, "Welcome to GAA", "Hello " + name + ", welcome!");
        return true;
    }

    public boolean login(String email, String plainPassword) {
        if (user.isLocked()) {
            System.out.println("Account locked for " + email);
            return false;
        }
        if (!email.equals(user.getEmail())) return false;
        boolean ok = PasswordUtils.verify(plainPassword, user.getPasswordHash());
        if (!ok) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            System.out.println("Failed attempts: " + user.getFailedLoginAttempts());
            if (user.getFailedLoginAttempts() >= MAX_FAILED) {
                user.setLocked(true);
                EmailService.sendEmail(user.getEmail(), "Account locked", "Your account has been locked after multiple failed attempts.");
                System.out.println("Account locked due to too many failed attempts.");
            }
            return false;
        }
        // successful login
        user.setFailedLoginAttempts(0);
        System.out.println("Login successful for " + email);
        // Simulate sending 2FA (could be expanded)
        EmailService.sendEmail(email, "2FA code", "Your code is: 123456 (demo)") ;
        return true;
    }

    public void requestPasswordReset(String email) {
        if (!email.equals(user.getEmail())) {
            System.out.println("No such user to reset");
            return;
        }
        String token = UUID.randomUUID().toString();
        resetTokens.put(token, email);
        tokenExpiry.put(token, LocalDateTime.now().plusMinutes(30));
        String link = "https://example.com/reset?token=" + token;
        EmailService.sendEmail(email, "Password reset", "Click here to reset your password (demo): " + link);
        System.out.println("Password reset token generated: " + token);
    }

    public boolean resetPassword(String token, String newPassword) {
        String email = resetTokens.get(token);
        if (email == null) return false;
        LocalDateTime expiry = tokenExpiry.get(token);
        if (expiry == null || expiry.isBefore(LocalDateTime.now())) {
            resetTokens.remove(token);
            tokenExpiry.remove(token);
            return false;
        }
        // apply reset
        user.setPasswordHash(PasswordUtils.hash(newPassword));
        user.setLocked(false);
        user.setFailedLoginAttempts(0);
        resetTokens.remove(token);
        tokenExpiry.remove(token);
        EmailService.sendEmail(email, "Password reset complete", "Your password has been changed.");
=======
    // --- Sign Up ---
    public boolean signUp(String email, String plainPassword, String name) {
        if (email == null || !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            throw new IllegalArgumentException("Invalid email");

        if (plainPassword == null || plainPassword.length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters");

        user.setEmail(email);
        user.setPasswordHash(PasswordUtils.hash(plainPassword));
        user.setName(name);

        EmailService.sendEmail(email, "Welcome to GAA",
                "Hello " + name + ", welcome!");

        return true;
    }

    // --- Login (now requires 2FA) ---
    public boolean login(String email, String plainPassword) {

        // lockout check
        if (user.getLockoutUntil() != null &&
            LocalDateTime.now().isBefore(user.getLockoutUntil())) {
            System.out.println("Account locked until " + user.getLockoutUntil());
            return false;
        }

        if (!email.equals(user.getEmail())) return false;

        boolean ok = PasswordUtils.verify(plainPassword, user.getPasswordHash());

        if (!ok) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if (user.getFailedLoginAttempts() >= MAX_FAILED) {
                user.setLocked(true);
                user.setLockoutUntil(LocalDateTime.now().plusMinutes(LOCKOUT_MINUTES));
                EmailService.sendEmail(user.getEmail(), 
                    "Account locked",
                    "Too many failed attempts. You are locked out for 15 minutes.");
            }

            return false;
        }

        // successful password match
        user.setFailedLoginAttempts(0);
        user.setLocked(false);
        user.setLockoutUntil(null);

        // generate 2FA
        String code = String.format("%06d", rand.nextInt(999999));
        user.setTwoFactorCode(code);
        user.setTwoFactorExpiry(LocalDateTime.now().plusMinutes(5));

        EmailService.sendEmail(email, "Your 2FA Code", "Your login code is: " + code);

        System.out.println("Password correct. 2FA required.");
        return true;
    }

    // --- Validate 2FA Code ---
    public boolean verifyTwoFactor(String code) {
        if (user.getTwoFactorCode() == null) return false;
        if (user.getTwoFactorExpiry().isBefore(LocalDateTime.now())) return false;

        if (code.equals(user.getTwoFactorCode())) {
            user.setTwoFactorCode(null);
            user.setTwoFactorExpiry(null);
            return true;
        }
        return false;
    }

    // --- Forgot Password ---
    public void requestPasswordReset(String email) {
        if (!email.equals(user.getEmail())) return;

        String token = UUID.randomUUID().toString();
        resetTokens.put(token, email);
        tokenExpiry.put(token, LocalDateTime.now().plusMinutes(30));

        String code = token.substring(0,6);

        EmailService.sendEmail(email,
            "Password Reset Code",
            "Your reset code is: " + code);

        System.out.println("Generated reset code: " + code);
    }

    public boolean resetPassword(String code, String newPassword) {
        String token = resetTokens.entrySet().stream()
                .filter(e -> e.getKey().startsWith(code))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (token == null) return false;

        if (tokenExpiry.get(token).isBefore(LocalDateTime.now())) return false;

        user.setPasswordHash(PasswordUtils.hash(newPassword));
        user.setLocked(false);
        user.setFailedLoginAttempts(0);

        resetTokens.remove(token);
        tokenExpiry.remove(token);

        EmailService.sendEmail(user.getEmail(),
                "Password Reset Complete",
                "Your password has been changed.");

>>>>>>> b76d39f (5th Commit test cases updated)
        return true;
    }
}
