package sqltables;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;
public class Store {
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	
	public Store(Statement statement)
	{
		this.statement = statement;
		try {
		resultSet = statement.executeQuery(selectAll);
		metaData = resultSet.getMetaData();
		}catch(SQLException e)
		{
			System.out.println("Something went wrong accessing SQL");
		}
		
	}
	
	public static final String createTable =
			"CREATE TABLE Store ("
			+ "Id int,"
			+ "City varchar(255),"
			+ "Pharmacy int,"
			+ "Zipcode int"
			+ ")";
	
	public static final String selectAll =
			"SELECT * FROM Store";
	
	public static final String dropTable =
			"DROP TABLE Store";
	
	
	/**
	 * Prints the table out as a string (mostly for testing right now)
	 * 
	 * @param resultSet
	 * @throws SQLException
	 */
	public void printTableData() throws SQLException {
		resultSet = statement.executeQuery(selectAll);
		// print header
		int dashCount = 0;
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			System.out.print(metaData.getColumnLabel(i) + " ");
			dashCount += metaData.getColumnLabel(i).length() + 1;
		}
		System.out.println();
		System.out.println("-".repeat(--dashCount));

		// print data
		while (resultSet.next()) {
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				System.out.printf("%-" + (metaData.getColumnLabel(i).toString().length() + 1) + "s",
						resultSet.getObject(i) + " ");
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Returns the amount of columns in the Employee database. 
	 * @return
	 * @author Kevin
	 */ 
	public int getColumnCount()
	{
		try {
		return metaData.getColumnCount();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Java method for executing the dropTable SQL method.
	 * @author Kevin
	 */
	public void dropTable()
	{
		try {
		statement.execute(dropTable);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Java method for executing the createTable SQL method.
	 * @author Kevin
	 */
	public void createTable()
	{
		try {
		statement.execute(createTable);
		resultSet = statement.executeQuery(selectAll);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * method to add a new store to the Stores database.
	 *
	 * @param storeID - the manually entered ID number of the new store
	 * @param city - the name of the city of the new store
	 * @param pharmacy - pharmacy is either a 0 or 1 for determining if the store has a pharmacy (0 means no pharm)
	 * @param zip - the zip code for the new store
	 *
	 *
	 * @author Edwin
	 */
	public void addStore(int storeID, String city, int pharmacy, int zip) {
		String s = String.format("INSERT INTO Store (Id, City, Pharmacy, Zipcode) VALUES "
				+ "(%d,'%s', %d, %d)", storeID, city, pharmacy, zip);
		try {
		statement.execute(s);
		}catch(SQLException e)
		{
			System.out.println("SQLException");
			e.printStackTrace();
		}
	}
	
	
	public String[] getStoreInfo(int idNum) {
		String row = String.format("SELECT * FROM Store WHERE Id=%d", idNum);
		String[] infos = new String[4];
		try {
			resultSet = statement.executeQuery(row);

			if (resultSet.next()) {
				for (int i = 0; i < infos.length; i++) {
					infos[i] = resultSet.getString(i + 1);
				}
			}
			return infos;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Removes a Store from the Store SQL Table
	 * 
	 * @param id
	 * @author James
	 */
	public void removeStore(int storeID) {
		String s = String.format("DELETE FROM Store WHERE Id = %d", storeID);
	try {
		statement.execute(s);
		}catch(SQLException e)
		{
			System.out.println("SQLException");
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates a Store from the Store SQL Table
	 * 
	 * @param id
	 * @author James
	 */
	public void updateStore(int storeID, String city, int pharmacy, int zip) {
		String s = String.format("UPDATE Store SET Id = %d, city = '%s',"
				+ " pharmacy = %d, zip = %d WHERE Id = %d", storeID, city, pharmacy, zip, storeID);
	try {
		statement.execute(s);
		}catch(SQLException e)
		{
			System.out.println("SQLException");
			e.printStackTrace();
		}
	}
	
	/**
	 * Sorts the Store SQL Table depending on the selection made by the button.
	 * 
	 * @param selection
	 * @author James
	 * @return 
	 */
	public String sortStore(String selection) {
		String s = String.format("SELECT * FROM Store "
				+ "ORDER BY %s", selection);
		return s;
	}
	
	/**
	 * Filters the Store SQL Table depending on the given columns name and the contents inside.
	 * 
	 * @param name
	 * @param filter
	 * @author James
	 * @return 
	 */
	public String filterStore(String name, String filter) {
		String n;
		if(name == "Id" || name == "Pharmacy")
			 n = filter;
		else
			n = "'" + filter + "'";
		String s = String.format("SELECT * FROM Store WHERE %s = %s", name, n);
		return s;
	}
	
	/**
	 * Method to get the names of the columns as an array of strings
	 * @return Returns the names of the columns of the table as an array of strings 
	 * @author Kevin Dang
	 */
	public String[] getColumnNames()
	{
		try {
		String[] columnNames = new String[metaData.getColumnCount()];
		for(int i = 1; i <= metaData.getColumnCount(); i++)
		{
			columnNames[i-1] = metaData.getColumnLabel(i);
		}
		return columnNames;
		}catch(SQLException e)
		{
			System.out.println("Something went wrong with SQL");
			e.printStackTrace();
		}
		return null;
	}
	public DefaultTableModel getTableModel(String query) {
		try {
			DefaultTableModel model = new DefaultTableModel();

			ResultSet resultSet = statement.executeQuery(query);

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				model.addColumn(metaData.getColumnName(i));
			}

			while (resultSet.next()) {
				Object[] rowData = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					rowData[i - 1] = resultSet.getObject(i);
				}
				model.addRow(rowData);
			}

			return model;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}