package gaa;

public class User {
    private String name;
    private String email;
    private String membership;
    private String expiry;

    public User(String name, String email, String membership, String expiry) {
        this.name = name;
        this.email = email;
        this.membership = membership;
        this.expiry = expiry;
    }

    public String getName() { return name; }
    
    public void setName(String n) { name = n; }

    public String getEmail() { return email; }

    public String getMembership() { return membership; }
    
    public void setMembership(String m) { membership = m; }

    public String getExpiry() { return expiry; }
}
