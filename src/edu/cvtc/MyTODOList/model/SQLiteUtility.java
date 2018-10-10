/**
 * 
 */
package edu.cvtc.MyTODOList.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.cvtc.MyTODOList.model.Event.EventRecurFreq;


/**
 * @author matt
 * 
 * References: http://www.sqlitetutorial.net/sqlite-java/insert/
 *
 */
public class SQLiteUtility {

	// This constant stores the sqlite file location within the projects directory. 
	public static final String SQLITEFILELOCATION = "jdbc:sqlite:/resources/event.db";
	public static final String LOADDRIVER = "org.sqlite.JDBC";
	
	
	
	// ------------------------------------------------------------------------------------------------------------------------- //
	// This function deletes events from the database //
	// ------------------------------------------------------------------------------------------------------------------------- //
	
	public static void deleteEventFromDatabase(Event event) throws ClassNotFoundException, SQLException{
		
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
	

	
	// ------------------------------------------------------------------------------------------------------------------------- //
	// This function retrieves and returns a single event from the database //
	// ------------------------------------------------------------------------------------------------------------------------- //
	public static Event retrieveEventFromDatabase(Event event) throws ClassNotFoundException, SQLException { 
		
		// Initialize objects
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		// Instantiate object to be returned
		 Event e = new Event();
		
		try {
		   // Collect data from the event passed into this String object
		   final String DATE = event.getEventDate();
		   final String TIME = event.getEventTime();
		   
		// Used to load a driver into memory (and to determine the correct driver)
		   Class.forName(LOADDRIVER);
			
		   // Connect the database to the code
		   connection = SQLiteDBConnection.connectDB();
		  
		   // Set autocommit to false, I am not sure if this is necessary 
		   connection.setAutoCommit(false);
		   
		   // Let us select the records and display them
	      final String SQL = "SELECT * "
	      					+ "FROM event "
	      					+ "WHERE EventDate = \"?\" & \"?\";";
		   
		   	// Connect the prepared statement and the SQL code
		    preparedStatement = connection.prepareStatement(SQL);
		    
		    preparedStatement.setString(1, DATE);
		    preparedStatement.setString(2, TIME);

		    // Execute the SQL statement
		      resultSet = preparedStatement.executeQuery(SQL);

		      // Extract data from result set, 1 row at a time. If there are no rows it returns false.
		      while(resultSet.next()){
		    	  
		    	
		    	  
		         // Retrieve by column names
		    	 e.eventID = resultSet.getInt("EventID");
			     e.eventName = resultSet.getString("EventName");
		         e.eventDate = resultSet.getString("EventDate");
		         e.eventTime = resultSet.getString("EventTime");
		         
		         final int R = resultSet.getInt("EventRecur");
		         if (R == 0) {
		        	 e.setEventRecur(false); 
		         } else {
		        	 e.setEventRecur(true);
		         }
		         
		         // Unsure if the eventRecurFreq will be a Blob type property
			     e.eventFrequency = EventRecurFreq.valueOf(resultSet.getString("EventRecurFreq"));
		         e.eventStart = resultSet.getString("EventStartTime");
		         e.eventEnd = resultSet.getString("EventEndTime");
		         
		         e.eventPriority = resultSet.getInt("EventPriority");
		         
		         final int CTL = resultSet.getInt("EventCountTowardsLimit");
		         if (CTL == 0) {
		        	 e.eventLimited = false;
		         } else {
		        	 e.eventLimited = true;
		         }
		         
		         e.eventCategory = resultSet.getString("EventCategory");
		         final int HR = resultSet.getInt("EventHasReminder");
		         if (HR == 0) {
		        	 e.eventReminder = false;
		         } else {
		        	 e.eventReminder = true;
		         }
		         
		         e.eventReminderTime = resultSet.getString("EventReminderTime");
		        
		       
		        
				
			}
		      connection.commit();
		}
		// Error handling
		catch (SQLException ex) {
		   System.out.println(ex.getMessage());
		}
		// Closing off the connections. This is very important.
		finally {
			resultSet.close();
		   preparedStatement.close();
		   connection.close();
		   
		}
		
		// Return the full event object
		return e;
	}
	
	
	
	// ------------------------------------------------------------------------------------------------------------------------- //
		// This function retrieves and returns a list of events //
		// ------------------------------------------------------------------------------------------------------------------------- //
		public static ArrayList<Event> retrieveEventsFromDatabase() throws SQLException, ClassNotFoundException { 

			final ArrayList<Event> events = new ArrayList<>();
			
			// Initialize objects
			Statement statement = null;
			Connection connection = null;
			ResultSet resultSet = null;
			
			// Instantiate object
			Event e = new Event();
			
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
					

			         // Retrieve by column names
			         e.eventID = resultSet.getInt("EventID");
			         e.eventName = resultSet.getString("EventName");
			         e.eventDate = resultSet.getString("EventDate");
			         e.eventTime = resultSet.getString("EventTime");
			         
			         final int R = resultSet.getInt("EventRecur");
			         if (R == 0) {
			        	 e.setEventRecur(false); 
			         } else {
			        	 e.setEventRecur(true);
			         }
			         
				     e.eventFrequency = EventRecurFreq.valueOf(resultSet.getString("EventRecurFreq"));
				     
			         e.eventStart = resultSet.getString("EventStartTime");
			         e.eventEnd = resultSet.getString("EventEndTime");
			         
			         e.eventPriority = resultSet.getInt("EventPriority");
			         
			         final int CTL = resultSet.getInt("EventCountTowardsLimit");
			         if (CTL == 0) {
			        	 e.eventLimited = false;
			         } else {
			        	 e.eventLimited = true;
			         }
			         
			         e.eventCategory = resultSet.getString("EventCategory");
			         final int HR = resultSet.getInt("EventHasReminder");
			         if (HR == 0) {
			        	 e.eventReminder = false;
			         } else {
			        	 e.eventReminder = true;
			         }
			         
			         e.eventReminderTime = resultSet.getString("EventReminderTime");
			        
			         // Add event to the events ArrayList
					events.add(e); 
			        
					
					
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
			
			// Return the full events ArrayList
			return events;
		}
		
		

	// ------------------------------------------------------------------------------------------------------------------------- //
	// This function updates an Event in the database // 
	// ------------------------------------------------------------------------------------------------------------------------- //
	
	public static void updateEventInDatabase(Event event) throws SQLException, ClassNotFoundException {
		
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
	
	
	// ------------------------------------------------------------------------------------------------------------------------- //
	// This functions writes events to a database // 
	// ------------------------------------------------------------------------------------------------------------------------- //
	
	public void writeEventToDatabase(Event event) throws SQLException, ClassNotFoundException {
		
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
		   
		    preparedStatement.setInt(1, event.getEventID());
		    preparedStatement.setString(2, event.getEventName());
		    preparedStatement.setString(3, event.getEventDesc());
		    preparedStatement.setString(3, event.getEventDate());
		    preparedStatement.setString(4, event.getEventTime());
		    
		    final boolean ISEVENTRECUR = event.isEventRecur(); // Bool to Int conversion
		    final int ER;
		    if (ISEVENTRECUR == true) {
		    	 ER = 1;
		    	} else {
		    	 ER = 0;
		    	}
		    
			preparedStatement.setInt(5, ER); 
		    preparedStatement.setString(6, event.eventFrequency.toString());
		    preparedStatement.setString(7, event.getEventStart());
		    preparedStatement.setString(8, event.getEventEnd());
		    preparedStatement.setInt(9, event.getEventPriority());
		    
		    final boolean ISEVENTLIMITED = event.isEventLimited(); // Bool to Int conversion
		    final int EL;
		    if (ISEVENTLIMITED == true) { 
		    	EL = 1; 
		    } else { EL = 0;
		    }
		    preparedStatement.setInt(10, EL);
		    
		    preparedStatement.setString(11, event.getEventCategory());
		    
		    final boolean ISEVENTREMINDER = event.isEventReminder(); // Bool to Int conversion
		    final int ERM;
		    if (ISEVENTREMINDER == true) {
		    	 ERM = 1;
		    	} else {
		    	 ERM = 0;
		    	}
		    
		    preparedStatement.setInt(12, ERM);
		    preparedStatement.setString(13, event.getEventReminderTime());
		    
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


