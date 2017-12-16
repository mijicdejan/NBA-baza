import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
	
	private AddPlayerForm addPlayerForm;
	private AddArenaForm addArenaForm;
	private AddCityFormController addCityFormController;

	/**
	 * Create the frame.
	 */
	public AddCityForm(Object object) {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				AddPlayerFormController.resetAddCityFormOpened();
			}

			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		
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
		
		if(object instanceof AddPlayerForm) {
			addPlayerForm = (AddPlayerForm) object;
			addArenaForm = null;
		} else if(object instanceof AddArenaForm) {
			addPlayerForm = null;
			addArenaForm = (AddArenaForm) object;
		}
		addCityFormController = new AddCityFormController(this);
		
		initComponents();
		initButtonListener();
	}
	
	private void initComponents() {
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
	
	private void initButtonListener() {
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCityFormController.save();
			}
		});
		
	}
	
	public String getName() {
		return nameTF.getText();
	}
	
	public String getStateOfCity() {
		return stateTF.getText();
	}
	
	public String getCountry() {
		return countryTF.getText();
	}
	
	public AddPlayerForm getAddPlayerForm() {
		return addPlayerForm;
	}
	
	public AddArenaForm getAddArenaForm() {
		return addArenaForm;
	}

}
