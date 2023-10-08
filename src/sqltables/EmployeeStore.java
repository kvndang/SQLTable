package sqltables;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class used to make the table that joins the Employee and Store SQL Tables
 * @author Kevin Dang
 */
public class EmployeeStore {
	ResultSet resultSet;
	ResultSetMetaData metaData;
	Statement statement;
	
	public EmployeeStore(Statement statement)
	{
		this.statement = statement;
		try {
		resultSet = statement.executeQuery(InnerJoin);
		metaData = resultSet.getMetaData();
		System.out.println(metaData.getColumnCount());
		}catch(SQLException e)
		{
			System.out.println("Something went wrong accessing SQL");
		}
	}
	
	public static final String InnerJoin = "SELECT FirstName, LastName, City, Zipcode"
			+ "FROM Employee"
			+ "INNER JOIN Store ON Employee.StoreID = Store.Id";
	
	public static final String selectAll = "SELECT * FROM Employee";
	
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
	
}