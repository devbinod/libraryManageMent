package ui;

import java.awt.*;

import javax.swing.*;

import exceptions.LoginException;
import controller.AuthController;
import services.dao.IAuth;
import util.LoginUtils;


public class LoginWindow extends JFrame implements LibWindow {
    public static final LoginWindow INSTANCE = new LoginWindow();
	
	private boolean isInitialized = false;
	
	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel container;
	
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;
	
	private JTextField username;
	private JTextField password;
	private JLabel label;
	private JButton loginButton;
	private JButton logoutButton;

	IAuth auth = new AuthController();
	private JTextArea messageArea;

	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();
	public void clear() {
		messageBar.setText("");
	}
	
	/* This class is a singleton */
    private LoginWindow () {}
    
    public void init() {
		if(!isInitialized){
			setTitle("Login");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		mainPanel = new JPanel();
    		defineUpperHalf();
    		defineMiddleHalf();
    		defineLowerHalf();
    		BorderLayout bl = new BorderLayout();
    		bl.setVgap(30);
    		mainPanel.setLayout(bl);
    					
    		mainPanel.add(upperHalf, BorderLayout.NORTH);
    		mainPanel.add(middleHalf, BorderLayout.CENTER);
    		mainPanel.add(lowerHalf, BorderLayout.SOUTH);
    		getContentPane().add(mainPanel);
    		isInitialized(true);
    		pack();
    		//setSize(660, 500);

		}
    }
    private void defineUpperHalf() {
    		
    		upperHalf = new JPanel();
    		upperHalf.setLayout(new BorderLayout());
    		defineTopPanel();
    		defineMiddlePanel();
    		defineLowerPanel();
    		upperHalf.add(topPanel, BorderLayout.NORTH);
    		upperHalf.add(middlePanel, BorderLayout.CENTER);
    		upperHalf.add(lowerPanel, BorderLayout.SOUTH);
    		
    	}
    	private void defineMiddleHalf() {
    		middleHalf = new JPanel();
    		middleHalf.setLayout(new BorderLayout());
    		JSeparator s = new JSeparator();
    		s.setOrientation(SwingConstants.HORIZONTAL);
    		//middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
    		middleHalf.add(s, BorderLayout.SOUTH);
    		
    	}
    	private void defineLowerHalf() {

    		lowerHalf = new JPanel();
			lowerHalf.setBorder(BorderFactory.createLineBorder(Color.PINK));
    		lowerHalf.setLayout(new GridBagLayout());

			messageArea = new JTextArea("");
			messageArea.setEditable(false);
			messageArea.setForeground(Util.ERROR_MESSAGE_COLOR);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.BOTH;
			lowerHalf.add(messageArea,gbc);

    	}
    	private void defineTopPanel() {
    		topPanel = new JPanel();
    		JPanel intPanel = new JPanel(new BorderLayout());
    		intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
    		JLabel loginLabel = new JLabel("Login");
    		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
    		intPanel.add(loginLabel, BorderLayout.CENTER);
			JButton backButton = new JButton("<= Back to Main");
			addBackButtonListener(backButton);
			topPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5,0,5,0);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.fill = GridBagConstraints.BOTH;
			topPanel.add(backButton, gbc);
			gbc.fill = GridBagConstraints.NONE;
			gbc.gridy = 1;
			topPanel.add(intPanel, gbc);
			if(!LoginUtils.isLoggedIn()){
				backButton.setVisible(false);
			}else{
				backButton.setVisible(true);
			}
    	}
    	
    	
    	
    	private void defineMiddlePanel() {
    		middlePanel=new JPanel();
			GridBagLayout gridBagLayout = new GridBagLayout();
    		middlePanel.setLayout(gridBagLayout);
    		defineLeftTextPanel();
    		defineRightTextPanel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5,0,5,0);
    		middlePanel.add(leftTextPanel, gbc);
    		middlePanel.add(rightTextPanel, gbc);
    	}
    	private void defineLowerPanel() {
    		lowerPanel = new JPanel();
    		loginButton = new JButton("Login");
    		addLoginButtonListener(loginButton);
    		lowerPanel.add(loginButton);
    	}

    	private void defineLeftTextPanel() {
    		
    		JPanel topText = new JPanel();
    		JPanel bottomText = new JPanel();
    		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
    		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));		
    		
    		username = new JTextField(10);
    		label = new JLabel("Username");
    		label.setFont(Util.makeSmallFont(label.getFont()));
    		topText.add(username);
    		bottomText.add(label);
    		
    		leftTextPanel = new JPanel();
    		leftTextPanel.setLayout(new BorderLayout());
    		leftTextPanel.add(topText,BorderLayout.NORTH);
    		leftTextPanel.add(bottomText,BorderLayout.CENTER);
    	}
    	private void defineRightTextPanel() {
    		
    		JPanel topText = new JPanel();
    		JPanel bottomText = new JPanel();
    		topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
    		bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));		
    		
    		password = new JPasswordField(10);
    		label = new JLabel("Password");
    		label.setFont(Util.makeSmallFont(label.getFont()));
    		topText.add(password);
    		bottomText.add(label);
    		
    		rightTextPanel = new JPanel();
    		rightTextPanel.setLayout(new BorderLayout());
    		rightTextPanel.add(topText,BorderLayout.NORTH);
    		rightTextPanel.add(bottomText,BorderLayout.CENTER);
    	}
    	
    	private void addBackButtonListener(JButton butn) {
    		butn.addActionListener(evt -> {
    			LibrarySystem.hideAllWindows();
    			LibrarySystem.INSTANCE.setVisible(true);
    		});
    	}
    	
    	private void addLoginButtonListener(JButton butn) {
    		butn.addActionListener(evt -> {
				try {
					String pass = password.getText();
					password.setText("");
					auth.login(username.getText(), pass);
//					auth.login("103", "111");
				} catch (LoginException e) {
					messageArea.setText("Username or password is invalid.");
					System.out.println(e.getMessage());
					return;
				}
				JOptionPane.showMessageDialog(this,"Successful Login\nWelcome back "+username.getText());

				LibrarySystem.hideAllWindows();
				LibrarySystem.INSTANCE.setVisible(true);
    		});
    	}
	
        
    
}
