package AMS_Interface;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class AMS_Registration extends JFrame{

	JLabel lblTitle, lblReg, lblUsername, lblPass, lblFullName;
	JTextField txtUsername, txtFullName;
	JPasswordField txtPass;
	JButton btnReg, btnClear, btnBack;
	
	public static void main(String[] args) {
		new AMS_Registration();

	}
	
	AMS_Registration(){
		lblTitle = new JLabel("ACCOUNT MANAGEMENT SYSTEM", SwingConstants.CENTER);
		lblTitle.setBounds(0,20,480,30);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 20));
		add(lblTitle);
		
		lblReg = new JLabel("REGISTRATION", SwingConstants.CENTER);
		lblReg.setBounds(0,50,480,30);
		lblReg.setFont(new Font("Serif", Font.BOLD, 20));
		add(lblReg);
		
		lblFullName = new JLabel("Full Name: ");
		lblFullName.setBounds(30,110,90,30);
		lblFullName.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblFullName);
		
		lblUsername = new JLabel("Username: ");
		lblUsername.setBounds(30,150,90,30);
		lblUsername.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblUsername);
		
		lblPass = new JLabel("Password: ");
		lblPass.setBounds(30,190,90,30);
		lblPass.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblPass);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(120,150, 250, 30);
		txtUsername.setFont(new Font("Serif", Font.PLAIN, 16));
		add(txtUsername);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(120,190,250,30);
		txtPass.setFont(new Font("Serif", Font.PLAIN, 16));
		add(txtPass);
		
		txtFullName = new JTextField();
		txtFullName.setBounds(120, 110, 250,30);
		txtFullName.setFont(new Font("Serif",Font.PLAIN, 16));
		add(txtFullName);
		
		btnBack = new JButton("Back");
		btnBack.setBounds(100,250,100,30);
		btnBack.setFont(new Font("Serif", Font.PLAIN, 16));
		btnBack.addActionListener(e->back());
		add(btnBack);
		
		
		btnClear = new JButton("Clear");
		btnClear.setBounds(210, 250, 100, 30);
		btnClear.setFont(new Font("Serif", Font.PLAIN, 16));
		btnClear.addActionListener(e->clear());
		add(btnClear);
		
		
		btnReg = new JButton("Register");
		btnReg.setBounds(320,250,100,30);
		btnReg.setFont(new Font("Serif", Font.PLAIN, 16));
		btnReg.addActionListener(e->reg());
		add(btnReg);
		
		setLayout(null);
		setTitle("AMS Registration");
		setSize(500,350);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	void back() {
		int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to return to log in?", "Return to Log In", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			new AMS_Login();
			dispose();
		}
	}
	void reg() {
		if (txtFullName.getText().trim().isEmpty()||txtUsername.getText().trim().isEmpty() || txtPass.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill-up the requirements.", "Full Name/Username/Password", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to register this account?", "Confirm Registration", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}
		
		try {
			FileWriter fw = new FileWriter("Users.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			String data = txtUsername.getText().trim()+","
					+ new String(txtPass.getPassword()).trim()+","
					+ txtFullName.getText().trim();
			
			bw.write(data);
			bw.newLine();
			
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error Occurred: " + e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JOptionPane.showMessageDialog(this, "Registration successful!");
		clear();
	}
	void clear() {
		txtFullName.setText("");
		txtUsername.setText("");
		txtPass.setText("");
	}

}
