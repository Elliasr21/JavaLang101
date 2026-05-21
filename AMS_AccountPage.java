package AMS_Interface;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class AMS_AccountPage extends JFrame {

	JLabel lblTitle, lblManageAccount, lblFullName, lblUsername, lblPass;
	JTextField txtFullName, txtUser;
	JPasswordField txtPass;
	JTable table;
	DefaultTableModel model;
	JScrollPane scrollTable;
	JButton btnAdd, btnLoad, btnUpd, btnDel, btnLogout;
	
	public static void main(String[] args) {
		new AMS_AccountPage();
	}
	
	public AMS_AccountPage() {
		lblTitle = new JLabel("ACCOUNT MANAGEMENT SYSTEM", SwingConstants.CENTER);
		lblTitle.setBounds(0,20,680,30);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 20));
		add(lblTitle);
		
		lblManageAccount = new JLabel("Manage Account");
		lblManageAccount.setBounds(20,50,150,30);
		lblManageAccount.setFont(new Font("serif", Font.BOLD, 16));
		add(lblManageAccount);
		
		String [] columns = {"Full Name", "Username", "Password"};
		model = new DefaultTableModel(columns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		scrollTable = new JScrollPane(table);
		scrollTable.setBounds(20,80,645,300);
		scrollTable.setFont(new Font("Serif", Font.PLAIN, 12));
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row!=-1) {
					txtFullName.setText(model.getValueAt(row, 0).toString());
					txtUser.setText(model.getValueAt(row, 1).toString());
					txtPass.setText(model.getValueAt(row, 2).toString());
				}
			}
		});
		add(scrollTable);
		
		lblFullName = new JLabel("Full Name: ");
		lblFullName.setBounds(30,400,90,30);
		lblFullName.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblFullName);
		
		lblPass = new JLabel("Password: ");
		lblPass.setBounds(30,490,90,30);
		lblPass.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblPass);
		
		lblUsername = new JLabel("Username: ");
		lblUsername.setBounds(30, 445, 90, 30);
		lblUsername.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblUsername);
		
		txtUser = new JTextField();
		txtUser.setBounds(120,445,250,30);
		txtUser.setFont(new Font("Serif", Font.PLAIN, 16));
		add(txtUser);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(120,490,250,30);
		txtPass.setFont(new Font("Serif", Font.PLAIN, 16));
		add(txtPass);
		
		txtFullName = new JTextField();
		txtFullName.setBounds(120, 400, 250, 30);
		txtFullName.setFont(new Font("Serif",Font.PLAIN, 16));
		add(txtFullName);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(500,400,100,20);
		btnAdd.setFont(new Font("Serif",Font.PLAIN, 16));
		btnAdd.addActionListener(e->add());
		add(btnAdd);
		
		btnLoad = new JButton("Load");
		btnLoad.setBounds(500, 425,100, 20);
		btnLoad.setFont(new Font("Serif", Font.PLAIN, 16));
		btnLoad.addActionListener(e->loadTable());
		add(btnLoad);
		
		btnUpd = new JButton("Update");
		btnUpd.setBounds(500, 450, 100, 20);
		btnUpd.setFont(new Font("Serif", Font.PLAIN, 16));
		btnUpd.addActionListener(e->update());
		add(btnUpd);
		
		btnDel = new JButton("Delete");
		btnDel.setBounds(500, 475, 100, 20);
		btnDel.setFont(new Font("Serif",Font.PLAIN, 16));
		btnDel.addActionListener(e->del());
		add(btnDel);
		
		btnLogout = new JButton("Log Out");
		btnLogout.setBounds(500, 500, 100, 20);
		btnLogout.setFont(new Font("Serif", Font.PLAIN, 16));
		btnLogout.addActionListener(e->logout());
		add(btnLogout);
		
		loadTable();
		
		setLayout(null);
		setTitle("ACCOUNT MANAGEMENT SYSTEM");
		setSize(700,570);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	void add() {
		if (txtFullName.getText().trim().isEmpty()&& txtUser.getText().trim().isEmpty()&&txtPass.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "No data is filled up, no account added", "Add Unsuccessful", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if (txtFullName.getText().trim().isEmpty()||txtUser.getText().trim().isEmpty()||txtPass.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill-up the missing requirements.", "Full Name/Username/Password", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			
			FileReader fr = new FileReader("Users.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			while ((line = br.readLine()) != null){
				String[] parts = line.split(",");
				if (parts.length == 3) {
					String dataUser = parts[0].trim();
					String dataFullName = parts[2].trim();
					
					if (txtUser.getText().trim().equals(dataUser)) {
						JOptionPane.showMessageDialog(this, "This user is already taken.");
						br.close();
						return;
					}
					
					if (txtFullName.getText().trim().equals(dataFullName)) {
						JOptionPane.showMessageDialog(this, "This full name is already registered.");
						br.close();
						return;
					}
				}
			}
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error Occured: " + e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
		int confirm = JOptionPane.showConfirmDialog(this, "Are you aure you want to add this account?", "Confirm Add", JOptionPane.YES_NO_OPTION);
		if  (confirm!=JOptionPane.YES_OPTION) {
			return;
		}
		
		try {
			FileWriter fw = new FileWriter("Users.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			String data = txtUser.getText().trim() + "," + new String(txtPass.getPassword()).trim()+","+txtFullName.getText().trim();
			bw.write(data);
			bw.newLine();
			
			bw.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error Occurred: " + e.getMessage(), "Add Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JOptionPane.showMessageDialog(this, "Account successfully added!");
		clear();
	}
	
	void loadTable() {
		model.setRowCount(0);
		
		try {
			FileReader fr = new FileReader("Users.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			while ((line = br.readLine()) != null) {
				String[]row = line.split(",");
				
				String dataUser = row[0].trim();
				String dataPassword = row[1].trim();
				String dataFullName = row[2].trim();
				
				row[0] = dataFullName;
				row[1] = dataUser;
				row[2] = dataPassword;
				
				model.addRow(row);
			}
			
			clear();
			br.close();
			
		} catch(IOException e) {
			JOptionPane.showMessageDialog(this, "Error Occured: " + e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	void update() {
		int selectedRow = table.getSelectedRow();
		
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select an account to update", "No Selection", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if (txtFullName.getText().trim().isEmpty() || txtUser.getText().trim().isEmpty()||txtPass.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill-up the missing requirements.", "Full Name/Username/Password", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			FileReader fr = new FileReader("Users.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			int rowIndex = 0;
			while((line = br.readLine())!=null) {
				String [] parts = line.split(",");
				if (parts.length ==3) {
					String dataUser = parts[0].trim();
					String dataPass = parts[1].trim();
					String dataFullName= parts[2].trim();
					
					if (rowIndex == selectedRow) {
						if (txtUser.getText().trim().equals(dataUser) && new String (txtPass.getPassword()).trim().equals(dataPass) && txtFullName.getText().trim().equals(dataFullName)) {
							JOptionPane.showMessageDialog(this, "This account has no changes.");
							br.close();
							return;
						}
					} else {
						if(txtFullName.getText().trim().equals(dataFullName)) {
							JOptionPane.showMessageDialog(this, "This full name is already registered.");
							br.close();
							return;
						}
						if (txtUser.getText().trim().equals(dataUser)) {
							JOptionPane.showMessageDialog(this, "This username is already taken.");
							br.close();
							return;
						}
					}
				}
				rowIndex++;
			}
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "An Error Occured: " +e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this account?", "Confirm Update", JOptionPane.YES_NO_OPTION);
		if (confirm !=JOptionPane.YES_OPTION) {
			return;
		}
		
		ArrayList<String> lines = new ArrayList<>();
		try {
			FileReader fr = new FileReader("Users.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			int rowIndex=0;
			while((line = br.readLine()) !=null) {
				if (rowIndex == selectedRow) {
					String updData = txtUser.getText().trim() + "," + new String(txtPass.getPassword()).trim()+","+txtFullName.getText().trim();
					lines.add(updData);
				} else {
					lines.add(line);
				}
				
				rowIndex++;
			}
			
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "An Error Occured: " + e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			FileWriter fw = new FileWriter ("Users.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (String l : lines) {
				bw.write(l);
				bw.newLine();
			}
			bw.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,  "An Error Occured: " + e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JOptionPane.showMessageDialog(this, "Account successfully updated!");
		clear();
		table.clearSelection();		
	}
	
	void del() {
		int selectedrow = table.getSelectedRow();
		
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(this, "Please select an account to delete.", "No Selecteion", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this account?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}
		
		ArrayList<String> lines = new ArrayList<>();
		try {
			FileReader fr = new FileReader("Users.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			int rowIndex = 0;
			
			while ((line = br.readLine()) != null) {
				if (rowIndex != selectedrow) {
					lines.add(line);
				}
				
				rowIndex++;
			}
			br.close();
			
		}catch(IOException e) {
			JOptionPane.showMessageDialog(this, "An Error Occurred: " + e.getMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			FileWriter fw =new FileWriter("Users.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (String l : lines) {
				bw.write(l);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "An Error Occurred: " + e.getMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JOptionPane.showMessageDialog(this,  "Account successfully deleted!");
		clear();
		table.clearSelection();
	}
	
	void logout (){
		int confirm = JOptionPane.showConfirmDialog(this,  "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}
		System.exit(0);
	}
	
	void clear() {
		txtUser.setText("");
		txtPass.setText("");
		txtFullName.setText("");
	}

}
