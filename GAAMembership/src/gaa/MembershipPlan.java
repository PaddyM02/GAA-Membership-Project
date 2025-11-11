package gaa;

public class MembershipPlan {
   
	private String name;
    
	private double price;
    
	private String duration;
    
	private String benefits;

    
    public MembershipPlan(String n, double p, String d, String b) {
        name=n; price=p; duration=d; benefits=b;
    
    }

    
    public String getName(){return name;}
    
    public double getPrice(){return price;}
    
    public String getDuration(){return duration;}
    
    public String getBenefits(){return benefits;}

    @Override
    public String toString(){
        return name + " (€" + price + ")";
    
    }
}
