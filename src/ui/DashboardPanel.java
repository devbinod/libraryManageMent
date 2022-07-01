package ui;

import controller.DashboardController;
import services.dao.IDashboard;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel {

    public JPanel getMainPanel() {
        return mainPanel;
    }

    boolean isInitialized = false;

    private JPanel topPanel;
    private JPanel mainPanel;
    private JPanel outerMiddle;

    JLabel userCount;
    JLabel bookCount;


    private final IDashboard iDashboard = new DashboardController();

    private static final DashboardPanel INSTANCE = new DashboardPanel();

    public static DashboardPanel getInstance() {

        INSTANCE.init();
        return INSTANCE;
    }


    public void init() {

        if (!isInitialized) {
            loadUserCount();
            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            defineTopPanel();
            defineOuterMiddle();
            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(outerMiddle, BorderLayout.CENTER);
            isInitialized = true;
        }
    }

    void loadUserCount() {
        isInitialized = true;

        if (userCount != null) userCount.setText("No of Users  :  " + iDashboard.getUserCount());
        if (bookCount != null) bookCount.setText("No Of Books   " + iDashboard.getBookCount());
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AddBookLabel = new JLabel("Dashboard Status");
        Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AddBookLabel);
    }

    public void defineOuterMiddle() {
        outerMiddle = new JPanel();
        outerMiddle.setLayout(new BorderLayout());

        //set up left and right panels
        JPanel middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        userCount = new JLabel("No of Users  :  " + iDashboard.getUserCount());
        bookCount = new JLabel("No Of Books   " + iDashboard.getBookCount());

        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(userCount);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(bookCount);


        middlePanel.add(leftPanel);
        middlePanel.add(rightPanel);
        outerMiddle.add(middlePanel, BorderLayout.NORTH);
        JPanel addBookButtonPanel = new JPanel();
        addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);
    }

}
