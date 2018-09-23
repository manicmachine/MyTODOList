package edu.cvtc.MyTODOList;

import java.util.Scanner;

public class Main {
	
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		while(true) {
			switch (mainMenu()) {
				case(1):
					// Display events.
					break;
				case(2):
					// Create a new event.
					break;
				case(3):
					// Edit an event.
					break;
				case(4):
					// Delete an event.
					break;
				case(5):
					// Quit application.
					System.exit(0);
					break;
			}
		}
	}
	
	// Display the main menu, prompting the user for what action they wish to take.
	// Returns an int to be used to invoke the function associated with the provided action.
	public static int mainMenu() {
		
		String input;
		int userSelection;
		
		while(true) {
			System.out.print("Select the desired action:\n"
					+ "\n"
					+ "1 - View Events\n"
					+ "2 - Add Event\n"
					+ "3 - Edit Event\n"
					+ "4 - Delete Event\n"
					+ "5 - Quit\n"
					+ "\n"
					+ "Enter Action (1-5): ");
			
			input = scanner.nextLine();
			
			// Determine if the user provided an acceptable action.
			try {
				userSelection = Integer.parseInt(input);
				
				if (userSelection >= 1 && userSelection <= 5) {
					return userSelection;
				} else {
					System.out.println("Please enter a valid selection (1-5).");
				}
				
			} catch (NumberFormatException exception) {
				System.out.println("Invalid action provided. Please enter a valid selection (1-5)");
			}	
		}
	}
	
	// Prompt user to select a specific day, week, month, or provided date range which the user wishes to view.
	public static void viewEventsMenu() {}
	
	// Display event creation prompts.
	public static void addEventMenu() {}
	
	// Prompt user to provide an event to edit, then display event edit prompts. 
	public static void editEventMenu() {}
	
	// Prompt user for an event to delete.
	public static void deleteEventMenu() {}
	
	// Display event editor prompts for creation and editing of events.
	public static void eventEditor() {}
	
	// Display an array of events.
	public static void displayEvents(Event[] events) {}
}
