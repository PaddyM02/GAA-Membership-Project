package gaa;

import java.time.LocalDate;

public class Payment {
    private final LocalDate date;
    private final MembershipPlan plan;
    private final int amountCents;
    private final String description;
    private final String transactionId;

    public Payment(LocalDate date, MembershipPlan plan, int amountCents, String description, String transactionId) {
        this.date = date;
        this.plan = plan;
        this.amountCents = amountCents;
        this.description = description;
        this.transactionId = transactionId;
    }

    public LocalDate getDate() { return date; }
    public MembershipPlan getPlan() { return plan; }
    public int getAmountCents() { return amountCents; }
    public String getDescription() { return description; }
    public String getTransactionId() { return transactionId; }

    @Override
    public String toString() {
        return date + " - " + plan + " - €" + (amountCents / 100.0) + " - " + description + " (tx:" + transactionId + ")";
    }
}
