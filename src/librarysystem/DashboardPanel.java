package librarysystem;

import business.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardPanel {

    public JPanel getMainPanel() {
        return mainPanel;
    }

    boolean isInitialized = false;

    private JPanel topPanel;
    private JPanel mainPanel;
    private JPanel outerMiddle;

    private Dashboard countStatus;
    JLabel userCount;
    JLabel bookCount;

    public void setCountStatus(Dashboard countStatus) {
        this.countStatus = countStatus;
    }

    private final ControllerInterface ci = new SystemController();

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
        setCountStatus(ci.getDashboardCount());
        isInitialized = true;
        System.out.println(" book list   "+ countStatus.getTotalNumberOfBook());

        if (userCount != null) userCount.setText("No of Users  :  " + countStatus.getTotalNumberOfUser());
        if (bookCount != null) bookCount.setText("No Of Books   " + countStatus.getTotalNumberOfBook());
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

        userCount = new JLabel("No of Users  :  " + countStatus.getTotalNumberOfUser());
        bookCount = new JLabel("No Of Books   " + countStatus.getTotalNumberOfBook());

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
