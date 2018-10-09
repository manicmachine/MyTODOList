package edu.cvtc.MyTODOList.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.Button;
import javax.swing.JSplitPane;
import java.awt.Panel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import edu.cvtc.MyTODOList.model.Event;

import java.awt.Canvas;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import javax.swing.JTextPane;

public class Main_UI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	static LocalDate today;
	static String month;
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
		numOfDays = today.lengthOfMonth();

		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_UI frame = new Main_UI();
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
		setTitle("Your Schedule");
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
		contentPane.add(ButtonsPanel);
		ButtonsPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		ButtonsPanel.setLayout(null);
		
		JButton CancelBtn = new JButton("Cancel");
		CancelBtn.setBounds(12, 27, 142, 57);
		ButtonsPanel.add(CancelBtn);
		
		JButton SubmitBtn = new JButton("Submit");
		SubmitBtn.setBounds(159, 27, 130, 57);
		ButtonsPanel.add(SubmitBtn);
		
		JList EventList = new JList();
		EventList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		EventList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Event 1", "Event 2", "Event 3", "Church", "porn", "Crying", "Online poker"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		EventList.setBounds(1138, 13, 120, 683);
		contentPane.add(EventList);
		
		JTextPane MonthLbl = new JTextPane();
		MonthLbl.setForeground(Color.BLACK);
		MonthLbl.setBackground(new Color(0, 0, 0, 0)); // Transparent background
		MonthLbl.setFont(new Font("Tahoma", Font.BOLD, 60));
		MonthLbl.setEditable(false);
		MonthLbl.setText(month + "\r\n");
		MonthLbl.setBounds(527, 51, 235, 76);
		// Adjust pane size according to how long the month's spelling is.
		MonthLbl.setSize(month.length() * 42, 200);
		
		contentPane.add(MonthLbl);
	}
}
