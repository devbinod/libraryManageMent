package librarysystem;

import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class CheckoutRecordPanel {
    ControllerInterface ci = new SystemController();

    private JTable table;
    private JPanel topPanel;
    private JPanel lowerPanel;
    private JPanel middlePanel;
    private boolean tableDataSet;
    private final JPanel mainPanel;

    private JTextField memberIdField;

    private JButton searchButton;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CheckoutRecordPanel() {
        mainPanel = new JPanel();
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);
        mainPanel.setLayout(bl);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
    }

    private void defineTopPanel() {
        topPanel = new JPanel();
        JLabel label = new JLabel("View Member Checkout Record");
        Util.adjustLabelFont(label, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(label);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel memberId = new JLabel("Member ID:");
        middlePanel.add(memberId, FlowLayout.LEFT);

        memberIdField = new JTextField(15);
        middlePanel.add(memberIdField, FlowLayout.CENTER);

        searchButton = new JButton("Search");
        searchButtonListener(searchButton);
        middlePanel.add(searchButton, FlowLayout.RIGHT);
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        lowerPanel.setLayout(fl);
    }

    private void searchButtonListener(JButton searchBtn) {
        searchBtn.addActionListener(e -> {
            JScrollPane scrollPane;
            String memberID = memberIdField.getText().trim();
            if (memberID.length() == 0) {
                JOptionPane.showMessageDialog(mainPanel, "Member ID cannot be empty");
            } else {
                try {
                    List<String[]> records = ci.getCheckoutEntries(memberID);
                    if (records.size() == 0) {
                        if(tableDataSet){
                            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                            tableModel.setRowCount(0);
                        }
                        JOptionPane.showMessageDialog(mainPanel, "No entries found");
                    }else {
                        if (!tableDataSet) {
                            DefaultTableModel tableModel = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int row, int column) {
                                    //all cells false
                                    return false;
                                }
                            };
                            tableModel.addColumn("Member Id");
                            tableModel.addColumn("Full name");
                            tableModel.addColumn("Book ISBN");
                            tableModel.addColumn("Book Title");
                            tableModel.addColumn("Copy Number");
                            tableModel.addColumn("Checkout Date");
                            tableModel.addColumn("Due Date");



                            table = new JTable(tableModel);

                            for (String[] rec : records) {
                                tableModel.addRow(rec);
                            }
                            tableModel.addRow(new String[]{});
                            tableModel.addRow(new String[]{});
                            tableModel.addRow(new String[]{});
                            tableModel.addRow(new String[]{});
                            tableModel.addRow(new String[]{});
                            tableModel.addRow(new String[]{});
                            tableModel.addRow(new String[]{});

                            table.setMinimumSize(new Dimension(500, 70));
                            table.setPreferredScrollableViewportSize(table.getPreferredSize());
                            table.setFillsViewportHeight(true);
                            scrollPane = new JScrollPane(table);

                            lowerPanel.add(scrollPane);
                            tableDataSet = true;
                        } else {
                            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                            tableModel.setRowCount(0);

                            for (String[] rec : records) {
                                tableModel.addRow(rec);
                            }
                            table.setModel(tableModel);
                        }
                        JOptionPane.showMessageDialog(mainPanel, "Checkout records were found successfully");
                        table.setVisible(true);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                } catch (LibrarySystemException e1) {
                    JOptionPane.showMessageDialog(mainPanel, e1.getMessage());
                }
            }
        });
    }

    public void updateData() {
//        StringBuilder sb = new StringBuilder();
//        List<String> bookIds = ci.allBookIds();
//        Collections.sort(bookIds);
//        for (String s : bookIds) {
//            sb.append(s).append("\n");
//        }
//        mainPanel.repaint();
    }
}
