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

public class Main_UI extends JFrame {

	private JPanel contentPane;
	private JTable Week_Calendar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		Week_Calendar = new JTable();
		Week_Calendar.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{"", null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
			}
		));
		Week_Calendar.setColumnSelectionAllowed(true);
		Week_Calendar.setCellSelectionEnabled(true);
		Week_Calendar.setBackground(Color.LIGHT_GRAY);
		contentPane.add(Week_Calendar);
	}

}
