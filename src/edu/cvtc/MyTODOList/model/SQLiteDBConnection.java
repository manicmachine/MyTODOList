/**
 * 
 */
package edu.cvtc.MyTODOList.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * @author matt
 * 
 * This Class provides behavior which allows you to connect to the Calendar SQLite Database.
 *
 */
public class SQLiteDBConnection {
	public static Connection connectDB() {
		
		// Create a connection object
		Connection connection = null;
		
		try{
			// The connection string for the Calendar SQLite Database
			String url = "jdbc:sqlite:/resources/event.db";
			
			// Create a connection to the database
			connection = DriverManager.getConnection(url);
			
			
			// Success message sent to the console
			System.out.println("Connection to the SQLite database successful.");
			
		// Error handling
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		finally {
			// If the program is still connected to the DB then close it
			try {
				if (connection != null) {
					connection.close();
				}
			} 
			// Error handling
			catch(SQLException e) {
				System.out.print(e.getMessage());
			}
		}
		return connection;
		

	}
}
