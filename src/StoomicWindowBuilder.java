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
	private JPanel studentPanel;
	private JPanel academicianPanel;
	private JPanel LogInPanel;
	private JPanel KayıtPanel;
	

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
		enterPanel.setVisible(true);
		
		final JPanel academicianPanel = new JPanel();
		frame.getContentPane().add(academicianPanel, "name_1463345066077977000");
		academicianPanel.setLayout(null);
		academicianPanel.setVisible(false);
		
		final JPanel studentPanel = new JPanel();
		frame.getContentPane().add(studentPanel, "name_1463345114900106000");
		studentPanel.setLayout(null);
		studentPanel.setVisible(false);
		
		final JPanel LogInPanel = new JPanel();
		frame.getContentPane().add(LogInPanel, "name_1463376116988117000");
		LogInPanel.setLayout(null);
		LogInPanel.setVisible(false);
		
		final JPanel KayıtPanel = new JPanel();
		frame.getContentPane().add(KayıtPanel, "name_1463390171180866000");
		KayıtPanel.setLayout(null);
		
		JButton btnBack_1 = new JButton("Back");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInPanel.setVisible(false);
				studentPanel.setVisible(true);
			}
		});
		btnBack_1.setBounds(78, 100, 117, 29);
		LogInPanel.add(btnBack_1);
		LogInPanel.setVisible(false);
		
		JButton btnAcademicianEntrance = new JButton("Academician Entrance");
		btnAcademicianEntrance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				academicianPanel.setVisible(true);
				enterPanel.setVisible(false);
			
			}
		});
		btnAcademicianEntrance.setBounds(122, 93, 189, 29);
		enterPanel.add(btnAcademicianEntrance);
		
		JButton btnStudentEntrance = new JButton("Student Entrance");
		btnStudentEntrance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentPanel.setVisible(true);
				enterPanel.setVisible(false);
			}
		});
		btnStudentEntrance.setBounds(126, 160, 189, 29);
		enterPanel.add(btnStudentEntrance);
		
	
		
		JButton btnBackButton = new JButton("Back");
		btnBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterPanel.setVisible(true);
				academicianPanel.setVisible(false);
				
			}
		});
		btnBackButton.setBounds(6, 6, 82, 29);
		academicianPanel.add(btnBackButton);
		
	
		
		JButton btnBackButton_1 = new JButton("Back");
		btnBackButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterPanel.setVisible(true);
				studentPanel.setVisible(false);
			}
		});
		btnBackButton_1.setBounds(6, 6, 75, 29);
		studentPanel.add(btnBackButton_1);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KayıtPanel.setVisible(true);
				studentPanel.setVisible(false);
			}
		});
		btnSignUp.setBounds(165, 173, 117, 29);
		studentPanel.add(btnSignUp);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInPanel.setVisible(true);
				studentPanel.setVisible(false);
			}
		});
		btnLogIn.setBounds(165, 112, 117, 29);
		studentPanel.add(btnLogIn);
		
		
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KayıtPanel.setVisible(false);
				studentPanel.setVisible(true);
			}
		});
		btnBack.setBounds(104, 171, 117, 29);
		KayıtPanel.add(btnBack);
		
	
	}
}
