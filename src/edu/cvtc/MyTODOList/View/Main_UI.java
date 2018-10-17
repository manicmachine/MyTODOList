package edu.cvtc.MyTODOList.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.Component;

import javax.swing.JButton;

import edu.cvtc.MyTODOList.model.Event;
import edu.cvtc.MyTODOList.model.SQLiteUtility;
import edu.cvtc.MyTODOList.model.Event.EventRecurFreq;


import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.Font;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

public class Main_UI extends JFrame {

	private static final long serialVersionUID = 5630737946015469841L;
	private JPanel contentPane;
	static int numOfDays;
	static int realDay, realMonth, realYear, currentDay, currentMonth, currentYear;
	static SQLiteUtility sqliteUtility = new SQLiteUtility();
	static ArrayList<Event> allEvents = new ArrayList<>();
	static ArrayList<Event> filteredEvents = new ArrayList<>();
	static DefaultListModel eventModel = new DefaultListModel();
	static JTextArea eventDetails = new JTextArea();
	static JList eventList = new JList();
	static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");
	static JLabel yearLabel;
	static JButton previousBtn, nextBtn, todayBtn;
	static JTable calendarTable;
	static JComboBox yearList;
	static DefaultTableModel calendarModel;
	static JScrollPane calendarScroll;
	static JPanel calendarPanel;
	static Main_UI mainFrame;
	static GregorianCalendar calendar = new GregorianCalendar();
	static String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	public static void main(String[] args) {
		
//		try {
//			ArrayList<Event> tempEventList = sqliteUtility.retrieveEventsFromDatabase();
//			if (tempEventList.size() > 0) {
//				allEvents = tempEventList;
//			}
//		} catch (ClassNotFoundException | SQLException e1) {
//			System.out.println("Failed to retrieve events from database. " + e1.toString());
//		}
		
		realDay = calendar.get(GregorianCalendar.DAY_OF_MONTH);
		realMonth = calendar.get(GregorianCalendar.MONTH);
		realYear = calendar.get(GregorianCalendar.YEAR);
		currentDay = realDay;
		currentMonth = realMonth;
		currentYear = realYear;	

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame = new Main_UI();
					mainFrame.setResizable(false);
					mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Main_UI() {
		setTitle("MyTODOList - The Stupid Simple Scheduler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1320, 850);
		contentPane = new JPanel();
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel monthLbl = new JLabel(monthNames[currentMonth]);
		monthLbl.setBounds(500, 50, 235, 76);
		monthLbl.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		contentPane.add(monthLbl);
		
		// Initialize and configure main calendar
		yearLabel = new JLabel("Year:");
		yearList = new JComboBox();
		previousBtn = new JButton("<--");
		nextBtn = new JButton("-->");
		todayBtn = new JButton("Today");
		calendarModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		calendarTable = new JTable(calendarModel);
		calendarScroll = new JScrollPane(calendarTable);
		calendarPanel = new JPanel(null);

		calendarPanel.add(yearLabel);
		calendarPanel.add(yearList);
		calendarPanel.add(previousBtn);
		calendarPanel.add(nextBtn);
		calendarPanel.add(todayBtn);
		calendarPanel.add(calendarScroll);
		
		calendarPanel.setBounds(80, 120, 920, 680);
		yearLabel.setBounds(400, 0, 80, 25);
		yearList.setBounds(460 - yearList.getPreferredSize().width / 2, 0, 120, 25);
		previousBtn.setBounds(10, 0, 50, 25);
		nextBtn.setBounds(870, 0, 50, 25);
		todayBtn.setBounds(yearList.getX() + 195, 0, 120, 25);
		calendarScroll.setBounds(10, 25, 920, 650);
		
		for (int i = realYear - 5; i <= realYear + 5; i++) {
			yearList.addItem(String.valueOf(i));
		}
		yearList.setSelectedIndex(5);
		
		String[] headers = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		for (int i = 0; i < 7; i++) {
			calendarModel.addColumn(headers[i]);
		}
		
		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);
		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setCellSelectionEnabled(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		calendarTable.setRowHeight(120);
		
		calendarModel.setColumnCount(7);
		calendarModel.setRowCount(6);
		
		refreshCalendar(realDay, realMonth, realYear);
		
		previousBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (yearList.getSelectedIndex() != 0 || currentMonth != 0) {
					if (currentMonth == 0) {
						currentMonth = 11;
						currentYear -= 1;
						yearList.setSelectedIndex(yearList.getSelectedIndex() -1);
					} else {
						currentMonth -= 1;
					}
					
					monthLbl.setText(monthNames[currentMonth]);
					calendarTable.clearSelection();
					refreshCalendar(currentMonth, currentYear);
					
				} else {
					JOptionPane.showMessageDialog(null, "Sorry, but you can't go back more than 5 years.\nLeave the past where it is.", "Too far in the past!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		nextBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (yearList.getSelectedIndex() != 10 || currentMonth != 11) {
					if (currentMonth == 11) {
						currentMonth = 0;
						currentYear += 1;
						yearList.setSelectedIndex(yearList.getSelectedIndex() + 1);
					} else {
						currentMonth += 1;
					}
					
					monthLbl.setText(monthNames[currentMonth]);
					calendarTable.clearSelection();
					refreshCalendar(currentMonth, currentYear);
				} else {
					JOptionPane.showMessageDialog(null, "Sorry, but you can't go ahead more than 5 years.\nPerhaps you should be more present.", "Too far in the future!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		yearList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (yearList.getSelectedItem() != null) {
					currentYear = Integer.parseInt(yearList.getSelectedItem().toString());
					calendarTable.clearSelection();
					refreshCalendar(currentMonth, currentYear);
				}
				
			}
		});
		
		todayBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDay = realDay;
				currentMonth = realMonth;
				currentYear = realYear;
				
				yearList.setSelectedIndex(5);
				monthLbl.setText(monthNames[currentMonth]);
				filteredEvents = filterEvents(allEvents, currentDay, currentMonth, currentYear);
				updateEventsList(eventList, filteredEvents);
				refreshCalendar(currentDay, currentMonth, currentYear);
			}
		});
		
		contentPane.add(calendarPanel);

		eventList = new JList(allEvents.toArray());
		JScrollPane eventListScroll = new JScrollPane(eventList);
		eventListScroll.setBounds(1040, 80, 240, 440);
		eventListScroll.setBorder(BorderFactory.createTitledBorder("Events"));
		contentPane.add(eventListScroll);
		
		eventDetails.setBounds(1040, 600, 240, 150);
		eventDetails.setEditable(false);
		eventDetails.setWrapStyleWord(true);
		eventDetails.setLineWrap(true);
		eventDetails.setBorder(BorderFactory.createTitledBorder("Event Details"));
		contentPane.add(eventDetails);
		
		JPanel eventDetailsButtonPanel = new JPanel();
		eventDetailsButtonPanel.setBounds(1040, 760, 250, 40);
		eventDetailsButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		eventDetailsButtonPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		eventDetailsButtonPanel.setLayout(null);
		contentPane.add(eventDetailsButtonPanel);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.setBounds(0, 0, 110, 40);
		eventDetailsButtonPanel.add(updateBtn);
		updateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int updtEventIndex = eventList.getSelectedIndex();
				Event updtEvent = allEvents.get(eventList.getSelectedIndex());
				updateEventDialog((JFrame) contentPane.getTopLevelAncestor(), eventList, updtEvent, updtEventIndex);
			}
			
		});
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setBounds(130, 0, 110, 40);
		deleteBtn.setEnabled(false);
		eventDetailsButtonPanel.add(deleteBtn);
		deleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				sqliteUtility.deleteEventFromDatabase();
				allEvents.remove(eventList.getSelectedIndex());
				filteredEvents = filterEvents(allEvents, currentDay, currentMonth, currentYear);
				eventDetails.setText("");
				eventList.clearSelection();
				deleteBtn.setEnabled(false);
				updateEventsList(eventList, filteredEvents);
			}
		});
		
		JButton createBtn = new JButton("Create");
		createBtn.setBounds(1040, 540, 240, 35);
		contentPane.add(createBtn);
		createBtn.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createEventDialog((JFrame) contentPane.getTopLevelAncestor(), eventList);
				
			}
		});
		
		eventList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				deleteBtn.setEnabled(true);
				if (null != eventList.getSelectedValue()) {
					eventDetails.setText(((Event) eventList.getSelectedValue()).eventDetails());
				}
			}
		});

		calendarTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (null != calendarTable.getValueAt(calendarTable.rowAtPoint(e.getPoint()), calendarTable.columnAtPoint(e.getPoint()))) {
					currentDay = (int) calendarTable.getValueAt(calendarTable.rowAtPoint(e.getPoint()), calendarTable.columnAtPoint(e.getPoint()));
					filteredEvents = filterEvents(allEvents, currentDay, currentMonth, currentYear);
					updateEventsList(eventList, filteredEvents);
				}
			}
		});

		
	}
	
	public static void createEventDialog(JFrame parent, JList list) {

		JDialog dialog = new JDialog(parent, true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.setOpaque(true);
		
		JLabel eventNameLabel = new JLabel("Event Name: ");
		
		JTextField eventName = new JTextField(30);
		
		JLabel eventDescLabel = new JLabel("Event Description: ");
		
		JTextArea eventDesc = new JTextArea();
		eventDesc.setWrapStyleWord(true);
		
		JLabel eventDateLabel = new JLabel("Event Date:");
		DatePickerSettings eventDatePickerSettings = new DatePickerSettings();
		eventDatePickerSettings.setAllowKeyboardEditing(false);
		DatePicker eventDatePicker = new DatePicker(eventDatePickerSettings);
		
		JLabel eventTimeLabel = new JLabel("Event Time:");
		TimePickerSettings eventTimeSettings = new TimePickerSettings();
		eventTimeSettings.setAllowKeyboardEditing(false);
		TimePicker eventTime = new TimePicker(eventTimeSettings);
		
		JLabel frequencyLabel = new JLabel("How frequently?");
		JComboBox frequencyList = new JComboBox(Event.EventRecurFreq.values());
		frequencyList.setEnabled(false);
		
		JLabel reoccuringLabel = new JLabel("Reoccuring?");
		JRadioButton noReoccurBtn = new JRadioButton("Nope");
		noReoccurBtn.setSelected(true);
		noReoccurBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frequencyList.setEnabled(false);
				
			}
		});
		
		JRadioButton yesReoccurBtn = new JRadioButton("Yep");
		yesReoccurBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frequencyList.setEnabled(true);
				
			}
		});
		
		ButtonGroup reoccurBtnGroup = new ButtonGroup();
		reoccurBtnGroup.add(noReoccurBtn);
		reoccurBtnGroup.add(yesReoccurBtn);
		
		JLabel priorityLabel = new JLabel("Priority (1: Least important, 10: Most Important)");
		JSlider priority = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
		priority.setMajorTickSpacing(1);
		priority.setPaintLabels(true);
		priority.setPaintTicks(true);
		priority.setSnapToTicks(true);
		
		JButton createEventBtn = new JButton("Create Event!");
		createEventBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Event newEvent = new Event();
				
				if (eventName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "Please provide an event name.", "Missing event name", JOptionPane.ERROR_MESSAGE);
				} else if (eventDatePicker.toString().isEmpty()){
					JOptionPane.showMessageDialog(dialog, "Please provide a starting event date.", "Missing event date", JOptionPane.ERROR_MESSAGE);
				} else {
					newEvent.setEventName(eventName.getText());
					newEvent.setEventDesc(eventDesc.getText());
					newEvent.setEventDate(eventDatePicker.getDate().format(dateFormat).toString());
					newEvent.setEventTime(eventTime.getTimeStringOrEmptyString());
					newEvent.setEventPriority(priority.getValue());
					
					if (yesReoccurBtn.isSelected()) {
						newEvent.setEventRecur(true);
						newEvent.setEventFrequency((EventRecurFreq) frequencyList.getSelectedItem());
					}
					
					allEvents.add(newEvent);
					filteredEvents = filterEvents(allEvents, currentDay, currentMonth, currentYear);
//					try {
//						sqliteUtility.writeEventToDatabase(newEvent);
//					} catch (ClassNotFoundException | SQLException e1) {
//						System.out.println("Failed to write new event to database. " + e1.toString());
//					}
					updateEventsList(list, filteredEvents);
					dialog.dispose();
				}

			}
		});
		
		inputPanel.add(eventNameLabel);
		inputPanel.add(eventName);
		inputPanel.add(eventDescLabel);
		inputPanel.add(eventDesc);
		inputPanel.add(eventDateLabel);
		inputPanel.add(eventDatePicker);
		inputPanel.add(eventTimeLabel);
		inputPanel.add(eventTime);
		inputPanel.add(reoccuringLabel);
		inputPanel.add(noReoccurBtn);
		inputPanel.add(yesReoccurBtn);
		inputPanel.add(frequencyLabel);
		inputPanel.add(frequencyList);
		inputPanel.add(priorityLabel);
		inputPanel.add(priority);
		inputPanel.add(createEventBtn);
		
		dialog.setBounds(26, 175, 1100, 400);
		dialog.getContentPane().add(BorderLayout.CENTER, inputPanel);
		dialog.pack();
		dialog.setVisible(true);
		dialog.setResizable(false);
		
	}
	
	public static void updateEventDialog(JFrame parent, JList list, Event eventToUpdate, int eventIndex) {

		JDialog dialog = new JDialog(parent, true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		inputPanel.setOpaque(true);
		
		JLabel eventNameLabel = new JLabel("Event Name: ");
		
		JTextField eventName = new JTextField(30);
		eventName.setText(eventToUpdate.getEventName());
		
		JLabel eventDescLabel = new JLabel("Event Description: ");
		
		JTextArea eventDesc = new JTextArea();
		eventDesc.setWrapStyleWord(true);
		eventDesc.setText(eventToUpdate.getEventDesc());
		
		JLabel eventDateLabel = new JLabel("Event Date:");
		DatePickerSettings eventDatePickerSettings = new DatePickerSettings();
		eventDatePickerSettings.setAllowKeyboardEditing(false);
		DatePicker eventDatePicker = new DatePicker(eventDatePickerSettings);
		eventDatePicker.setDate(LocalDate.parse(eventToUpdate.getEventDate(), dateFormat));
		
		JLabel eventTimeLabel = new JLabel("Event Time:");
		TimePickerSettings eventTimeSettings = new TimePickerSettings();
		eventTimeSettings.setAllowKeyboardEditing(false);
		TimePicker eventTime = new TimePicker(eventTimeSettings);
		eventTime.setText(eventToUpdate.getEventTime());
		
		JLabel frequencyLabel = new JLabel("How frequently?");
		JComboBox frequencyList = new JComboBox(Event.EventRecurFreq.values());
		frequencyList.setEnabled(false);
		frequencyList.setSelectedItem(eventToUpdate.getEventFrequency());
		
		JLabel reoccuringLabel = new JLabel("Reoccuring?");
		JRadioButton noReoccurBtn = new JRadioButton("Nope");
		JRadioButton yesReoccurBtn = new JRadioButton("Yep");
		
		if(eventToUpdate.isEventRecur()){
			yesReoccurBtn.setSelected(true);
		} else {
			noReoccurBtn.setSelected(true);
		}
		
		noReoccurBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frequencyList.setEnabled(false);
				
			}
		});
		
		yesReoccurBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frequencyList.setEnabled(true);
				
			}
		});
		
		ButtonGroup reoccurBtnGroup = new ButtonGroup();
		reoccurBtnGroup.add(noReoccurBtn);
		reoccurBtnGroup.add(yesReoccurBtn);
		
		JLabel priorityLabel = new JLabel("Priority (1: Least important, 10: Most Important)");
		JSlider priority = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
		priority.setMajorTickSpacing(1);
		priority.setPaintLabels(true);
		priority.setPaintTicks(true);
		priority.setSnapToTicks(true);
		
		JButton updateEventBtn = new JButton("Update Event!");
		updateEventBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Event uptEvent = eventToUpdate;
				
				if (eventName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "Please provide an event name.", "Missing event name", JOptionPane.ERROR_MESSAGE);
				} else if (eventDatePicker.toString().isEmpty()){
					JOptionPane.showMessageDialog(dialog, "Please provide a starting event date.", "Missing event date", JOptionPane.ERROR_MESSAGE);
				} else {
					uptEvent.setEventName(eventName.getText());
					uptEvent.setEventDesc(eventDesc.getText());
					uptEvent.setEventDate(eventDatePicker.getDate().format(dateFormat).toString());
					uptEvent.setEventTime(eventTime.getTimeStringOrEmptyString());
					uptEvent.setEventPriority(priority.getValue());
					
					if (yesReoccurBtn.isSelected()) {
						uptEvent.setEventRecur(true);
						uptEvent.setEventFrequency((EventRecurFreq) frequencyList.getSelectedItem());
					}
					
					allEvents.set(eventIndex, eventToUpdate);
					filteredEvents = filterEvents(allEvents, currentDay, currentMonth, currentYear);
					updateEventsList(list, filteredEvents);
					eventDetails.setText("");
					dialog.dispose();
				}

			}
		});
		
		inputPanel.add(eventNameLabel);
		inputPanel.add(eventName);
		inputPanel.add(eventDescLabel);
		inputPanel.add(eventDesc);
		inputPanel.add(eventDateLabel);
		inputPanel.add(eventDatePicker);
		inputPanel.add(eventTimeLabel);
		inputPanel.add(eventTime);
		inputPanel.add(reoccuringLabel);
		inputPanel.add(noReoccurBtn);
		inputPanel.add(yesReoccurBtn);
		inputPanel.add(frequencyLabel);
		inputPanel.add(frequencyList);
		inputPanel.add(priorityLabel);
		inputPanel.add(priority);
		inputPanel.add(updateEventBtn);
		
		dialog.setBounds(26, 175, 1100, 400);
		dialog.getContentPane().add(BorderLayout.CENTER, inputPanel);
		dialog.pack();
		dialog.setVisible(true);
		dialog.setResizable(false);
		
	}
	
	public static void updateEventsList(JList list, ArrayList<Event> events) {
		DefaultListModel tempModel = new DefaultListModel();
		
		for (Event event: events) {
			tempModel.addElement(event);
		}
		
		eventModel = tempModel;
		list.setModel(eventModel);
	}
	
	public static void refreshCalendar(int month, int year) {
		int numOfDays, startOfMonth;
		GregorianCalendar newCalendar = new GregorianCalendar(year, month, 1);
		numOfDays = newCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		startOfMonth = newCalendar.get(GregorianCalendar.DAY_OF_WEEK);
		
		// Clear calendar
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				calendarModel.setValueAt(null, row, col);
			}
		}
		
		// Render calendar
		for (int i = 1; i <= numOfDays; i++) {
			int row = new Integer((i+startOfMonth-2)/7);
			int column = (i+startOfMonth-2) % 7;
			calendarModel.setValueAt(i, row, column);
		}
	}
	
	// If a day is provided, change the table selection to the day passed.
	public static void refreshCalendar(int day, int month, int year) {
		int numOfDays, startOfMonth;
		GregorianCalendar newCalendar = new GregorianCalendar(year, month, 1);
		numOfDays = newCalendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		startOfMonth = newCalendar.get(GregorianCalendar.DAY_OF_WEEK);
		
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				calendarModel.setValueAt(null, row, col);
			}
		}
		
		for (int i = 1; i <= numOfDays; i++) {
			int row = new Integer((i+startOfMonth-2)/7);
			int column = (i+startOfMonth-2) % 7;
			calendarModel.setValueAt(i, row, column);
			if (i == day) {
				calendarTable.changeSelection(row, column, false, false);
			}
		}
	}
	
	public static ArrayList<Event> filterEvents (ArrayList<Event> events, int day, int month, int year) {
		ArrayList<Event> tempEventList = new ArrayList<>();
		String filterDate = Integer.toString(month + 1) + "-" + Integer.toString(day) + "-" + Integer.toString(year);
		for (Event event : events) {
			if (event.getEventDate().equals(filterDate)) {
				tempEventList.add(event);
			}
		}
		
		return tempEventList;
	}
}
