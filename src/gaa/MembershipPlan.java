package gaa;

import java.util.ArrayList;
import java.util.List;

public class MembershipPlan {
    private final String name;
    private final int price;
    private final int durationDays;
    private final String benefits;

    public MembershipPlan(String name, int price, int durationDays, String benefits) {
        this.name = name;
        this.price = price;
        this.durationDays = durationDays;
        this.benefits = benefits;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getDurationDays() { return durationDays; }
    public String getBenefits() { return benefits; }

    public static List<MembershipPlan> defaultPlans() {
        List<MembershipPlan> list = new ArrayList<>();
        list.add(new MembershipPlan("Silver", 50, 365, "Basic access"));
        list.add(new MembershipPlan("Gold", 80, 365, "Gym + walking track"));
        list.add(new MembershipPlan("Platinum", 150, 365, "All access + insurance"));
        return list;
    }

    @Override
    public String toString() {
        return name + " (€" + price + ")";
    }
}
