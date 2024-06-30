package com.librarysystempackage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class TransactionPage {

    private Reader reader;
    private Library objLibrary = new Library();
    public static JLabel outcome = new JLabel("");
    private JComboBox<Book> borrowdropdown;
    private JComboBox<Book> returndropdown;
    private JButton returnButton;
    public JButton borrowButton;
    
    private JPanel panel;

    public TransactionPage(Reader reader) {
        this.reader = reader;
        RenderUI();
        LoadData();
        LoadControls();
    }

    public void LoadControls() {
        borrowButton = new JButton("Borrow");
        borrowButton.setBounds(310, 50, 80, 20); // Set bounds for borrowButton
    
        borrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Book selectedBook = (Book) borrowdropdown.getSelectedItem();
                if (selectedBook != null) {
                    
                        if (!selectedBook.ValidateBorrow(reader)) {
                            return;
                        }
                        if (objLibrary.CountReaderBooks(reader,selectedBook) > 1 ) {

                            outcome.setText("You have already borrowed the book. Only one copy is allowed at a time");
                            return;
                        }
                        objLibrary.borrowBook(reader, selectedBook);
                        outcome.setText("You have borrowed the book " + selectedBook.name);
                    
                    LoadData(); // Refresh dropdowns after borrowing
                }
            }
        });
        panel.add(borrowButton); // Add borrowButton to the panel
    
        returnButton = new JButton("Return");
        returnButton.setBounds(310, 100, 80, 20); // Set bounds for returnButton
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Book selectedBook = (Book) returndropdown.getSelectedItem();
                if (selectedBook != null) {
                    
                        objLibrary.returnBook(reader, selectedBook);
                        outcome.setText("You have returned the book " + selectedBook.name);
                    
                    LoadData(); // Refresh dropdowns after returning
                }
            }
        });
        panel.add(returnButton); // Add returnButton to the panel
    }
    
    public void LoadData() {
        objLibrary.LoadReaderBooks(reader);

        borrowdropdown.setModel(new DefaultComboBoxModel<>(objLibrary.AvailableBooks.toArray(new Book[0])));
        returndropdown.setModel(new DefaultComboBoxModel<>(reader.BorrowedBooks.toArray(new Book[0])));
    }

    public void RenderUI() {
        JFrame Transactionframe = new JFrame("Transaction");
        panel = new JPanel();

        Transactionframe.setSize(400, 400);
        Transactionframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Transactionframe.add(panel);

        panel.setLayout(null);

        JLabel WelcomeLabel = new JLabel("Welcome, " + reader.ReaderName + ".");
        WelcomeLabel.setBounds(10, 10, 200, 20);
        panel.add(WelcomeLabel);

        JLabel AvailableBooksLabel = new JLabel("Available Books :");
        AvailableBooksLabel.setBounds(10, 40, 200, 20);
        panel.add(AvailableBooksLabel);

        borrowdropdown = new JComboBox<>();
        borrowdropdown.setBounds(150, 40, 150, 20);
        panel.add(borrowdropdown);

        
        JLabel BorrowedBooksLabel = new JLabel("Borrowed Books :");
        BorrowedBooksLabel.setBounds(10, 100, 200, 20);
        panel.add(BorrowedBooksLabel);

        returndropdown = new JComboBox<>();
        returndropdown.setBounds(150, 100, 150, 20);
        panel.add(returndropdown);

        outcome.setBounds(10, 160, 500, 25);
        panel.add(outcome);

        Transactionframe.setVisible(true);
    }

}
