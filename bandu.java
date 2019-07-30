package sample;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class Watch extends JFrame implements Runnable{

	JLabel IST;
	JLabel EST;
	private JPanel contentPane;
	JTextArea textArea;
	
	Thread t = null;
	int hours = 0, minutes = 0, seconds = 0;
	String timeString = "";
	JTextArea area;
	int d;
	boolean q;
	Robot robot;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Watch frame = new Watch();
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
	public Watch() {
		super("Digital Watch");
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t = new Thread(this);
		t.start();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIst = new JLabel("EST");
		lblIst.setHorizontalAlignment(SwingConstants.CENTER);
		lblIst.setFont(new Font("Sitka Text", Font.BOLD, 20));
		lblIst.setBounds(54, 25, 69, 20);
		contentPane.add(lblIst);
		
		JLabel lblEst = new JLabel("IST");
		lblEst.setHorizontalAlignment(SwingConstants.CENTER);
		lblEst.setFont(new Font("Sitka Text", Font.BOLD, 20));
		lblEst.setBounds(335, 25, 69, 20);
		contentPane.add(lblEst);
		
		 IST = new JLabel("");
		 IST.setHorizontalAlignment(SwingConstants.CENTER);
		IST.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 16));
		IST.setBounds(15, 61, 210, 20);
		contentPane.add(IST);
		
		 EST = new JLabel("");
		 EST.setHorizontalAlignment(SwingConstants.CENTER);
		EST.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 17));
		EST.setBounds(254, 61, 184, 20);
		contentPane.add(EST);
		
		JButton btnNewButton = new JButton("Control");
		btnNewButton.setBounds(177, 185, 115, 29);
		contentPane.add(btnNewButton);
		 
		 scrollPane = new JScrollPane();
		 scrollPane.setBounds(99, 119, 276, 55);
		 contentPane.add(scrollPane);
		
		 textArea =  new JTextArea(1, 3);
		 scrollPane.setViewportView(textArea);
		 textArea.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 18));
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				q=!q;
			}
		});
		d=0;
		q=true;
	}

	@Override
	public void run() {
		
		try {
			while (true) {

				TimeZone.setDefault(TimeZone.getTimeZone("EST"));
				Calendar cal = Calendar.getInstance();
				hours = cal.get(Calendar.HOUR_OF_DAY);
				// if ( hours > 12 ) hours -= 12;
				minutes = cal.get(Calendar.MINUTE);
				seconds = cal.get(Calendar.SECOND);
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				Date date = cal.getTime();
				timeString = formatter.format(date);

				

				printTimeEST();

				TimeZone.setDefault(TimeZone.getTimeZone("IST"));
				cal = Calendar.getInstance();
				hours = cal.get(Calendar.HOUR_OF_DAY);
				// if ( hours > 12 ) hours -= 12;
				minutes = cal.get(Calendar.MINUTE);
				seconds = cal.get(Calendar.SECOND);

				formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				date = cal.getTime();
				timeString = formatter.format(date);

				printTimeIST();
				if (this.isFocused() && q) {
					if (d==0) {
						robot.keyPress(KeyEvent.VK_C);
					}
					if (d==1) {
						robot.keyPress(KeyEvent.VK_H);
					}
					if (d==2) {
						robot.keyPress(KeyEvent.VK_O);
					}
					if (d==3) {
						robot.keyPress(KeyEvent.VK_C);
					}
					if (d==4) {
						robot.keyPress(KeyEvent.VK_O);
						robot.keyPress(KeyEvent.VK_SPACE);
					}
					
				}
				d++;
				if (d==5) {
					d=0;
				}

				// area.setText();
				t.sleep(2000); // interval given in milliseconds
			}
		} catch (Exception e) {
		}
		
	}
	
	public void printTimeEST() {
		IST.setText(timeString);
	}

	public void printTimeIST() {
		EST.setText(timeString);
	}
}
