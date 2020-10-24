import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JTextField;
import java.sql.*;
import java.util.*;
import java.awt.Color;
public class Book03_P092 extends JFrame {
	private JButton getAccountButton, insertAccountButton;
	private JList accountNumberList;
	private Connection connection;
	private JTextField accountIDText,
			usernameText,
			passwordText,
			tsText,
			activeTSText;
	private int accountIDStr;
	public String usernameStr = new String();
	public String passwordStr = new String();
	public String tsStr= new String();
	public String activeTSStr= new String();
	private JTextArea errorText;
	public Book03_P092(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
	}
	private void loadAccounts() {
		Vector v = new Vector();
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT username FROM acc_acc");
			while (rs.next()){
				v.addElement(rs.getString("username"));
			}
			rs.close();

		} catch (SQLException e) {
			displaySQLErrors(e);
		}
		accountNumberList.setListData(v);
	}
	private void buildGUI() {
		Container c = getContentPane();
		c.setLayout (new FlowLayout());

		Vector v = new Vector();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT acc_id FROM acc_acc");
			while (rs.next()) {
				v.addElement(rs.getString("acc_id"));
			}
			rs.close();
		} catch (SQLException e){
		}
		accountNumberList = new JList(v);
		accountNumberList.setVisibleRowCount(2);
		JScrollPane accountNumberListScrollPane = new JScrollPane(accountNumberList);
		getAccountButton = new JButton ("Get Account_xx");
		getAccountButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try {
						Statement statement = connection.createStatement();
						ResultSet rs = statement.executeQuery ("SELECT *FROM acc_acc WHERE acc_id = "+ accountNumberList.getSelectedValue());
						if (rs.next()){
							accountIDText.setText(rs.getString("acc_id"));
							usernameText.setText(rs.getString("username"));
							passwordText.setText(rs.getString("password"));
							tsText.setText(rs.getString("ts"));
							activeTSText.setText(rs.getString("act_ts"));
						}
					} catch (SQLException selectException){
						displaySQLErrors(selectException);
					}
				}
			}
		);
		insertAccountButton = new JButton ("Insert Account");
		insertAccountButton.addActionListener (

			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Statement statement = connection.createStatement();
						accountIDStr = Integer.parseInt(accountIDText.getText());
						usernameStr = usernameText.getText();
						passwordStr = passwordText.getText();
						int i = statement.executeUpdate("INSERT INTO acc_acc (acc_id, username, password, ts, act_ts) VALUES ("+accountIDStr+", '" + usernameStr +"', '" + passwordStr  + "', now() ,  now() )");
						errorText.append("Inserted " + i + " rows successufully");
						accountNumberList.removeAll();
					} catch (SQLException insertException){
						displaySQLErrors(insertException);
					}
				}
			}
		);
		JPanel first = new JPanel();
		first.add(accountNumberListScrollPane);
		first.add(getAccountButton);
                first.add(insertAccountButton);
		accountIDText = new JTextField(15);
		usernameText = new JTextField(15);
		passwordText = new JTextField(15);
		tsText = new JTextField(15);
		activeTSText = new JTextField(15);
		errorText = new JTextArea(5, 15);
		errorText.setEditable(false);
		JPanel second = new JPanel();
		second.setLayout(new GridLayout(5,1));
		second.add(accountIDText);
		second.add(usernameText);
		second.add(passwordText);
		second.add(tsText);
		second.add(activeTSText);
		JPanel third = new JPanel();
		third.add(new JScrollPane(errorText));
		c.add(first);
		c.add(second);
		c.add(third);
		c.setBackground(new Color(106, 104, 106));
		setSize(400,400);
		show();
	}
	public void connectToDB(){
		try{
			String dbUrl="jdbc:mysql://localhost:3306/accounts?serverTimezone=UTC";
			String username="root";
			String password="";
			connection =DriverManager.getConnection(dbUrl, username, password);
		} catch (SQLException e){
			System.exit(1);
		}
	}
	private void displaySQLErrors(SQLException e){
		errorText.append("SQLException " + e.getMessage() + "\n");
		errorText.append("SQLState" + e.getSQLState() + "\n");
		errorText.append("VendorError" + e.getErrorCode() + "\n");
	}
	private void init(){
		connectToDB();
	}
	public static void main (String[] args){
		Book03_P092 book03_P092 = new Book03_P092();
		book03_P092.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			}
		);
		book03_P092.init();
		book03_P092.buildGUI();
	}
}


