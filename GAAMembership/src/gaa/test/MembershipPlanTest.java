package gaa.test;

import org.junit.jupiter.api.Test;

import gaa.MembershipPlan;

import static org.junit.jupiter.api.Assertions.*;

class MembershipPlanTest {

    @Test
    void testConstructorAndGetters() {
        MembershipPlan plan = new MembershipPlan("Gold", 60.0, "1 year", "Gym + Track");

        assertEquals("Gold", plan.getName());
        assertEquals(60.0, plan.getPrice());
        assertEquals("1 year", plan.getDuration());
        assertEquals("Gym + Track", plan.getBenefits());
    }

    @Test
    void testToString() {
        MembershipPlan plan = new MembershipPlan("Platinum", 100.0, "1 year", "All facilities + Insurance");
        String expected = "Platinum (€100.0)";
        assertEquals(expected, plan.toString());
    }
}
