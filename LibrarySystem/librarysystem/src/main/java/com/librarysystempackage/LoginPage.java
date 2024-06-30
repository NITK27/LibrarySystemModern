package com.librarysystempackage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.*;



public class LoginPage implements ActionListener {

    public Library objLibrary = new Library();

    JTextField userText;
    JTextField userPassword;
    JLabel success = new JLabel("");
    JFrame frame = new JFrame();

    public LoginPage(){
        this.ShowLogin();
    }

    public void ShowLogin(){
        JFrame frame = new JFrame("Login");

        JPanel panel = new JPanel();
        
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(panel);   

        panel.setLayout(null);

        // username
        JLabel userLabel = new JLabel("User : ");
        userLabel.setBounds(10, 20, 80, 25); 
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        //password
        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(10, 50, 80, 25); 
        panel.add(passwordLabel);

        userPassword = new JTextField(20);
        userPassword.setBounds(100,50,165,25);
        panel.add(userPassword);

        //Login
        JButton button = new JButton("Login");
        button.setBounds(10,80,80,25);
        button.addActionListener(this);
        panel.add(button);

        
        success.setBounds(10, 110, 300, 25); 
        panel.add(success);

        frame.setVisible(true);

        //return;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        Reader reader = null;
        int readerId = Integer.parseInt(userText.getText());
        String password = userPassword.getText();

        reader = objLibrary.Login(readerId , password);

        if (reader != null) {
            // Login successful
            success.setText("Login Successful.");

            TransactionPage transactionPage = new TransactionPage(reader);
            
            
            //frame.setVisible(false); // Hide the login page
            
        } else {
            // Login failed
            success.setText("Wrong Password, try again.");
            userPassword.setText("");
       
    }
}

}
