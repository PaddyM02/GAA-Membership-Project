package gaa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Dashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private final MembershipService svc;
    private final AuthService auth;

    private JLabel nameLabel = new JLabel();
    private JLabel membershipLabel = new JLabel();
    private DefaultListModel<String> paymentModel = new DefaultListModel<>();
    private JList<String> paymentList = new JList<>(paymentModel);

    public Dashboard(MembershipService svc, AuthService auth) {
        super("GAA Membership - Dashboard");
        this.svc = svc;
        this.auth = auth;
        init();
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void init() {
        JTabbedPane tabs = new JTabbedPane();

        // Profile tab
        JPanel profile = new JPanel(new BorderLayout(8,8));
        JPanel info = new JPanel(new GridLayout(3,1));
        nameLabel.setText("Name: " + svc.getUser().getName());
        membershipLabel.setText("Membership: " + svc.getUser().getMembership());
        info.add(nameLabel);
        info.add(membershipLabel);
        JButton edit = new JButton("Edit Profile");
        edit.addActionListener((ActionEvent e) -> editProfile());
        info.add(edit);
        profile.add(info, BorderLayout.NORTH);

        // Payments tab
        JPanel payments = new JPanel(new BorderLayout(6,6));
        payments.add(new JScrollPane(paymentList), BorderLayout.CENTER);
        JButton refresh = new JButton("Refresh"); 
        refresh.addActionListener((ActionEvent e) -> refreshModels());
        payments.add(refresh, BorderLayout.SOUTH);

        // Membership tab
        JPanel membership = new JPanel(new GridLayout(0,1,6,6));
        for (MembershipPlan p : svc.getPlans()) {
            JButton b = new JButton("Purchase/Upgrade to " + p.getName() + " - €" + p.getPrice());
            b.addActionListener((ActionEvent e) -> {
                svc.upgradeTo(p.getName());
                refreshAfterChange();
                JOptionPane.showMessageDialog(this, "Changed to " + p.getName());
            });
            membership.add(b);
        }
        JButton downgrade = new JButton("Downgrade to Silver");
        downgrade.addActionListener((ActionEvent e) -> {
            svc.downgradeTo("Silver");
            refreshAfterChange();
            JOptionPane.showMessageDialog(this, "Downgraded to Silver");
        });
        JButton eligibility = new JButton("Check Upgrade Eligibility");
        eligibility.addActionListener((ActionEvent e) -> {
            String s = svc.determineEligibility();
            JOptionPane.showMessageDialog(this, s);
        });
        membership.add(eligibility);
        membership.add(downgrade);

        tabs.addTab("Profile", profile);
        tabs.addTab("Payments", payments);
        tabs.addTab("Membership", membership);

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
        add(new JLabel("Welcome to the GAA Membership System"), BorderLayout.NORTH);

        refreshModels();
    }

    private void editProfile() {
        String name = JOptionPane.showInputDialog(this, "Enter your name:", svc.getUser().getName());
        if (name != null && !name.trim().isEmpty()) {
            svc.getUser().setName(name.trim());
            refreshAfterChange();
        }
    }

    private void refreshModels() {
        paymentModel.clear();
        for (String s : svc.getUser().getPaymentStrings()) paymentModel.addElement(s);
        membershipLabel.setText("Membership: " + svc.getUser().getMembership());
        nameLabel.setText("Name: " + svc.getUser().getName());
    }

    private void refreshAfterChange() {
        refreshModels();
    }
}
