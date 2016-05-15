import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JPanel;


public class StoomicWindowBuilder {

	private JFrame frame;
	private JPanel enterPanel;
	private JPanel academicianPanel;
	private JPanel studentPanel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StoomicWindowBuilder window = new StoomicWindowBuilder();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StoomicWindowBuilder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		final JPanel enterPanel = new JPanel();
		frame.getContentPane().add(enterPanel, "name_1463345056979110000");
		enterPanel.setLayout(null);
		
		JButton btnAcademicianEntrance = new JButton("Academician entrance");
		btnAcademicianEntrance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				academicianPanel.setVisible(true);
				enterPanel.setVisible(false);
			}
		});
		btnAcademicianEntrance.setBounds(73, 75, 217, 29);
		enterPanel.add(btnAcademicianEntrance);
		
		JButton btnStudentEntrance = new JButton("Student Entrance");
		btnStudentEntrance.setBounds(73, 129, 217, 29);
		enterPanel.add(btnStudentEntrance);
		
		final JPanel academicianPanel = new JPanel();
		frame.getContentPane().add(academicianPanel, "name_1463345066077977000");
		
		JButton btnBackButton = new JButton("Back Button");
		academicianPanel.add(btnBackButton);
		
		final JPanel studentPanel = new JPanel();
		frame.getContentPane().add(studentPanel, "name_1463345114900106000");
		
		JButton btnBackButton_1 = new JButton("Back button");
		studentPanel.add(btnBackButton_1);
	}
}
