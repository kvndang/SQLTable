package sqltables;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AppFrame extends JFrame {


	private JPanel cards;
	private JPanel mainPanel;
	private JPanel editPanel;
	private JFrame frame;
	private CardLayout cardLayout;
	
	
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";
	private Employee employee;

	private String query = Employee.selectAll; 
	private JTable table;
	
	private JPanel nextButtonPanel;
	private JButton nextBtn;
	
	private JPanel removePanel;
	private JPanel addPanel;
	private JPanel updatePanel;
	private JLabel addLbl;
	private JLabel updateLbl;
	private JLabel removeLbl;
	private JLabel firstNameLbl;
	private JTextField firstNameTxtField;
	private JLabel lastNameLbl;
	private JTextField lastNameTxtField;
	private JLabel jobTitleLbl;
	private JTextField jobTitleTxtField;
	private JLabel dobLbl;
	private JTextField dobTxtField;
	private JLabel storeIdLbl;
	private JTextField textField;
	private JButton addEmployeeBtn;
	private JLabel findLbl;
	private JTextField textField_1;
	private JButton findBtn;
	private JLabel updateFirstNameLbl;
	private JTextField updateFirstNameTxtField;
	private JLabel updateLastNameLbl;
	private JTextField updateLastNameTxtField;
	private JLabel updateJobTitleLbl;
	private JTextField updateJobTitleTxtField;
	private JLabel updateDOBLbl;
	private JTextField updateDOBTxtField;
	private JLabel updateStoreIDLbl;
	private JTextField updateStoreIDTxtField;
	private JButton updateBtn;
	private JLabel removeIDLbl;
	private JTextField removeIDTxtField;
	private JButton removeBtn;
	private JButton prevBtn;
	private JPanel tablePanel;
	private Store store;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppFrame window = new AppFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppFrame() {
		
		Connection connection = null;
		Statement statement = null;
		
		try {
				connection = DriverManager.getConnection(databaseURL);
				statement = connection.createStatement();
				employee = new Employee(statement);
				store = new Store(statement);
				store.addStore(321, "West Valley", 0, 94118);
				store.printTableData();
			}
					
			catch(SQLException e)
			{
				System.out.println("Something went wrong accessing SQL.");
				e.printStackTrace();
			}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 1200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);
		frame.getContentPane().add(cards, "cards");
		
		DefaultTableModel model = employee.getTableModel(query);
		table = new JTable(model);
		
		createMainPanel();
		
		createEditPanel();
		
		
	}

	

	private void createEditPanel() {
		editPanel = new JPanel();
		cards.add(editPanel, "editPanel");
		editPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
			createAddPanel();
			createUpdatePanel();	
			createRemovePanel();
		
	}

	private void createRemovePanel() {
		removePanel = new JPanel();
		editPanel.add(removePanel);
		
			removeLbl = new JLabel("Remove Employee:");
			removePanel.add(removeLbl);
		
			removeIDLbl = new JLabel("Enter Id to remove");
			removePanel.add(removeIDLbl);
		
			removeIDTxtField = new JTextField();
			removePanel.add(removeIDTxtField);
			removeIDTxtField.setColumns(10);
		
			removeBtn = new JButton("REMOVE");
			removePanel.add(removeBtn);
		
			prevBtn = new JButton("GO BACK");
			prevBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.previous(cards);
				}
			});
			removePanel.add(prevBtn);
		
	}

	private void createUpdatePanel() {
		updatePanel = new JPanel();
		editPanel.add(updatePanel);
		
			updateLbl = new JLabel("Update Employee:");
			updatePanel.add(updateLbl);
		
			findLbl = new JLabel("Enter Id to Update");
			updatePanel.add(findLbl);
		
			textField_1 = new JTextField();
			updatePanel.add(textField_1);
			textField_1.setColumns(10);
		
			findBtn = new JButton("Find");
			updatePanel.add(findBtn);
		
			updateFirstNameLbl = new JLabel("First Name");
			updatePanel.add(updateFirstNameLbl);
		
			updateFirstNameTxtField = new JTextField();
			updatePanel.add(updateFirstNameTxtField);
			updateFirstNameTxtField.setColumns(10);
		
			updateLastNameLbl = new JLabel("Last Name");
			updatePanel.add(updateLastNameLbl);
		
			updateLastNameTxtField = new JTextField();
			updatePanel.add(updateLastNameTxtField);
			updateLastNameTxtField.setColumns(10);
		
			updateJobTitleLbl = new JLabel("Job Title");
			updatePanel.add(updateJobTitleLbl);
		
			updateJobTitleTxtField = new JTextField();
			updatePanel.add(updateJobTitleTxtField);
			updateJobTitleTxtField.setColumns(10);
		
			updateDOBLbl = new JLabel("Date Of Birth");
			updatePanel.add(updateDOBLbl);
		
			updateDOBTxtField = new JTextField();
			updatePanel.add(updateDOBTxtField);
			updateDOBTxtField.setColumns(10);
		
			updateStoreIDLbl = new JLabel("Store ID");
			updatePanel.add(updateStoreIDLbl);
	
			updateStoreIDTxtField = new JTextField();
			updatePanel.add(updateStoreIDTxtField);
			updateStoreIDTxtField.setColumns(10);
		
			updateBtn = new JButton("UPDATE");
			updatePanel.add(updateBtn);
		
	}

	private void createAddPanel() {
		addPanel = new JPanel();
		editPanel.add(addPanel);
		
			addLbl = new JLabel("Add Employee:");
			addPanel.add(addLbl);
		
		
			firstNameLbl = new JLabel("First Name");
			addPanel.add(firstNameLbl);
		
			firstNameTxtField = new JTextField();
			addPanel.add(firstNameTxtField);
			firstNameTxtField.setColumns(10);
		
			lastNameLbl = new JLabel("Last Name");
			addPanel.add(lastNameLbl);
		
			lastNameTxtField = new JTextField();
			addPanel.add(lastNameTxtField);
			lastNameTxtField.setColumns(10);
		
			jobTitleLbl = new JLabel("Job Title");
			addPanel.add(jobTitleLbl);
		
			jobTitleTxtField = new JTextField();
			addPanel.add(jobTitleTxtField);
			jobTitleTxtField.setColumns(10);
		
			dobLbl = new JLabel("Date Of Birth");
			addPanel.add(dobLbl);
		
			dobTxtField = new JTextField();
			addPanel.add(dobTxtField);
			dobTxtField.setColumns(10);
		
			storeIdLbl = new JLabel("StoreID");
			addPanel.add(storeIdLbl);
		
			textField = new JTextField();
			addPanel.add(textField);
			textField.setColumns(10);
		
			addEmployeeBtn = new JButton("Add");
			addEmployeeBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			addPanel.add(addEmployeeBtn);
		
	}

	private void createMainPanel() {
		mainPanel = new JPanel();
		cards.add(mainPanel, "1");
		
			createNextPanel();
			
			createNextBtn();
			
			table = new JTable();
		
	}

	private void createNextPanel() {
		mainPanel.setLayout(new BorderLayout(0, 0));
		nextButtonPanel = new JPanel();
		mainPanel.add(nextButtonPanel);
		{
			createTablePanel();
		}
	}

	private void createTablePanel() {
		tablePanel = new JPanel();
		mainPanel.add(tablePanel, BorderLayout.NORTH);
		
			createEmployeeTable();
		
	}

	private void createEmployeeTable() {
	
		tablePanel.add(new JScrollPane(table));
;
	
	}

	private void createNextBtn() {
		nextBtn = new JButton("Modify");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.next(cards);
			}
		});
		nextButtonPanel.add(nextBtn);
	}

	

	

}
