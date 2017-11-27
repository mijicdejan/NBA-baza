import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AddCityForm extends JFrame {

	private JPanel contentPane;
	
	private JLabel nameLbl;
	private JTextField nameTF;
	private JLabel stateLbl;
	private JTextField stateTF;
	private JLabel countryLbl;
	private JTextField countryTF;
	private JButton saveBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCityForm frame = new AddCityForm();
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
	public AddCityForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 50, 330, 290);
		setBackground(new Color(255, 255, 255));
		setTitle("Add city");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		nameLbl = new JLabel("Name: ");
		nameLbl.setBounds(10, 10, 300, 25);
		nameLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(nameLbl);
		
		nameTF = new JTextField();
		nameTF.setBounds(10, 35, 300, 30);
		nameTF.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(nameTF);
		
		stateLbl = new JLabel("State: ");
		stateLbl.setBounds(10, 75, 300, 25);
		stateLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(stateLbl);
		
		stateTF = new JTextField();
		stateTF.setBounds(10, 100, 300, 30);
		stateTF.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(stateTF);
		
		countryLbl = new JLabel("Country: ");
		countryLbl.setBounds(10, 140, 300, 25);
		countryLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(countryLbl);
		
		countryTF = new JTextField();
		countryTF.setBounds(10, 165, 300, 30);
		countryTF.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(countryTF);
		
		saveBtn = new JButton("Save");
		saveBtn.setBounds(85, 210, 150, 30);
		saveBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		saveBtn.setBackground(Color.WHITE);
		contentPane.add(saveBtn);
	}

}
