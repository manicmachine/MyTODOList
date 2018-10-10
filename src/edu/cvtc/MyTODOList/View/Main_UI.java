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
		setBounds(100, 100, 1288, 756);
		contentPane = new JPanel();
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setBounds(26, 175, 1100, 400);
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
		table.setRowHeight(100);
		contentPane.add(table);
		
		JPanel ButtonsPanel = new JPanel();
		ButtonsPanel.setBounds(527, 588, 301, 108);
		ButtonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		ButtonsPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		ButtonsPanel.setLayout(null);
		contentPane.add(ButtonsPanel);
		
		JButton DeleteBtn = new JButton("Delete");
		DeleteBtn.setBounds(159, 27, 130, 57);
		ButtonsPanel.add(DeleteBtn);
		
		JButton CreateBtn = new JButton("Create");
		CreateBtn.setBounds(12, 27, 142, 57);
		ButtonsPanel.add(CreateBtn);
		CreateBtn.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createEventDialog(contentPane);
				
			}
		});
		
		JList EventList = new JList(events.toArray());
		EventList.setFont(new Font("Tahoma", Font.PLAIN, 20));
//		EventList.setModel(new AbstractListModel() {
//			String[] values = new String[] {"Event 1", "Event 2", "Event 3", "Church", "Crying", "Online poker"};
//			public int getSize() {
//				return values.length;
//			}
//			public Object getElementAt(int index) {
//				return values[index];
//			}
//		});
		EventList.setBounds(1138, 13, 120, 683);
		contentPane.add(EventList);
		
		JTextPane MonthLbl = new JTextPane();
		MonthLbl.setForeground(Color.BLACK);
		MonthLbl.setBackground(new Color(0, 0, 0, 0)); // Transparent background
		MonthLbl.setFont(new Font("Tahoma", Font.BOLD, 60));
		MonthLbl.setEditable(false);
		MonthLbl.setText(month + "\r\n");
		MonthLbl.setBounds(527, 51, 235, 76);
		MonthLbl.setSize(month.length() * 42, 200); // Adjust pane size according to how long the month's spelling is.
		
		contentPane.add(MonthLbl);
	}
	
	public static void createEventDialog(JPanel parent) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("Create Event");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
						frame.dispose();
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
				
				frame.setBounds(26, 175, 1100, 400);
				frame.getContentPane().add(BorderLayout.CENTER, inputPanel);
				frame.pack();
				frame.setVisible(true);
				frame.setResizable(false);
			}
		});
		
		
	}
	
}
