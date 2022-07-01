package ui;

import controller.BookController;
import model.Book;
import services.dao.IBook;
import exceptions.LibrarySystemException;
import model.Address;
import model.Author;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.*;

public class AddBookPanel {
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel topPanel;
    private JPanel mainPanel;
    private JPanel outerMiddle;

    private JTextField bookISBN;
    private JTextField titleField;
    private JTextField maxCheckoutLength;
    private JTextField authLastNameField;
    private JTextField authFirstNameField;
    private JTextField authPhoneNumberField;

    private final IBook iBook = new BookController();

    public void clearData() {
        authPhoneNumberField.setText("");
        authFirstNameField.setText("");
        authLastNameField.setText("");
        maxCheckoutLength.setText("");
        titleField.setText("");
        bookISBN.setText("");
    }

    public AddBookPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineOuterMiddle();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(outerMiddle, BorderLayout.CENTER);
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AddBookLabel = new JLabel("Add Book Title");
        Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
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

        JLabel titleLabel = new JLabel("Book Title");
        JLabel bookISBNLabel = new JLabel("Book ISBN");
        JLabel authLastNameLabel = new JLabel("Author Last Name");
        JLabel authFirstNameLabel = new JLabel("Author First Name");
        JLabel authPhoneNumberLabel = new JLabel("Author Phone Number");
        JLabel maxCheckoutLengthLabel = new JLabel("Max Checkout Length");

        bookISBN = new JTextField(10);
        titleField = new JTextField(10);
        maxCheckoutLength = new JTextField(10);
        authLastNameField = new JTextField(10);
        authFirstNameField = new JTextField(10);
        authPhoneNumberField = new JTextField(10);

        leftPanel.add(authFirstNameLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(authLastNameLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(authPhoneNumberLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(bookISBNLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(maxCheckoutLengthLabel);

        rightPanel.add(authFirstNameField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(authLastNameField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(authPhoneNumberField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(titleField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(bookISBN);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(maxCheckoutLength);

        middlePanel.add(leftPanel);
        middlePanel.add(rightPanel);
        outerMiddle.add(middlePanel, BorderLayout.NORTH);

        //add button at bottom
        JButton addBookButton = new JButton("Add Book");
        attachAddBookButtonListener(addBookButton);
        JPanel addBookButtonPanel = new JPanel();
        addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        addBookButtonPanel.add(addBookButton);
        outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);
    }

    private void attachAddBookButtonListener(JButton btn) {
        btn.addActionListener(evt -> {
            String isbn = bookISBN.getText().trim();
            String title = titleField.getText().trim();
            String lName = authLastNameField.getText().trim();
            String fName = authFirstNameField.getText().trim();
            String phoneNumber = authPhoneNumberField.getText().trim();
            String maxCheckoutLen = maxCheckoutLength.getText().trim();
            if (lName.isBlank() ||fName.isBlank() ||
                    phoneNumber.isBlank() || isbn.isBlank() ||
                            maxCheckoutLen.isBlank()
            ) {
                JOptionPane.showMessageDialog(mainPanel, "All fields are required!");
            } else if (!Util.isNumeric(maxCheckoutLen)) {
                JOptionPane.showMessageDialog(mainPanel, "Checkout length should be an integer!");
            } else {
                Address address = new Address("-", "-", "-", "-");
                Author author = new Author(fName, lName, phoneNumber, address, "-");
                try {
                    Book book = new Book(isbn,title,Integer.parseInt(maxCheckoutLen), List.of(author));
                    iBook.addBook(book);
                    bookISBN.setText("");
                    titleField.setText("");
                    authLastNameField.setText("");
                    authFirstNameField.setText("");
                    authPhoneNumberField.setText("");
                    maxCheckoutLength.setText("");
                    JOptionPane.showMessageDialog(mainPanel, "The book titled " + title + " with " + isbn + " ISBN has been added to the collection!");
                } catch (LibrarySystemException e) {
                    JOptionPane.showMessageDialog(mainPanel, e.getMessage());
                }
            }
        });
    }

    public void updateData() {
    }
}
