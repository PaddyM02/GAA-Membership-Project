package gaa.test;

<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gaa.User;

class UserTest {

    @Test
    void testConstructorAndGetters() {
        User user = new User("John Doe", "john@example.com", "Gold", "2025-12-31");

        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("Gold", user.getMembership());
        assertEquals("2025-12-31", user.getExpiry());
    }

    @Test
    void testSetName() {
        User user = new User("John Doe", "john@example.com", "Gold", "2025-12-31");
        user.setName("Jane Smith");
        assertEquals("Jane Smith", user.getName());
    }

    @Test
    void testSetMembership() {
        User user = new User("John Doe", "john@example.com", "Gold", "2025-12-31");
        user.setMembership("Silver");
        assertEquals("Silver", user.getMembership());
    }
}

=======
import gaa.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPasswordHash("hashedPassword123");
        user.setMembership("Gold");
        user.setExpiryDate(LocalDate.of(2026, 1, 1));
        user.setFailedLoginAttempts(2);
        user.setLocked(false);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("hashedPassword123", user.getPasswordHash());
        assertEquals("Gold", user.getMembership());
        assertEquals(LocalDate.of(2026, 1, 1), user.getExpiryDate());
        assertEquals(2, user.getFailedLoginAttempts());
        assertFalse(user.isLocked());

        user.setLocked(true);
        assertTrue(user.isLocked(), "User should now be locked");

        user.setFailedLoginAttempts(5);
        assertEquals(5, user.getFailedLoginAttempts(), "Failed attempts should update");
    }

    @Test
    void testMembershipPlanEnumMapping() {
        user.setMembership("Gold");
        MembershipPlan plan = user.getMembershipPlanEnum();
        assertNotNull(plan, "Should return a plan object for valid name");
        assertEquals("Gold", plan.getName());

        user.setMembership("InvalidPlan");
        assertNull(user.getMembershipPlanEnum(), "Should return null for invalid membership");
    }

    @Test
    void testPaymentStringsList() {
        assertNotNull(user.getPaymentStrings(), "Payment list should not be null");
        assertTrue(user.getPaymentStrings().isEmpty(), "Payment list should start empty");

        user.getPaymentStrings().add("2025-01-01: Silver - €50");
        assertEquals(1, user.getPaymentStrings().size());
        assertTrue(user.getPaymentStrings().get(0).contains("Silver"));
    }

    @Test
    void testDefaultValues() {
        User newUser = new User();
        assertEquals("Silver", newUser.getMembership(), "Default membership should be Silver");
        assertEquals(0, newUser.getFailedLoginAttempts(), "Default failed attempts should be 0");
        assertFalse(newUser.isLocked(), "User should not be locked by default");
    }
}
>>>>>>> b76d39f (5th Commit test cases updated)
