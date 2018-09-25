/**
 * 
 */
package edu.cvtc.MyTODOList.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 * @author matt
 * 
 * References: http://www.sqlitetutorial.net/sqlite-java/insert/
 *
 */
public class SQLiteUtility {

	// This stores the sqlite file location within the projects directory. 
	public static final String SQLITEFILELOCATION = "jdbc:sqlite:/resources/event.db";
	public static final String LOADDRIVER = "org.sqlite.JDBC";
	
	// This function deletes events from the database
	public static void deleteEventFromDatabase(Event event){
		
		// Create objects
	    PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		// TODO: Prepare query with data from the Event
		final String SQL = "";
		
		try {
			// Used to load a driver into memory (and to determine the correct driver)
		   Class.forName(LOADDRIVER);
			
		     connection = SQLiteDBConnection.connectDB();
		  
		     // Set autocommit to false, I am not sure if this is necessary 
			   connection.setAutoCommit(false);
 
			// Connect the prepared statement and the SQL code
			  preparedStatement = connection.prepareStatement(SQL);
		     
		     // Execute the SQL statement
			    preparedStatement.executeUpdate();
			    connection.commit();

		}
		// Error handling
		catch (SQLException ex) {
		   System.out.println(ex.getMessage());
		}
		// Closing off the connections. This is very important.
		finally {
			preparedStatement.close();
			connection.close();
		}
		
	}
	
	
	// This retrieves and returns a list of 
	public static List<Event> retrieveEventsFromDatabase() { 

		final List<Event> events = new ArrayList<>();
		
		// Initialize objects
		Statement statement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		
		try {
			// Used to load a driver into memory (and to determine the correct driver)
		   Class.forName(LOADDRIVER);
			
		  // Create the connection 
		   connection = SQLiteDBConnection.connectDB();
		   
		   // Create the SQL statement
		   statement = connection.createStatement();


		   // Let us select all the records and display them.
		      final String SQL = "SELECT * FROM event;";
		      resultSet = statement.executeQuery(SQL);


		      // Extract data from result set, 1 row at a time. If there are no rows it returns false.
		      while(resultSet.next()){
		    	  
				// Create event object to hold sql data 
				Event e = new Event();

		         // Retrieve by column names
		         e.eventID = resultSet.getString("EventID");
		         e.eventName = resultSet.getString("EventName");
		         e.setEventDate = resultSet.getDate("EventDescription");
		         e.setEventTime = resultSet.getString("EventTime");
		         
		         final int R = resultSet.getInt("EventRecur");
		         if (R == 0) {
		        	 e.setEventRecur = false;
		         } else {
		        	 e.setEventRecur = true;
		         }
		         
		         // Unsure if the eventRecurFreq will be a Blob type property
			     e.setEventRecurFreq = resultSet.getBlob("EventRecurFreq");
		         e.setEventStartTime = resultSet.getString("EventStartTime");
		         e.setEventEndTime = resultSet.getString("EventEndTime");
		         
		         e.setEventPriority = resultSet.getInt("EventPriority");
		         
		         final int CTL = resultSet.getInt("EventCountTowardsLimit");
		         if (CTL == 0) {
		        	 e.setEventLimited = false;
		         } else {
		        	 e.setEventLimited = true;
		         }
		         
		         e.setEventCategory = resultSet.getInt("EventCategory");
		         final int HR = resultSet.getInt("EventHasReminder");
		         if (HR == 0) {
		        	 e.setEventReminder = false;
		         } else {
		        	 e.setEventReminder = true;
		         }
		         
		         e.setEventReminderTime = resultSet.getString("EventReminderTime");
		        
		         // Add event to the events ArrayList
				events.add(e); 
		        
				// Return the full events ArrayList
				return events;
			}
		    
		}
		// Error handling
		catch (SQLException ex) {
		   System.out.println(ex.getMessage());
		}
		// Closing off the connections. This is very important.
		finally {
			resultSet.close();
		   statement.close();
		   connection.close();
		}
	}

	
	public static void updateEventInDatabase(Event event) {
		
		// Initialize objects
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		// TODO: Prepare query with information Event
		// Initialize immutable statement
		final String SQL = "";
		
		try {
			// Used to load a driver into memory (and to determine the correct driver)
		   Class.forName(LOADDRIVER);
			
		     connection = SQLiteDBConnection.connectDB();

		     // Set autocommit to false, I am not sure if this is necessary 
			   connection.setAutoCommit(false);
			  

			// Connect the prepared statement and the SQL code
		     preparedStatement = connection.prepareStatement(SQL);
		     
		 
		     // Execute the SQL statement
		    preparedStatement.executeUpdate();
		    connection.commit();
			    
		}
		
		// Error handling
		catch (SQLException ex) {
		   System.out.println(ex.getMessage());
		}
		// Closing off the connections. This is very important.
		finally {
		  preparedStatement.close();
		   connection.close();
		}
	}
	
	// This functions writes events to a database
	public void writeEventToDatabase(Event event) throws SQLException {
		
		// Initialize objects
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		// Initialize immutable statement
		final String SQL = "INSERT INTO event (EventID, EventName, EventDate, EventTime, EventRecur, EventRecurFreq, " + 
					 "EventStartTime, EventEndTime, EventPriority, EventCountTowardsLimit, EventCategory, " +
					 "EventHasReminder, EventReminderTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			// Used to load a driver into memory (and to determine the correct driver)
		   Class.forName(LOADDRIVER);
			
		   // Connect the database to the code
		   connection = SQLiteDBConnection.connectDB();
		  
		   // Set autocommit to false, I am not sure if this is necessary 
		   connection.setAutoCommit(false);
		   
		   	// Connect the prepared statement and the SQL code
		    preparedStatement = connection.prepareStatement(SQL);
		   
		    preparedStatement.setInt(1, event.getEventID);
		    preparedStatement.setString(2, event.getEventName);
		    preparedStatement.setDate(3, event.getEventDate);
		    preparedStatement.setString(4, event.getEventTime);
		    preparedStatement.setBlob(5, event.getEventRecur);
		    preparedStatement.setString(6, event.getEventRecurFreq);
		    preparedStatement.setString(7, event.getEventStart);
		    preparedStatement.setInt(8, event.getEventEnd);
		    preparedStatement.setInt(9, event.getEventPriority);
		    preparedStatement.setInt(10, event.getEventLimited);
		    preparedStatement.setInt(11, event.getEventCategory);
		    preparedStatement.setString(12, event.getEventReminder);
		    preparedStatement.setString(13, event.getEventReminderTime);
		    
		    // Execute the SQL statement
		    preparedStatement.executeUpdate();
		    connection.commit();
	    

		}
		// Error handling
		catch (SQLException ex) {
		   System.out.println(ex.getMessage());
		}
		
		// Closing off the connections. This is very important.
		finally {
		   preparedStatement.close();
		   connection.close();
		}

	}
	}
