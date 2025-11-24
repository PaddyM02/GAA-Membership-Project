package gaa.test;

<<<<<<< HEAD
import org.junit.jupiter.api.Test;

import gaa.MembershipPlan;
import gaa.MembershipService;
import gaa.User;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class MembershipServiceTest {

    @Test
    void testConstructorInitialization() {
        MembershipService service = new MembershipService();

        // Verify user defaults
        User user = service.getUser();
        assertEquals("Jack O'Dea", user.getName());
        assertEquals("jack@example.com", user.getEmail());
        assertEquals("Silver", user.getMembership());
        assertEquals("2026-03-15", user.getExpiry());

        // Verify plans
        List<MembershipPlan> plans = service.getPlans();
        assertEquals(3, plans.size());
        assertEquals("Silver", plans.get(0).getName());
        assertEquals("Gold", plans.get(1).getName());
        assertEquals("Platinum", plans.get(2).getName());

        // Verify initial payment
        List<String> payments = service.getPayments();
        assertEquals(1, payments.size());
        assertTrue(payments.get(0).contains("Silver - €30"));
    }

    @Test
    void testUpgrade() {
        MembershipService service = new MembershipService();
        service.upgrade("Gold");

        User user = service.getUser();
        assertEquals("Gold", user.getMembership());

        List<String> payments = service.getPayments();
        assertTrue(payments.get(payments.size() - 1).contains("upgraded to Gold"));
    }

    @Test
    void testDowngrade() {
        MembershipService service = new MembershipService();
        service.downgrade("Silver");

        User user = service.getUser();
        assertEquals("Silver", user.getMembership());

        List<String> payments = service.getPayments();
        assertTrue(payments.get(payments.size() - 1).contains("downgraded to Silver"));
    }
}

=======
import gaa.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MembershipServiceTest {

    private User user;
    private MembershipService service;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("member@example.com");
        user.setMembership("Silver");
        user.setExpiryDate(LocalDate.now().plusDays(365));
        service = new MembershipService(user);
    }

    @Test
    void testPlansInitialization() {
        List<MembershipPlan> plans = service.getPlans();
        assertEquals(3, plans.size(), "Should have 3 default plans");
        assertEquals("Silver", plans.get(0).getName());
        assertEquals("Gold", plans.get(1).getName());
        assertEquals("Platinum", plans.get(2).getName());
    }

    @Test
    void testFindPlanByName() {
        MembershipPlan gold = service.findPlanByName("Gold");
        assertNotNull(gold);
        assertEquals(80, gold.getPrice());
        assertNull(service.findPlanByName("InvalidPlan"));
    }

    @Test
    void testDetermineEligibility() {
        user.setMembership("Silver");
        assertEquals("Eligible for Gold or Platinum", service.determineEligibility());

        user.setMembership("Gold");
        assertEquals("Eligible for Platinum", service.determineEligibility());

        user.setMembership("Platinum");
        assertEquals("Already Platinum or no upgrades available", service.determineEligibility());
    }

    @Test
    void testUpgradeToGold() {
        boolean success = service.upgradeTo("Gold");
        assertTrue(success, "Upgrade to Gold should succeed");
        assertEquals("Gold", user.getMembership());
        assertNotNull(user.getExpiryDate());
        assertFalse(service.getPayments().isEmpty(), "Payment list should not be empty after upgrade");
    }

    @Test
    void testUpgradeToInvalidPlan() {
        boolean success = service.upgradeTo("Diamond");
        assertFalse(success, "Upgrade to invalid plan should fail");
    }

    @Test
    void testUpgradeToSamePlanFails() {
        user.setMembership("Gold");
        boolean success = service.upgradeTo("Gold");
        assertFalse(success, "Upgrading to same plan should fail");
    }

    @Test
    void testDowngradeToSilver() {
        user.setMembership("Gold");
        boolean success = service.downgradeTo("Silver");
        assertTrue(success, "Downgrade to Silver should succeed");
        assertEquals("Silver", user.getMembership());
    }

    @Test
    void testDowngradeToNonSilverFails() {
        user.setMembership("Gold");
        boolean success = service.downgradeTo("Gold");
        assertFalse(success, "Downgrade to non-Silver should fail");
    }

    @Test
    void testAddPaymentCreatesPaymentRecord() {
        MembershipPlan gold = service.findPlanByName("Gold");
        service.addPayment(gold, "Manual test payment");
        assertEquals(1, service.getPayments().size());
        Payment p = service.getPayments().get(0);
        assertEquals("Gold", p.getPlan().getName());
        assertTrue(p.toString().contains("Manual test payment"));
    }
}
>>>>>>> b76d39f (5th Commit test cases updated)
