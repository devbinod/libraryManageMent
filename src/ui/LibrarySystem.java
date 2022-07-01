package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import controller.AuthController;
import services.dao.IAuth;
import dataaccess.Auth;


public class LibrarySystem extends JFrame implements LibWindow {
	IAuth iAuth = new AuthController();
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
    JMenuItem login;

	JTabbedPane tabbedPane;

	String pathToImage;
    private boolean isInitialized = false;
    
    private static LibWindow[] allWindows = { 
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE
	};

	private static Class[] librarianPages = {CheckoutBookPanel.class, AllBookIdsWindow.class, CheckoutRecordPanel.class};
	private static Class[] adminPages = {DashboardPanel.class,NewMemberPanel.class, AddBookPanel.class, AllBookIdsWindow.class, AddBookCopyPanel.class};

	public static void hideAllWindows() {
		
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);
			
		}
	}



    private LibrarySystem() {}
    
    public void init() {
		if(!isInitialized){
			formatContentPane();
			setPathToImage();
			updateTabs();
//			insertSplashImage();

			createMenus();
			//pack();
			setSize(720,500);
			isInitialized = true;
		}
    }

	public void showLogin(){
		LibrarySystem.hideAllWindows();
		LoginWindow.INSTANCE.init();
		Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
		LoginWindow.INSTANCE.setVisible(true);
	}
    
    private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		getContentPane().add(mainPanel);	
	}
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+"\\src\\librarysystem\\library.jpg";
    }
    
    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));
    }

	private void updateTabs() {
		if(tabbedPane == null){
			tabbedPane = new JTabbedPane();
		}

		tabbedPane.removeAll();

		List<Class> authList = new ArrayList<>();
		List<String> tabTitles = new ArrayList<>();
		if(iAuth.getAuth() != null) {
			if (iAuth.getAuth() == Auth.LIBRARIAN) {
				authList.addAll(Arrays.stream(librarianPages).toList());
			}else if (iAuth.getAuth() == Auth.ADMIN) {
				authList.addAll(Arrays.stream(adminPages).toList());
			}else if (iAuth.getAuth() == Auth.BOTH) {
				authList.addAll(Arrays.stream(librarianPages).toList());
				authList.addAll(Arrays.stream(adminPages).toList());
			}
		}

		if(authList.contains(DashboardPanel.class)){

			tabbedPane.addTab("Dashboard", DashboardPanel.getInstance().getMainPanel());
			tabTitles.add("Dashboard");
		}
		if(authList.contains(AllMemberIdsWindow.class)){
			tabbedPane.addTab("All Members", AllMemberIdsWindow.getInstance().getMainPanel());
			tabTitles.add("All Members");
		}
		if(authList.contains(AllBookIdsWindow.class)){
			tabbedPane.addTab("All Books", AllBookIdsWindow.getInstance().getMainPanel());
			tabTitles.add("All Books");
		}
		if(authList.contains(AddBookPanel.class)){
			AddBookPanel addBook = new AddBookPanel();
			JPanel addBookPanel = addBook.getMainPanel();
			tabbedPane.addTab("Add Book", addBookPanel);
		}
		if(authList.contains(AddBookCopyPanel.class)){
			AddBookCopyPanel addBookCopy = new AddBookCopyPanel();
			JPanel addBookCopyPanel = addBookCopy.getMainPanel();
			tabbedPane.addTab("Add Book Copy", addBookCopyPanel);
		}
		if(authList.contains(NewMemberPanel.class)){
			NewMemberPanel newMemberPanel = new NewMemberPanel();
			JPanel newMember = newMemberPanel.getMainPanel();
			tabbedPane.addTab("Add New Member", newMember);
		}
		if(authList.contains(CheckoutBookPanel.class)){
			CheckoutBookPanel checkoutBookPanel = new CheckoutBookPanel();
			JPanel myCheckoutBook = checkoutBookPanel.getMainPanel();
			tabbedPane.addTab("Checkout Book", myCheckoutBook);
		}
		if(authList.contains(CheckoutRecordPanel.class)){
			CheckoutRecordPanel checkoutRecordPanel = new CheckoutRecordPanel();
			JPanel myCheckoutRecord = checkoutRecordPanel.getMainPanel();
			tabbedPane.addTab("Checkout Record", myCheckoutRecord);
		}
		if(tabbedPane.getTabCount() > 0){
			tabbedPane.setSelectedIndex(0);
		}
		if(!isInitialized){
			mainPanel.add(tabbedPane);
			tabbedPane.addChangeListener(l -> {
				int indexSelected = tabbedPane.getSelectedIndex();
				if(indexSelected >= 0 && indexSelected < tabbedPane.getTabCount()) {
					if (tabbedPane.getTitleAt(indexSelected).equals("All Books")) {
						AllBookIdsWindow.getInstance().listBookIDS();
					}
					if (tabbedPane.getTitleAt(indexSelected).equals("Dashboard")) {
						DashboardPanel.getInstance().loadUserCount();
					}
				}
			});
		}
	}


    private void createMenus() {
    	menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);		
    }
    
    private void addMenuItems() {
       options = new JMenu("Options");  
 	   menuBar.add(options);
 	   login = new JMenuItem("Logout");
 	   login.addActionListener(new LoginListener());
 	   options.add(login);
    }

	@Override
	public void setVisible(boolean b) {
		updateTabs();
		super.setVisible(b);
	}

	class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			iAuth.logout();
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);
			
		}
    	
    }

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
    
}
