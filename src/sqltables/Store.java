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
		resultSet = statement.executeQuery(Employee.selectAll);
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
	public static void newStore(int storeID, String city, int pharmacy, int zip) {
	try {
	Connection connection = DriverManager.getConnection(databaseURL);
	PreparedStatement prep = connection.prepareStatement(insertData);
	prep.setInt(1, storeID);
	prep.setString(2, city);
	prep.setInt(3, pharmacy);
	prep.setInt(4, zip);

	int success = prep.executeUpdate();

	if (success > 0)
	System.out.println("New Store added! ");
	else
	System.out.println("Bad or incomplete data. Please retry adding new Store.");

	prep.close();
	connection.close();

	} catch (SQLException e) {
	System.out.println("There was a problem adding a new store to the Store Database.");
	}
	}
	
}