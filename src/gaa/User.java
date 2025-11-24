package gaa;

import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.time.LocalDateTime;
>>>>>>> b76d39f (5th Commit test cases updated)
import java.util.ArrayList;
import java.util.List;

public class User {
<<<<<<< HEAD
=======

>>>>>>> b76d39f (5th Commit test cases updated)
    private String name;
    private String email;
    private String passwordHash;
    private String membership = "Silver";
    private LocalDate expiryDate;
<<<<<<< HEAD
    private int failedLoginAttempts = 0;
    private boolean locked = false;
    // Backwards-compatible: old project used strings for payments; keep both
=======

    private int failedLoginAttempts = 0;
    private boolean locked = false;

    private LocalDateTime lockoutUntil = null;

    // 2FA fields
    private String twoFactorCode = null;
    private LocalDateTime twoFactorExpiry = null;

>>>>>>> b76d39f (5th Commit test cases updated)
    private List<String> paymentStrings = new ArrayList<>();

    public User() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
<<<<<<< HEAD
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getMembership() { return membership; }
    public void setMembership(String membership) { this.membership = membership; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(int failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }
    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }
    public List<String> getPaymentStrings() { return paymentStrings; }

    // convenience helper to map membership to enum-like class (MembershipPlan is not an enum now)
=======

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getMembership() { return membership; }
    public void setMembership(String membership) { this.membership = membership; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(int failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }

    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }

    public LocalDateTime getLockoutUntil() { return lockoutUntil; }
    public void setLockoutUntil(LocalDateTime until) { this.lockoutUntil = until; }

    public String getTwoFactorCode() { return twoFactorCode; }
    public void setTwoFactorCode(String code) { this.twoFactorCode = code; }

    public LocalDateTime getTwoFactorExpiry() { return twoFactorExpiry; }
    public void setTwoFactorExpiry(LocalDateTime expiry) { this.twoFactorExpiry = expiry; }

    public List<String> getPaymentStrings() { return paymentStrings; }

>>>>>>> b76d39f (5th Commit test cases updated)
    public MembershipPlan getMembershipPlanEnum() {
        for (MembershipPlan mp : MembershipPlan.defaultPlans()) {
            if (mp.getName().equalsIgnoreCase(this.membership)) return mp;
        }
        return null;
    }
}
