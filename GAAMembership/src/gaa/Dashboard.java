package gaa;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {

    private static final long serialVersionUID = 1L;
	private final MembershipService svc;
    private final DefaultListModel<MembershipPlan> planModel = new DefaultListModel<>();
    private final DefaultListModel<String> paymentModel = new DefaultListModel<>();
    private JLabel nameLabel;
    private JLabel membershipLabel;

    public Dashboard(MembershipService svc) {
        super("GAA Membership Dashboard");
        this.svc = svc;
        setSize(700, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        User user = svc.getUser();

        // Top panel - user info
        JPanel top = new JPanel();
        top.setBorder(BorderFactory.createTitledBorder("User Information"));
        top.setLayout(new GridLayout(3,2,8,8));

        nameLabel = new JLabel("Name: " + user.getName());
        JButton editBtn = new JButton("Edit Name");

        editBtn.addActionListener(e -> {
            String newName = JOptionPane.showInputDialog(this,"Enter new name:", user.getName());
            if(newName != null && !newName.isBlank()){
                user.setName(newName);
                nameLabel.setText("Name: " + newName);
            }
        });

        membershipLabel = new JLabel("Membership: " + user.getMembership());

        top.add(nameLabel);
        top.add(editBtn);
        top.add(new JLabel("Email: " + user.getEmail()));
        top.add(new JLabel(""));
        top.add(membershipLabel);
        top.add(new JLabel("Expiry: " + user.getExpiry()));

        add(top, BorderLayout.NORTH);

        // Center - plans + payments
        JPanel center = new JPanel(new GridLayout(1,2,10,10));
        center.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Plans panel
        JPanel planPanel = new JPanel(new BorderLayout(8,8));
        planPanel.setBorder(BorderFactory.createTitledBorder("Available Plans"));
        JList<MembershipPlan> planList = new JList<>(planModel);
        planList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        planPanel.add(new JScrollPane(planList), BorderLayout.CENTER);

        JPanel planButtons = new JPanel();
        JButton up = new JButton("Upgrade");
        JButton down = new JButton("Downgrade");

        up.addActionListener(e -> {
            MembershipPlan p = planList.getSelectedValue();
            if(p != null){

                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to upgrade to " + p.getName() + "?",
                        "Confirm Upgrade",
                        JOptionPane.YES_NO_OPTION
                );

                if(confirm == JOptionPane.YES_OPTION) {
                    svc.upgrade(p.getName());
                    refreshAfterChange();
                    JOptionPane.showMessageDialog(this, "Upgraded to " + p.getName());
                }

            } else {
                JOptionPane.showMessageDialog(this,"Please select a plan to upgrade to.");
            }
        });


        down.addActionListener(e -> {
            MembershipPlan p = planList.getSelectedValue();
            if(p != null){

                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to downgrade to " + p.getName() + "?",
                        "Confirm Downgrade",
                        JOptionPane.YES_NO_OPTION
                );

                if(confirm == JOptionPane.YES_OPTION) {
                    svc.downgrade(p.getName());
                    refreshAfterChange();
                    JOptionPane.showMessageDialog(this, "Downgraded to " + p.getName());
                }

            } else {
                JOptionPane.showMessageDialog(this,"Please select a plan to downgrade to.");
            }
        });


        planButtons.add(up);
        planButtons.add(down);
        planPanel.add(planButtons, BorderLayout.SOUTH);

        center.add(planPanel);

        // Payments panel
        JPanel payPanel = new JPanel(new BorderLayout(8,8));
        payPanel.setBorder(BorderFactory.createTitledBorder("Payment History"));
        JList<String> payList = new JList<>(paymentModel);
        payPanel.add(new JScrollPane(payList), BorderLayout.CENTER);

        center.add(payPanel);

        add(center, BorderLayout.CENTER);

        // Fill models
        refreshModels();
    }

    private void refreshModels() {
        planModel.clear();
        List<MembershipPlan> plans = svc.getPlans();
        for (MembershipPlan p : plans) planModel.addElement(p);

        paymentModel.clear();
        for (String s : svc.getPayments()) paymentModel.addElement(s);

        membershipLabel.setText("Membership: " + svc.getUser().getMembership());
    }

    private void refreshAfterChange() {
        // after upgrade/downgrade
        refreshModels();
        nameLabel.setText("Name: " + svc.getUser().getName());
    }
}
