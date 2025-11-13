package gaa.test;

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

