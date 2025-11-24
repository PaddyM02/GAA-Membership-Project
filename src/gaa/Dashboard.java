package gaa;

import javax.swing.*;
<<<<<<< HEAD
=======
import javax.swing.border.EmptyBorder;
>>>>>>> b76d39f (5th Commit test cases updated)
import java.awt.*;
import java.awt.event.ActionEvent;

public class Dashboard extends JFrame {

    private static final long serialVersionUID = 1L;
<<<<<<< HEAD
    private final MembershipService svc;
    private final AuthService auth;

    private JLabel nameLabel = new JLabel();
    private JLabel membershipLabel = new JLabel();
    private DefaultListModel<String> paymentModel = new DefaultListModel<>();
    private JList<String> paymentList = new JList<>(paymentModel);
=======

    private final MembershipService svc;
    @SuppressWarnings("unused")
	private final AuthService auth;

    private final JLabel nameLabel = new JLabel();
    private final JLabel membershipLabel = new JLabel();
    private final DefaultListModel<String> paymentModel = new DefaultListModel<>();
    private final JList<String> paymentList = new JList<>(paymentModel);
>>>>>>> b76d39f (5th Commit test cases updated)

    public Dashboard(MembershipService svc, AuthService auth) {
        super("GAA Membership - Dashboard");
        this.svc = svc;
        this.auth = auth;
<<<<<<< HEAD
        init();
        setSize(700, 420);
=======

        initializeUI();
        setSize(750, 450);
>>>>>>> b76d39f (5th Commit test cases updated)
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

<<<<<<< HEAD
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
=======
    private void initializeUI() {
        // Gradient background panel (dark theme)
        JPanel background = new JPanel() {
            private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(30, 30, 30); // dark gray
                Color color2 = new Color(10, 10, 10); // almost black
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setOpaque(false); // transparent tabs
        tabs.addTab("Profile", createCardPanel(createProfilePanel()));
        tabs.addTab("Payments", createCardPanel(createPaymentsPanel()));
        tabs.addTab("Membership", createCardPanel(createMembershipPanel()));

        background.add(tabs, BorderLayout.CENTER);

        JLabel header = new JLabel("Welcome to the GAA Membership System", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        header.setForeground(Color.WHITE);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        background.add(header, BorderLayout.NORTH);

        refreshUI();
    }

    // Wraps any panel in a dark "card" with rounded corners
    private JPanel createCardPanel(JPanel innerPanel) {
        JPanel card = new JPanel() {
            private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(40, 40, 40)); // dark gray card
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.add(innerPanel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createProfilePanel() {
        JPanel profile = new JPanel();
        profile.setOpaque(false);
        profile.setLayout(new BoxLayout(profile, BoxLayout.Y_AXIS));

        updateProfileLabels();
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        membershipLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setForeground(Color.WHITE);
        membershipLabel.setForeground(Color.WHITE);

        JButton editButton = new JButton("Edit Profile");
        editButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        editButton.addActionListener((ActionEvent e) -> editProfile());

        profile.add(nameLabel);
        profile.add(Box.createRigidArea(new Dimension(0, 10)));
        profile.add(membershipLabel);
        profile.add(Box.createRigidArea(new Dimension(0, 20)));
        profile.add(editButton);

        return profile;
    }

    private JPanel createPaymentsPanel() {
        JPanel payments = new JPanel(new BorderLayout(10, 10));
        payments.setOpaque(false);

        paymentList.setVisibleRowCount(10);
        paymentList.setFixedCellWidth(600);
        paymentList.setBackground(new Color(50, 50, 50)); // dark list
        paymentList.setForeground(Color.WHITE);
        payments.add(new JScrollPane(paymentList), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener((ActionEvent e) -> refreshUI());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);
        payments.add(buttonPanel, BorderLayout.SOUTH);

        return payments;
    }

    private JPanel createMembershipPanel() {
        JPanel membership = new JPanel();
        membership.setOpaque(false);
        membership.setLayout(new BoxLayout(membership, BoxLayout.Y_AXIS));

        for (MembershipPlan plan : svc.getPlans()) {
            JButton planButton = new JButton("Purchase/Upgrade to " + plan.getName() + " - €" + plan.getPrice());
            planButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            planButton.addActionListener((ActionEvent e) -> {
                svc.upgradeTo(plan.getName());
                refreshUI();
                JOptionPane.showMessageDialog(this, "Changed to " + plan.getName());
            });
            membership.add(planButton);
            membership.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton downgradeButton = new JButton("Downgrade to Silver");
        downgradeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        downgradeButton.addActionListener((ActionEvent e) -> {
            svc.downgradeTo("Silver");
            refreshUI();
            JOptionPane.showMessageDialog(this, "Downgraded to Silver");
        });

        JButton eligibilityButton = new JButton("Check Upgrade Eligibility");
        eligibilityButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        eligibilityButton.addActionListener((ActionEvent e) -> {
            String message = svc.determineEligibility();
            JOptionPane.showMessageDialog(this, message);
        });

        membership.add(eligibilityButton);
        membership.add(Box.createRigidArea(new Dimension(0, 10)));
        membership.add(downgradeButton);

        return membership;
    }

    private void editProfile() {
        String newName = JOptionPane.showInputDialog(this, "Enter your name:", svc.getUser().getName());
        if (newName != null && !newName.trim().isEmpty()) {
            svc.getUser().setName(newName.trim());
            refreshUI();
        }
    }

    private void refreshUI() {
        paymentModel.clear();
        for (String payment : svc.getUser().getPaymentStrings()) {
            paymentModel.addElement(payment);
        }
        updateProfileLabels();
    }

    private void updateProfileLabels() {
        nameLabel.setText("Name: " + svc.getUser().getName());
        membershipLabel.setText("Membership: " + svc.getUser().getMembership());
>>>>>>> b76d39f (5th Commit test cases updated)
    }
}
