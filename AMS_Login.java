package AMS_Interface;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;


public class AMS_Login extends  JFrame {
	
	JLabel lblTitle, lblLogin, lblUsername, lblPass;
	JTextField txtUsername;
	JPasswordField txtPass;
	JButton btnLogin, btnReg, btnExt;

	public static void main(String[] args) {
		new AMS_Login();
		
	}
	
	AMS_Login(){
		lblTitle = new JLabel ("ACCOUNT MANAGEMENT SYSTEM", SwingConstants.CENTER);
		lblTitle.setBounds(0,20,480,30);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 20));
		add(lblTitle);
		
		lblLogin = new JLabel ("LOG IN", SwingConstants.CENTER);
		lblLogin.setBounds(0,50,480,30);
		lblLogin.setFont(new Font("Serif", Font.BOLD, 16));
		
		lblUsername = new JLabel("Username: ");
		lblUsername.setBounds(20, 110, 90, 30);
		lblUsername.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblUsername);
		
		lblPass = new JLabel("Password:");
		lblPass.setBounds(30,150,90,30);
		lblPass.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblPass);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(120,110,250,30);
		txtUsername.setFont(new Font("Serif", Font.PLAIN, 16));
		add(txtUsername);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(120,150,250,30);
		txtPass.setFont(new Font("Serif", Font.PLAIN, 16));
		add(txtPass);
		
		btnLogin = new JButton("Log In");
		btnLogin.setBounds(370,220,100,30);
		btnLogin.setFont(new Font("Serif", Font.PLAIN, 16));
		add(btnLogin);
		btnLogin.addActionListener(e-> logIn());
		
		btnReg = new JButton("Register");
		btnReg.setBounds(260,220,100,30);
		btnReg.setFont(new Font("Serif", Font.PLAIN, 16));
		add(btnReg);
		btnReg.addActionListener(e->register());
		
		setLayout(null);
		setTitle("AMS Log In");
		setSize(500,300);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	void logIn() {
		String inputUser = txtUsername.getText().trim();
		String inputPassword = new String(txtPass.getPassword()).trim();
		
		if (inputUser.isEmpty()||inputPassword.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill-up username and password, or register new account.", "Username/Password/Register", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			FileReader fr = new FileReader("Users.txt");
			BufferedReader br = new BufferedReader(fr);
			
			boolean found = false;
			String line;
			while((line = br.readLine()) != null){
				String[]parts = line.split(",");
				if (parts.length == 3) {
					String dataUser = parts[0].trim();
					String dataPass = parts[1].trim();
					String dataFullName = parts[2].trim();
					
					if (inputUser.equals(dataUser) && inputPassword.equals(dataPass)) {
						JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + dataFullName + "!");
						new AMS_AccountPage();
						dispose();
						found = true;
						break;
					}
				}
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,  "Error Occurred: " + e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
	}
		
	}
	
	void exit() {
		if (!txtUsername.getText().trim().isEmpty()||!txtPass.getText().trim().isEmpty()) {
			int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit log in?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}
	
	void register() {
		if (!txtUsername.getText().trim().isEmpty()||!txtPass.getText().trim().isEmpty()) {
			int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to discard inputs?", "Confirm Registration", JOptionPane.YES_NO_OPTION);
			if(choice == JOptionPane.YES_OPTION) {
				new AMS_Registration();
				dispose();
			} else {
				new AMS_Registration();
				dispose();
			}
		}
	}
	
	void clear() {
		txtUsername.setText("");
		txtPass.setText("");
	}
}
