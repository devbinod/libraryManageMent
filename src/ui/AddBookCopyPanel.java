package ui;

import exceptions.LibrarySystemException;
import controller.BookController;
import services.dao.IBook;

import javax.swing.*;
import java.awt.*;

public class AddBookCopyPanel {
    private final IBook iBook = new BookController();
    private final JPanel mainPanel;
    private JPanel topPanel;
    private JPanel outerMiddle;


    private JTextField isbn;

    public AddBookCopyPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineOuterMiddle();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(outerMiddle, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void clearData() {
        isbn.setText("");
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AddBookLabel = new JLabel("Add Book Copy");
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

        JLabel bookIsbn = new JLabel("Enter ISBN");

        isbn = new JTextField(10);

        leftPanel.add(bookIsbn);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));


        rightPanel.add(isbn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        middlePanel.add(leftPanel);
        middlePanel.add(rightPanel);
        outerMiddle.add(middlePanel, BorderLayout.NORTH);

        //add button at bottom
        JButton addBookButton = new JButton("Add Book Copy");
        attachAddBookButtonListener(addBookButton);
        JPanel addBookButtonPanel = new JPanel();
        addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        addBookButtonPanel.add(addBookButton);
        outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);

    }

    private void attachAddBookButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            String bookID = isbn.getText();

            if (bookID.length() == 0) {
                JOptionPane.showMessageDialog(mainPanel, "ISBN cannot be empty");
                return;
            }
            try {
                iBook.addBookCopy(bookID);
                JOptionPane.showMessageDialog(mainPanel,"Book copy has been successfully added");
            } catch (LibrarySystemException e) {
                JOptionPane.showMessageDialog(mainPanel, e.getMessage());
            }
        });
    }
}
