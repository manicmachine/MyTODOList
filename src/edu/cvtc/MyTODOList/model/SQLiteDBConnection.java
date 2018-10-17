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
 **/
public class SQLiteDBConnection {
    public static Connection connectDB() {

        // Create a connection object
        Connection connection = null;

        try{
            // **Fellow developers: This is the sample DB. Uncomment for use.*
            // final String SQLITEFILELOCATION = "jdbc:sqlite:/resources/sampleEvent.db";

            // This constant stores the sqlite file location within the projects directory. Comment this out for sample DB use.
            final String SQLITEFILELOCATION = "jdbc:sqlite:/Users/csather/Google Drive/Programming/eclipse-ee-workspace/MyTODOList/src/edu/cvtc/MyTODOList/resources/event.db";

            // Create a connection to the database
            connection = DriverManager.getConnection(SQLITEFILELOCATION);


            // Success message sent to the console
            System.out.println("Connection to the SQLite database successful.");

        // Error handling
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return connection;

    }
}