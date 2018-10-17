package edu.cvtc.MyTODOList;

import java.util.Scanner;

import edu.cvtc.MyTODOList.model.Event;
import edu.cvtc.MyTODOList.model.Event.EventRecurFreq;

import java.util.ArrayList;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author csather
 * 
 * Main application loop. 
 *
 */
public class Main {
	
	static String input;
	static int userSelection = 0;
	static Scanner scanner = new Scanner(System.in);
	static ArrayList<Event> events = new ArrayList<Event>();
	static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
	
	public static void main(String[] args) {
		
		do {
			switch (mainMenu()) {
				case(1): // Display events.
					userSelection = 0; // reset user selection
					viewEventsMenu();
					break;
				case(2): // Create a new event.
					userSelection = 0;
					addEventMenu();
					break;
				case(3): // Edit an event.
					break;
				case(4): // Delete an event.
					break;
				case(5): // Quit application.
					System.exit(0);
			}
			
		} while(true);
	}
	
	// Display the main menu, prompting the user for what action they wish to take.
	// Returns an int to be used to invoke the function associated with the provided action.
	public static int mainMenu() {
		do {
			System.out.print(">> Select the desired action:\n"
					+ "\n"
					+ "1 - View Events\n"
					+ "2 - Add Event\n"
					+ "3 - Edit Event\n"
					+ "4 - Delete Event\n"
					+ "5 - Quit\n"
					+ "\n"
					+ "Enter Action (1-5): ");
			
			input = scanner.nextLine();
			userSelection = validateInput(input, 1, 5);
		} while(userSelection == 0);
		
		return userSelection;
	}
	
	// Prompt user to select a specific day, week, month, or provided date range which the user wishes to view.
	public static void viewEventsMenu() {
		do {
			System.out.print(">> Select timeframe to view:\n"
					+ "\n"
					+ "1 - View Today\n"
					+ "2 - View This Week\n"
					+ "3 - Go Back to Main Menu\n"
					+ "4 - Quit\n"
					+ "\n"
					+ "Enter Action (1-4): ");
			
			input = scanner.nextLine();
			userSelection = validateInput(input, 1, 4);
		} while(userSelection == 0);
		
		switch(userSelection) {
			case(1): // View today
				if (events.size() == 0) {
					System.out.println(">> You have no events scheduled.");
				} else {
					for (Event event : events) {
						// If the date of the current event is the same as today, display the event.
						if (LocalDate.parse((CharSequence) event.getEventDate(), dateFormat).equals(LocalDate.now())) {
							displayEvent(event);
						}
					}
				}
				break;
			case(2): // View this week
				if (events.size() == 0) {
					System.out.println(">> You have no events scheduled.");
				} else {
					for (Event event : events) {
						// If the date of the current event is between this weeks Monday and Sunday, display the event.
						if (LocalDate.parse((CharSequence) event.getEventDate(), dateFormat).isAfter(LocalDate.now().with(DayOfWeek.MONDAY)) &&
								LocalDate.parse((CharSequence) event.getEventDate(), dateFormat).isBefore(LocalDate.now().with(DayOfWeek.SUNDAY))) {
							displayEvent(event);
						}
					}
				}
				break;
			case(3): // Go back to main
				break;
			case(4): // Quit
				System.exit(0);
		}
	}
	
