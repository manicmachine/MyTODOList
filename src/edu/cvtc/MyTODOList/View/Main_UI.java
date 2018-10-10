package edu.cvtc.MyTODOList.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import edu.cvtc.MyTODOList.model.Event;
import edu.cvtc.MyTODOList.model.Event.EventRecurFreq;

import java.awt.Canvas;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.ListModel;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

public class Main_UI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	static LocalDate today;
	static String month;
	static int year;
	static int numOfDays;
	static ArrayList<Event> events = new ArrayList<Event>();
	static DefaultListModel eventModel = new DefaultListModel();
	static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		today = LocalDate.now();
		month = today.getMonth().toString();
		year = today.getYear();
		numOfDays = today.lengthOfMonth();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_UI frame = new Main_UI();
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main_UI() {
		setTitle("MyTODOList - The Stupid Simple Scheduler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1320, 850);
		contentPane = new JPanel();
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setBounds(80, 120, 920, 680);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(null);
		table.setAlignmentX(Component.LEFT_ALIGNMENT);
		table.setAlignmentY(Component.TOP_ALIGNMENT);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, ""},
				{null, null, null, null, null, null, ""},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "Events"
			}
		));
		table.setCellSelectionEnabled(true);
		table.setBackground(Color.LIGHT_GRAY);
		table.setRowHeight(136);
		contentPane.add(table);
		
		JList eventList = new JList(events.toArray());
		eventList.setBounds(1040, 80, 240, 440);
		contentPane.add(eventList);
		
		JTextArea eventDetails = new JTextArea();
		eventDetails.setBounds(1040, 600, 240, 150);
		eventDetails.setEditable(false);
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
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setBounds(130, 0, 110, 40);
		deleteBtn.setEnabled(false);
		eventDetailsButtonPanel.add(deleteBtn);
		deleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				events.remove(eventList.getSelectedIndex());
				eventDetails.setText("");
				eventList.clearSelection();
				deleteBtn.setEnabled(false);
				updateEventsList(eventList);
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
		
		
		JLabel monthLbl = new JLabel(month.toString());
		monthLbl.setBounds(527, 51, 235, 76);
		monthLbl.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		contentPane.add(monthLbl);
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
	
		UtilDateModel model = new UtilDateModel();
		JDatePicker eventDatePicker = new JDatePicker(model);
		
		JLabel eventTimeLabel = new JLabel("Event Time (24h):");
		JTextField eventTime = new JTextField(4);
		
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
				DateModel dateModel = eventDatePicker.getModel();
				int eventMonth = dateModel.getMonth() + 1; // JDatePicker 0-indexes the month so need to add 1
				int eventDay = dateModel.getDay();
				int eventYear = dateModel.getYear();
				LocalDate eventLocalDate = LocalDate.of(eventYear, eventMonth, eventDay);
				
				newEvent.setEventName(eventName.getText());
				newEvent.setEventDesc(eventDesc.getText());
				newEvent.setEventDate(eventLocalDate.format(dateFormat).toString());
				newEvent.setEventTime(eventTime.getText());
				newEvent.setEventPriority(priority.getValue());
				
				if (yesReoccurBtn.isSelected()) {
					newEvent.setEventRecur(true);
					newEvent.setEventFrequency((EventRecurFreq) frequencyList.getSelectedItem());
				}
				
				System.out.println(newEvent.toString());
				events.add(newEvent);
				updateEventsList(list);
				dialog.dispose();
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
	
	private static void updateEventsList(JList list) {
		DefaultListModel tempModel = new DefaultListModel();
		
		for (Event event: events) {
			tempModel.addElement(event);
		}
		
		eventModel = tempModel;
		list.setModel(eventModel);
	}
	
}
