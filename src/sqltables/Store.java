package sqltables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Store {
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";
	
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
	
	public static final String insertData =
			"INSERT INTO Store (Id, City, Pharmacy, Zipcode) VALUES "
			+ "(1000,'Taylorsville', 0, 84045),"
			+ "(1001,'Detroit', 1, 48127),"
			+ "(1002,'Arlington', 0, 76001)";
	
	public static final String selectAll =
			"SELECT * FROM Store";
	
	public static final String dropTable =
			"DROP TABLE Store";
	
	public void printTableData() throws SQLException {
		resultSet = statement.executeQuery(selectAll);
		//print header
		int dashCount = 0;
		for(int i = 1; i <= metaData.getColumnCount(); i++) {
			System.out.print(metaData.getColumnLabel(i) + " ");
			dashCount += metaData.getColumnLabel(i).length() + 1;
		}
		System.out.println();
		System.out.println("-".repeat(--dashCount));
		
		//print data
		while(resultSet.next()) {
			for(int i = 1; i<= metaData.getColumnCount(); i++ ) {
				System.out.printf("%-" + (metaData.getColumnLabel(i).toString().length()+1) +
						"s", resultSet.getObject(i) + " ");
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
	 * Java method for executing the insertData SQL method.
	 * @author Kevin
	 */
	public void insertData()
	{
		try {
		statement.execute(insertData);
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
	public ResultSet sortStore(String selection) {
		String s = String.format("SELECT * FROM Store "
				+ "ORDER BY %s", selection);
		try {
			resultSet = statement.executeQuery(s);
		} catch(SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		}
		return resultSet;
	}
	
	/**
	 * Filters the Store SQL Table depending on the given columns name and the contents inside.
	 * 
	 * @param name
	 * @param filter
	 * @author James
	 * @return 
	 */
	public ResultSet filterStore(String name, String filter) {
		String n;
		if(name == "Id" || name == "Pharmacy")
			 n = filter;
		else
			n = "'" + filter + "'";
		String s = String.format("SELECT * FROM Store WHERE %s = %s", name, n);
		try {
			resultSet = statement.executeQuery(s);
		} catch(SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		}
		return resultSet;
	}
	
}