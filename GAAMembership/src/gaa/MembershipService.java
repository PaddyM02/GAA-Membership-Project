package gaa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MembershipService {
    private User user;
   
    private List<MembershipPlan> plans = new ArrayList<>();
    
    private List<String> payments = new ArrayList<>();

    public MembershipService() {
    
    	plans.add(new MembershipPlan("Silver",30,"1 year","Basic access"));
        
    	plans.add(new MembershipPlan("Gold",60,"1 year","Gym + Track"));
        
    	plans.add(new MembershipPlan("Platinum",100,"1 year","All facilities + Insurance"));

        user = new User("Jack O'Dea","jack@example.com","Silver","2026-03-15");
        
        payments.add("2024-03-16: Silver - €30");
    }

    public User getUser(){ return user; }
    
    public List<MembershipPlan> getPlans(){ return plans; }
    
    public List<String> getPayments(){ return payments; }

    public void upgrade(String target){
        user.setMembership(target);
        payments.add(new Date() + ": " + user.getName() + " upgraded to " + target);
    }

    public void downgrade(String target){
        user.setMembership(target);
        
        payments.add(new Date() + ": " + user.getName() + " downgraded to " + target);
    }

}
