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
 * SQLite browser (small download size and very fast): https://sqlitebrowser.org/
 *
 */
public class SQLiteUtility {

	// A constant to store the load driver URL(?)
	public static final String LOADDRIVER = "org.sqlite.JDBC";

	// ----------------------------------------------------------------------------------------------------- //
	// This function retrieves and returns a single event from the database //
	// ----------------------------------------------------------------------------------------------------- //
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
					+ "WHERE EventDate = \"?\" & EventTime = \"?\";";

			// Connect the prepared statement and the SQL code
			preparedStatement = connection.prepareStatement(SQL);

			preparedStatement.setString(1, DATE);
			preparedStatement.setString(2, TIME);

			// Execute the SQL statement
			resultSet = preparedStatement.executeQuery(SQL);

			// Extract data from result set, 1 row at a time. If there are no rows it returns false.
			while(resultSet.next()){

				// Retrieve by column names
				e.setEventID(resultSet.getInt("EventID"));
				e.setEventName(resultSet.getString("EventName"));
				e.setEventDesc(resultSet.getString("EventDesc"));
				e.setEventDate(resultSet.getString("EventDate"));
				e.setEventTime(resultSet.getString("EventTime"));

				final int R = resultSet.getInt("EventRecur");
				if (R == 0) {
					e.setEventRecur(false); 
				} else {
					e.setEventRecur(true);
				}
				
				String erf = resultSet.getString("EventRecurFreq");
				e.setEventFrequency(EventRecurFreq.valueOf(erf));
						
				e.setEventStart(resultSet.getString("EventStartTime"));
				e.setEventEnd(resultSet.getString("EventEndTime"));
				e.setEventPriority(resultSet.getInt("EventPriority"));
		
				final int CTL = resultSet.getInt("EventCountTowardsLimit");
				if (CTL == 0) {
					e.setEventLimited(false);
				} else {
					e.setEventLimited(true);
				}

				e.setEventCategory(resultSet.getString("EventCategory"));
	
				final int HR = resultSet.getInt("EventHasReminder");
				if (HR == 0) {
					e.setEventReminder(false);
				} else {
					e.setEventReminder(true);
				}

				e.setEventReminderTime(resultSet.getString("EventReminderTime"));

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



	// ----------------------------------------------------------------------------------------------------- //
	// This function retrieves and returns a list of events //
	// ----------------------------------------------------------------------------------------------------- //
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
				e.setEventID(resultSet.getInt("EventID"));
				e.setEventName(resultSet.getString("EventName"));
				e.setEventDesc(resultSet.getString("EventDesc"));
				e.setEventDate(resultSet.getString("EventDate"));
				e.setEventTime(resultSet.getString("EventTime"));

				final int R = resultSet.getInt("EventRecur");
				if (R == 0) {
					e.setEventRecur(false); 
				} else {
					e.setEventRecur(true);
				}
				
				String erf = resultSet.getString("EventRecurFreq");
				e.setEventFrequency(EventRecurFreq.valueOf(erf));
						
				e.setEventStart(resultSet.getString("EventStartTime"));
				e.setEventEnd(resultSet.getString("EventEndTime"));
				e.setEventPriority(resultSet.getInt("EventPriority"));
		
				final int CTL = resultSet.getInt("EventCountTowardsLimit");
				if (CTL == 0) {
					e.setEventLimited(false);
				} else {
					e.setEventLimited(true);
				}

				e.setEventCategory(resultSet.getString("EventCategory"));
	
				final int HR = resultSet.getInt("EventHasReminder");
				if (HR == 0) {
					e.setEventReminder(false);
				} else {
					e.setEventReminder(true);
				}

				e.setEventReminderTime(resultSet.getString("EventReminderTime"));

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

	// -------------------------------------------------------------------------------------------------------- //
	// This functions writes events to a database // 
	// -------------------------------------------------------------------------------------------------------- //

	public static void writeEventToDatabase(Event event) throws SQLException, ClassNotFoundException {

		// Initialize objects
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		// Initialize immutable statement
		final String SQL = "INSERT INTO event (EventName, EventDesc, EventDate, EventTime, EventRecur, EventFreq, " + 
				"EventStartTime, EventEndTime, EventPriority, EventCountTowardsLimit, EventCategory, " +
				"EventHasReminder, EventReminderTime) "
				+ "VALUES (\"?\", \"?\", \"?\", \"?\", \"?\", \"?\", \"?\", \"?\", \"?\", \"?\", \"?\", \"?\", \"?\")";

		try {
			// Used to load a driver into memory (and to determine the correct driver)
			Class.forName(LOADDRIVER);

			// Connect the database to the code
			connection = SQLiteDBConnection.connectDB();

			// Set autocommit to false, I am not sure if this is necessary 
			connection.setAutoCommit(false);

			// Connect the prepared statement and the SQL code
			preparedStatement = connection.prepareStatement(SQL);

			// Setting the prepared statements to be sent to the DB
			preparedStatement.setString(1, event.getEventName());
			preparedStatement.setString(2, event.getEventDesc());
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


	// -------------------------------------------------------------------------------------------------------- //
	// This function updates an Event in the database //
	// -------------------------------------------------------------------------------------------------------- //

	public static void updateEventInDatabase(Event event) throws SQLException, ClassNotFoundException {

		// Initialize objects
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		// TODO: Prepare query with information Event
		final String DATE = event.getEventDate();
		final String TIME = event.getEventTime();

		// Initialize immutable query
		final String SQL = "UPDATE event "
				+ "SET EventName = \"?\", "
				+ "EventDesc = \"?\", "
				+ "EventDate = \"?\", "
				+ "EventTime = \"?\", "
				+ "EventRecur = \"?\", "
				+ "EventFreq = \"?\", " 
				+ "EventStartTime = \"?\", "
				+ "EventEndTime = \"?\", "
				+ "EventPriority = \"?\", "
				+ "EventCountTowardsLimit = \"?\", "
				+ "EventCategory = \"?\", " 
				+ "EventHasReminder = \"?\", "
				+ "EventReminderTime = \"?\""
				+ "WHERE EventDate = \"?\" & EventTime = \"?\";";


		try {
			// Used to load a driver into memory (and to determine the correct driver)
			Class.forName(LOADDRIVER);

			// Connect the database to the code
			connection = SQLiteDBConnection.connectDB();

			// Set autocommit to false, I am not sure if this is necessary 
			connection.setAutoCommit(false);

			// Connect the prepared statement and the SQL code
			preparedStatement = connection.prepareStatement(SQL);

			// Setting the prepared statements to be sent to the DB
			preparedStatement.setString(1, event.getEventName());
			preparedStatement.setString(2, event.getEventDesc());
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
			preparedStatement.setString(14, DATE);
			preparedStatement.setString(15, TIME);

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

	// --------------------------------------------------------------------------------------------------- //
	// This function deletes events from the database // 
	// --------------------------------------------------------------------------------------------------- //

	public static void deleteEventFromDatabase(Event event) throws ClassNotFoundException, SQLException{

		// Create objects
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		// Constant that holds the date and time from the event that you input
		final String DATE = event.getEventDate();
		final String TIME = event.getEventTime();

		// TODO: Prepare query with data from the Event
		final String SQL = "DELETE FROM event WHERE EventDate = \"?\" & EventTime = \"?\";";
		
		try {
			// Used to load a driver into memory (and to determine the correct driver)
			Class.forName(LOADDRIVER);

			connection = SQLiteDBConnection.connectDB();

			// Set autocommit to false, I am not sure if this is necessary 
			connection.setAutoCommit(false);

			// Connect the prepared statement and the SQL code
			preparedStatement = connection.prepareStatement(SQL);
			
			// Set the prepared statement properties to the date and time constants
			preparedStatement.setString(1, DATE);
			preparedStatement.setString(2, TIME);

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




