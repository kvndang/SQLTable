package sqltables;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

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
		resultSet = statement.executeQuery(innerJoin);
		metaData = resultSet.getMetaData();
		}catch(SQLException e)
		{
			System.out.println("Something went wrong accessing SQL");
		}
	}
	
	public static final String innerJoin = "SELECT FirstName, LastName, City, Zipcode"
			+ " FROM Employee"
			+ " INNER JOIN Store ON Employee.StoreID = Store.Id";

	
	public DefaultTableModel getTableModel() {
		try {
			resultSet = statement.executeQuery(innerJoin);
			DefaultTableModel model = new DefaultTableModel();
			
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