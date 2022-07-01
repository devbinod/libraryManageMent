package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;


public class AllMemberIdsWindow {
	private static final AllMemberIdsWindow INSTANCE = new AllMemberIdsWindow();
    ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea;

	private AllMemberIdsWindow() {}

	public static AllMemberIdsWindow getInstance(){
		INSTANCE.init();
		return INSTANCE;
	}

	public void init() {
		if(!isInitialized){
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			defineTopPanel();
			defineMiddlePanel();
			defineLowerPanel();
			mainPanel.add(topPanel, BorderLayout.NORTH);
			mainPanel.add(middlePanel, BorderLayout.CENTER);
			mainPanel.add(lowerPanel, BorderLayout.SOUTH);
			listMembers();
			isInitialized = true;
		}
	}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Member IDs");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}
	
	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);
		textArea = new TextArea(8,20);
		middlePanel.add(textArea);
		
	}
	
	public void defineLowerPanel() {
		lowerPanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		lowerPanel.setLayout(fl);
		JButton backButton = new JButton("<== Back to Main");
		addBackButtonListener(backButton);
//		lowerPanel.add(backButton);
	}
	
	public void setData(String data) {
		textArea.setText(data);
	}
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
		   LibrarySystem.hideAllWindows();
		   LibrarySystem.INSTANCE.setVisible(true);
	    });
	}

	private void listMembers(){
		List<String> ids = ci.allMemberIds();
               System.out.println("=====ddd"+ids);
		Collections.sort(ids);
		StringBuilder sb = new StringBuilder();
		for(String s: ids) {
			sb.append(s + "\n");
		}
		INSTANCE.setData(sb.toString());
		mainPanel.repaint();
	}

	public boolean isInitialized() {
		
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
		
	}
	
}


