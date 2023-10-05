package sqltables;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

public class AppFrame extends JFrame {

	private JPanel cards;
	private JPanel mainPanel;
	private JPanel editPanel;
	private JFrame frame;
	private CardLayout cardLayout;

	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";
	private Employee employee;
	
	private static Connection connection; // = null;
	private static Statement statement; // = null;

	private String query = Employee.selectAll;
	private JTable table;

	private JPanel nextButtonPanel;
	private JButton nextBtn;

	private JPanel removePanel;
	private JPanel addPanel;
	private JPanel updatePanel;
	private JLabel addLbl;
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
	private JTextField storeIdTxtField;
	private JButton addEmployeeBtn;
	private JLabel removeIDLbl;
	private JTextField removeIDTxtField;
	private JButton removeBtn;
	private JButton prevBtn;
	private JPanel tablePanel;
	private Store store;
	private JTextField txtEmployeeid;
	private JButton btnUpdate;
	private JPanel sortPanel;
	private JComboBox<String> sortOptions;
	private JButton sortBtn;
	private JPanel filterPanel;
	private JComboBox<String> filterOptions;
	private JTextField filterTxtField;
	private JButton filterBtn;

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

		 connection = null;
		statement = null;

		try {
			connection = DriverManager.getConnection(databaseURL);
			statement = connection.createStatement();
			employee = new Employee(statement);
			employee.printTableData();
			// store = new Store(statement);
			// store.printTableData();
		}

		catch (SQLException e) {
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
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int removeID = Integer.parseInt(removeIDTxtField.getText());

				employee.removeEmployee(removeID);

				try {
					employee.printTableData();
					System.out.println();
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});

		removePanel.add(removeBtn);

		prevBtn = new JButton("GO BACK");
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.previous(cards);
				updateTablePanel();
			}
		});
		removePanel.add(prevBtn);

	}

	private void createUpdatePanel() {
		updatePanel = new JPanel();
		editPanel.add(updatePanel);
		
		txtEmployeeid = new JTextField();
		updatePanel.add(txtEmployeeid);
		txtEmployeeid.setColumns(10);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				int employeeID = Integer.parseInt(txtEmployeeid.getText());
				String []infos = employee.getEmployeeInfo(employeeID);
				new UpdateFrame(infos);
				
			}
		});
		updatePanel.add(btnUpdate);

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

		storeIdTxtField = new JTextField();
		addPanel.add(storeIdTxtField);
		storeIdTxtField.setColumns(10);

		addEmployeeBtn = new JButton("Add");
		addPanel.add(addEmployeeBtn);
		addEmployeeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					employee.addEmployee(firstNameTxtField.getText().toString(), lastNameTxtField.getText().toString(),
							jobTitleTxtField.getText().toString(), dobTxtField.getText().toString(), 
							Integer.parseInt(storeIdTxtField.getText()));
				
				try {
					employee.printTableData();
					System.out.println();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});

	}

	private void createMainPanel() {
		mainPanel = new JPanel();
		cards.add(mainPanel, "1");

		createNextPanel();

		createFilterPanel();
		createNextBtn();
		createSortPanel();

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

		updateTablePanel();

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
	private void createSortPanel() {
		sortPanel = new JPanel();
		nextButtonPanel.add(sortPanel);
		createSortBtns();
	}
	private void createSortBtns(){
		sortOptions = new JComboBox<String>();
		sortPanel.add(sortOptions);
		sortOptions.addItem("FirstName");
		sortOptions.addItem("LastName");
		sortOptions.addItem("JobTitle");
		sortOptions.addItem("DOB");
		sortOptions.addItem("Id");
		sortOptions.addItem("StoreID");
		sortBtn = new JButton("Sort");
		sortBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet resultset = employee.sortEmployee(sortOptions.getSelectedItem().toString());
				
				employee.printTableData(resultset);
				updateTablePanel();
				System.out.println();
			}
		});
		sortPanel.add(sortBtn);
	}
	private void createFilterPanel() {
		filterPanel = new JPanel();
		nextButtonPanel.add(filterPanel);
		createFilterBtns();
	}
	private void createFilterBtns(){
		filterOptions = new JComboBox<String>();
		filterOptions.addItem("FirstName");
		filterOptions.addItem("LastName");
		filterOptions.addItem("JobTitle");
		filterOptions.addItem("DOB");
		filterOptions.addItem("Id");
		filterOptions.addItem("StoreID");
		filterPanel.add(filterOptions);
		
		filterTxtField = new JTextField();
		filterPanel.add(filterTxtField);
		filterTxtField.setColumns(10);
		
		filterBtn = new JButton("Filter");
		filterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet resultset = employee.filterEmployee(filterOptions.getSelectedItem().toString(), filterTxtField.getText().toString());
				
				employee.printTableData(resultset);
				updateTablePanel();
				System.out.println();
			}
		});
		filterPanel.add(filterBtn);
	}
	
	private void updateTablePanel() {
		tablePanel.removeAll();
		tablePanel.add(new JScrollPane(new JTable(employee.getTableModel(query))));
		tablePanel.revalidate();
		tablePanel.repaint();
		tablePanel.setVisible(true);
	}

	public class UpdateFrame extends JFrame {

		
		private JPanel contentPane;
		private JTextField txtFname;
		private JTextField txtLname;
		private JTextField txtPosition;
		private JTextField txtDob;
		private JTextField txtStoreid;

		/**
		 * Launch the application.
		 */


		/**
		 * Create the frame.
		 */
		public UpdateFrame(String[] infos) {
			setTitle("Update Employee Information");
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			setContentPane(contentPane);
			
//			for(String s : infos) {
//				System.out.println(s);
//			}
			
			int id = Integer.parseInt(infos[0]);
			
			JPanel contentPnl = new JPanel();
			contentPane.add(contentPnl);
			contentPnl.setLayout(new GridLayout(0, 2, 0, 0));
			
			JLabel lblFirstName = new JLabel("First Name: ");
			contentPane.add(lblFirstName);
			
			txtFname = new JTextField();
			txtFname.setText(infos[1]);
			contentPane.add(txtFname);
			txtFname.setColumns(10);
			
			JLabel lblLastName = new JLabel("Last Name: ");
			contentPane.add(lblLastName);
			
			txtLname = new JTextField();
			txtLname.setText(infos[2]);
			contentPane.add(txtLname);
			txtLname.setColumns(10);
			
			JLabel lblPosition = new JLabel("Position: ");
			contentPane.add(lblPosition);
			
			txtPosition = new JTextField();
			txtPosition.setText(infos[3]);
			contentPane.add(txtPosition);
			txtPosition.setColumns(10);
			
			JLabel lblDateOfBirth = new JLabel("Date Of Birth:");
			contentPane.add(lblDateOfBirth);
			
			txtDob = new JTextField();
			txtDob.setText(infos[4]);
			contentPane.add(txtDob);
			txtDob.setColumns(10);
			
			JLabel lblStoreId = new JLabel("Store ID");
			contentPane.add(lblStoreId);
			
			txtStoreid = new JTextField();
			txtStoreid.setText(infos[5]);
			contentPane.add(txtStoreid);
			txtStoreid.setColumns(10);
			
			
			
		
			
			JButton updateBtn = new JButton("UPDATE");
			updateBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
//				
					
					employee.updateEmployee(txtFname.getText(), txtLname.getText(),
							txtPosition.getText(), txtDob.getText(), Integer.parseInt(txtStoreid.getText()), id);
					
					dispose();
				}
			});
			contentPane.add(updateBtn);
			
			setVisible(true);
		}
		
	}
	
}
