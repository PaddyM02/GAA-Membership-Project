package gaa.test;

<<<<<<< HEAD
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
=======
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
>>>>>>> b76d39f (5th Commit test cases updated)
    }
}
