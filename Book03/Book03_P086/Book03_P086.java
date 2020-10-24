import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.awt.Color;
public class Book03_P086 extends JFrame {
	private JButton getAccountButton;
	private JList accountNumberList;
	private Connection connection;
	private JTextField accountIDText,
			usernameText,
			passwordText,
			tsText,
			activeTSText;
	public Book03_P086(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
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
		getAccountButton = new JButton ("Get Account");
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
					} catch (SQLException ee){
					}
				}
			}
		);
		JPanel first = new JPanel();
		first.add(accountNumberListScrollPane);
		first.add(getAccountButton);
		accountIDText = new JTextField(15);
		usernameText = new JTextField(15);
		passwordText = new JTextField(15);
		tsText = new JTextField(15);
		activeTSText = new JTextField(15);
		JPanel second = new JPanel();
		second.setLayout(new GridLayout(5,1));
		second.add(accountIDText);
		second.add(usernameText);
		second.add(passwordText);
		second.add(tsText);
		second.add(activeTSText);
		c.add(first);
		c.add(second);
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
	private void init(){
		connectToDB();
	}
	public static void main (String[] args){
		Book03_P086 book03_P086 = new Book03_P086();
		book03_P086.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			}
		);
		book03_P086.init();
		book03_P086.buildGUI();
	}
}


