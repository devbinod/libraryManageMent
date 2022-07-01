package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.BookController;
import services.dao.IBook;


public class AllBookIdsWindow {
	private static final long serialVersionUID = 1L;
	private static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    IBook iBook = new BookController();
    private boolean isInitialized = false;
	
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private TextArea textArea;

	private JTable table;
	private boolean tableDataSet;

	//Singleton class
	private AllBookIdsWindow() {}

	public static AllBookIdsWindow getInstance(){
		INSTANCE.init();
		return INSTANCE;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void init() {
		if(!isInitialized) {
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			defineTopPanel();
			defineMiddlePanel();
			defineLowerPanel();

			mainPanel.add(topPanel, BorderLayout.NORTH);
			mainPanel.add(middlePanel, BorderLayout.CENTER);
			mainPanel.add(lowerPanel, BorderLayout.SOUTH);
			mainPanel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

			isInitialized = true;
			listBookIDS();
		}
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Book List");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}
	
	public void defineMiddlePanel() {
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
		middlePanel.setLayout(fl);

	}
	
	public void defineLowerPanel() {
		
		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
//		lowerPanel.add(backToMainButn);
	}
	
	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);

		}
	}
	

	public void listBookIDS() {
		List<String[]> bookList = iBook.getAllBookList();

		if (!tableDataSet) {
			JScrollPane scrollPane;

			DefaultTableModel tableModel = new DefaultTableModel(){
				@Override
				public boolean isCellEditable(int row, int column) {
					//all cells false
					return false;
				}


			};
			tableModel.addColumn("S.N");
			tableModel.addColumn("ISIN ");
			tableModel.addColumn("Book Name");
			tableModel.addColumn("No Of Copies");
			tableModel.addColumn("Available Book");


			for (String[] rec : bookList) {
				tableModel.addRow(rec);
			}
			tableModel.addRow(new String[]{});

			table = new JTable(tableModel);
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(1).setPreferredWidth(150);
			table.getColumnModel().getColumn(2).setPreferredWidth(200);
			table.getColumnModel().getColumn(3).setPreferredWidth(150);
			table.getColumnModel().getColumn(4).setPreferredWidth(150);
			table.setMinimumSize(new Dimension(500, 150));
			table.setPreferredScrollableViewportSize(table.getPreferredSize());
			table.setFillsViewportHeight(true);
			scrollPane = new JScrollPane(table);

			middlePanel.add(scrollPane);
			tableDataSet = true;
		}else {
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.getDataVector().removeAllElements();
			tableModel.setRowCount(0);


			for (String[] rec : bookList) {
				tableModel.addRow(rec);
			}
			table.setModel(tableModel);

		}


	}



	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
		
	}
}
