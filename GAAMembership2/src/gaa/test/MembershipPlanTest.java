package gaa.test;

import gaa.MembershipPlan;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MembershipPlanTest {

    @Test
    void testConstructorAndGetters() {
        MembershipPlan plan = new MembershipPlan("Gold", 80, 365, "Gym + track access");

        assertEquals("Gold", plan.getName());
        assertEquals(80, plan.getPrice());
        assertEquals(365, plan.getDurationDays());
        assertEquals("Gym + track access", plan.getBenefits());
        assertTrue(plan.toString().contains("Gold"));
        assertTrue(plan.toString().contains("80"));
    }

    @Test
    void testDefaultPlansSizeAndNames() {
        List<MembershipPlan> plans = MembershipPlan.defaultPlans();
        assertNotNull(plans);
        assertEquals(3, plans.size(), "There should be 3 default plans");

        assertEquals("Silver", plans.get(0).getName());
        assertEquals("Gold", plans.get(1).getName());
        assertEquals("Platinum", plans.get(2).getName());
    }

    @Test
    void testDefaultPlansBenefits() {
        List<MembershipPlan> plans = MembershipPlan.defaultPlans();

        assertTrue(plans.get(0).getBenefits().toLowerCase().contains("basic"));
        assertTrue(plans.get(1).getBenefits().toLowerCase().contains("gym"));
        assertTrue(plans.get(2).getBenefits().toLowerCase().contains("insurance"));
    }

    @Test
    void testToStringFormat() {
        MembershipPlan plan = new MembershipPlan("Silver", 50, 365, "Basic access");
        String result = plan.toString();
        assertTrue(result.contains("Silver"));
        assertTrue(result.contains("€50"));
    }
}
