package ui;

import java.awt.*;

import javax.swing.JFrame;



public class Main {

	public static void main(String[] args) {
	      EventQueue.invokeLater(() -> 
	         {
				 LibrarySystem.INSTANCE.setTitle("BookTech Library Management");
				 LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				 LibrarySystem.INSTANCE.init();
				 centerFrameOnDesktop(LibrarySystem.INSTANCE);
				 LibrarySystem.INSTANCE.setVisible(true);
				 LibrarySystem.INSTANCE.showLogin();
			 });
	   }
	   
	   public static void centerFrameOnDesktop(Component f) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int height = toolkit.getScreenSize().height;
			int width = toolkit.getScreenSize().width;
			int frameHeight = f.getSize().height;
			int frameWidth = f.getSize().width;
		   f.setMinimumSize(new Dimension(width/2,height/2));
		   f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
		}
}
