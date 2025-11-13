package gaa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MembershipService {

    private final User user;
    private final List<MembershipPlan> plans = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();

    public MembershipService(User user) {
        this.user = user;
        // initialize plans (could be from DB/config)
        plans.add(new MembershipPlan("Silver", 50, 365, "Basic access"));
        plans.add(new MembershipPlan("Gold", 80, 365, "Gym + walking track"));
        plans.add(new MembershipPlan("Platinum", 150, 365, "All access + insurance"));
        // load user's existing payments if any
        if (user.getPayments() != null) {
            // if pre-populated strings exist in old project, ignore - we maintain Payment objects now
        }
    }

    public User getUser() { return user; }

    public List<MembershipPlan> getPlans() { return plans; }

    public List<Payment> getPayments() { return payments; }

    public void addPayment(MembershipPlan plan, String description) {
        String tx = UUID.randomUUID().toString().substring(0,8);
        Payment p = new Payment(LocalDate.now(), plan, plan.getPrice()*100, description, tx);
        payments.add(p);
        user.getPaymentStrings().add(p.toString());
    }

    public String determineEligibility() {
        MembershipPlan current = user.getMembershipPlanEnum();
        if (current == null) return "No membership assigned";
        if (current.getName().equalsIgnoreCase("Gold")) {
            return "Eligible for Platinum";
        } else if (current.getName().equalsIgnoreCase("Silver")) {
            return "Eligible for Gold or Platinum";
        } else {
            return "Already Platinum or no upgrades available";
        }
    }

    public boolean upgradeTo(String planName) {
        MembershipPlan target = findPlanByName(planName);
        if (target == null) return false;
        MembershipPlan current = user.getMembershipPlanEnum();
        if (current != null && current.getName().equalsIgnoreCase(target.getName())) {
            System.out.println("Already on that plan");
            return false;
        }
        // cannot upgrade above Platinum or downgrade below Silver via this method
        user.setMembership(target.getName());
        user.setExpiryDate(LocalDate.now().plusDays(target.getDurationDays()));
        addPayment(target, "Upgraded to " + target.getName());
        EmailService.sendEmail(user.getEmail(), "Membership changed", "Your membership changed to " + target.getName());
        return true;
    }

    public boolean downgradeTo(String planName) {
        MembershipPlan target = findPlanByName(planName);
        if (target == null) return false;
        if (target.getName().equalsIgnoreCase("Silver")) {
            user.setMembership("Silver");
            user.setExpiryDate(LocalDate.now().plusDays(target.getDurationDays()));
            addPayment(target, "downgraded to Silver");
            EmailService.sendEmail(user.getEmail(), "Membership downgraded", "You were downgraded to Silver");
            return true;
        } else {
            System.out.println("Only downgrade to Silver allowed in demo"); 
            return false;
        }
    }

    public MembershipPlan findPlanByName(String name) {
        for (MembershipPlan p : plans) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }
}