	// Display event creation prompts.
	public static void addEventMenu() {
		Event tempEvent = new Event();
		System.out.println("<-- Event Creation -->");
		
		outerloop: 
		do {
			System.out.print(">> Enter event name: ");
			tempEvent.setEventName(scanner.nextLine());
			System.out.println();
			
			System.out.print(">> Enter event description: ");
			tempEvent.setEventDesc(scanner.nextLine());
			System.out.println();
			
			do {
				System.out.print(">> Enter event date (MM-DD-YYYY): ");
				
				try { // Attempt to parse entered date, and if successful, exit loop. Otherwise prompt again.
					LocalDate tmpDate = LocalDate.parse((CharSequence) scanner.nextLine(), dateFormat);
					tempEvent.setEventDate(tmpDate.format(dateFormat).toString());
					break;
				} catch (DateTimeParseException e) {
					System.out.println("Invalid date. Please enter your date in the format MM-DD-YYYY. \n"
							+ "Today's date is " + LocalDate.now().format(dateFormat).toString());
				}
			} while(true);
			System.out.println();
			
			System.out.print(">> Enter event time in 24h format (HH:mm): ");
			do {
				try { // Attempt to parse entered date, and if successful, exit loop. Otherwise prompt again.
					LocalTime tmpTime = LocalTime.parse((CharSequence) scanner.nextLine(), timeFormat);
					tempEvent.setEventTime(tmpTime.format(timeFormat).toString());
					break;
				} catch (DateTimeParseException e) {
					System.out.println("Invalid time. Please enter your time in the 24h format HH:mm. \n"
							+ "The current time is " + LocalTime.now().withSecond(0).withNano(0).format(timeFormat).toString());
				}
			} while(true);
			System.out.println();
			
			System.out.print(">> Does this event reoccur (Y/N)? ");
			input = scanner.nextLine().toString().toLowerCase();

			if (input.equals("y") || input.equals("yes")) {

				tempEvent.setEventRecur(true);
				System.out.println(">> How often should this event reoccur?");
				
				for (EventRecurFreq day: EventRecurFreq.values()) {
					System.out.println(day);
				}

				do {
					System.out.print(">> Select one of the above: ");

					input = scanner.nextLine().toString().toUpperCase();
					for (EventRecurFreq day : EventRecurFreq.values()) {
						if (input.equals(day.toString())) {
							tempEvent.setEventFrequency(day.toString());
						}
					}

					if (null == tempEvent.getEventFrequency()) {
						System.out.println("Invalid selection. Please choose one of the above options.");
					} else {
						break;
					}

				} while(true);

			} else {
				tempEvent.setEventRecur(false);
			}

			System.out.print(">> Set event priority (1-10, 1: Not very important, 10: Very important): ");
			input = scanner.nextLine();
			tempEvent.setEventPriority(validateInput(input, 1, 10));
			
			System.out.println(">> Current event:");
			displayEvent(tempEvent);
			
			// Ask user if the event is correct. If so, add tempEvent to events and exit loop. Otherwise do it again.
			System.out.print(">> Is this correct (Y/N)? ");
			input = scanner.nextLine().toString().toLowerCase();
			
			if (input.equals("y") || input.equals("yes")) {
				
				for (Event event : events) {
					if (tempEvent.getEventDate().equals(event.getEventDate())) {
						if (tempEvent.getEventTime().equals(event.getEventTime())) {
							
							// If event occurs at the same time as another event, ask the user if they want to schedule 
							// two events at the same time
							System.out.print("You already have an event that occurs at this time: \"" + event.getEventName() 
							+ "\". Would you still like to add this event (Y/N)? ");
							input = scanner.nextLine().toString().toLowerCase();
							
							if (input.equals("y") || input.equals("yes")) {
								events.add(tempEvent);
								break outerloop;
							} else {
								break outerloop;
							}
						}
					} 
					
					// If event doesn't occur on the same date as another, add the event
					else {
						events.add(tempEvent);
						break outerloop;
					}
				}
				
				events.add(tempEvent);
				break;
			}
			
		} while(true);

		//TODO: Sort events based upon their priority.
	}
	
	// Prompt user to provide an event to edit, then display event edit prompts. 
	public static void editEventMenu() {}
	
	// Prompt user for an event to delete.
	public static void deleteEventMenu() {}
	
	// Display event editor prompts for creation and editing of events.
	public static void eventEditor() {}
	
	// Retrieves events for a specified day.
	// TODO: Implement this.
//	public static ArrayList<Event> retrieveEvents(LocalDate date) {
//		ArrayList<Event> events = SQLiteUtility.retrieveEventsFromDatabase();
//		
//		// Cycle through all the returned events and remove those which don't match the given date.
//		// TODO: Replace this with more specific sqliteutility method once available.
//		for (Event event : events) {
//			if (event.getEventDate() != LocalDate.now().toString()) {
//				events.remove(events.indexOf(event));
//			}
//		}
//		
//		return events;
//	}
	
	// Retrieves events for a date range.
	// TODO: Implement this.
//	public static ArrayList<Event> retrieveEvents(LocalDate startDate, LocalDate endDate) {
//		ArrayList<Event> events = SQLiteUtility.retrieveEventsFromDatabase();
//		
//		// Cycle through all returned events and remove those which aren't within the given date range.
//		// TODO: Replace this with more specific sqliteutility method once available.
//		for (Event event: events) {
//			if (startDate.isEqual(eventDate) event.getEventDate())
//		}
//	}
	// Display a single event.
	public static void displayEvent(Event event) {
		System.out.println(">> Event: " + event.getEventName() + "\n"
				+ "Event Description: " + event.getEventDesc() + "\n"
				+ "Event Date: " + event.getEventDate() + "\n"
				+ "Event Time: " + event.getEventTime() + "\n"
				+ "Reoccuring: " + event.isEventRecur() + "\n"
				+ "Reoccuring frequency: " + event.getEventFrequency() + "\n"
				+ "Priority: " + event.getEventPriority() + "\n"
				+ "\n");
	}
	
	//TODO:  Todd's add-on  Tweaks maybe?
	// Display an ArrayList of Events.
	public static void displayEvents(ArrayList<Event> events) {
		
		int eventArrayLength = events.size();
		
		if(eventArrayLength > 8) {
			System.out.println("You cannot schedule more than 8 events.");
		} else {
			System.out.println("You currently have " + eventArrayLength + " events scheduled");
		}
		
	}
	
	// Validate input based upon a provided range.
	// Returns a zero if the input was invalid.
	public static int validateInput(String input, int minValue, int maxValue) throws NumberFormatException {
		int temp;
		
		// Determine if the user provided an acceptable action.
		try {
			temp = Integer.parseInt(input);
			
			if (temp >= minValue && temp <= maxValue) {
				return temp;
			} else {
				System.out.println(">> Please enter a valid selection (" + Integer.toString(minValue) + "-" + Integer.toString(maxValue) + ").");
				return 0; 
			}
			
		} catch (NumberFormatException exception) {
			System.out.println(">> Invalid action provided. Please enter a valid selection (" + Integer.toString(minValue) + "-" + Integer.toString(maxValue) + ")");
			return 0;
		}	
	}
}
