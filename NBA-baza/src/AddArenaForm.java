import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.nba.persistence.model.dao.CityDAO;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dto.CityDTO;

@SuppressWarnings("serial")
public class AddArenaForm extends JFrame {

	private JPanel contentPane;
	
	private JLabel nameLbl;
	private JTextField nameTF;
	private JLabel capacityLbl;
	private JSpinner capacitySpin;
	private JLabel cityLbl;
	private JComboBox<String> cityCB;
	private JButton addCityBtn;
	private JButton saveBtn;
	
	private HashMap<String, CityDTO> citiesMap;
	private AddArenaFormController addArenaFormController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddArenaForm frame = new AddArenaForm();
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
	public AddArenaForm() {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetAddArenaFormOpened();
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
		setBounds(100, 50, 330, 320);
		setBackground(new Color(255, 255, 255));
		setTitle("Add arena");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		citiesMap = new HashMap<>();
		addArenaFormController = new AddArenaFormController(this);
		
		initComponents();
		initButtonsListeners();
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
		
		capacityLbl = new JLabel("Capacity: ");
		capacityLbl.setBounds(10, 75, 300, 25);
		capacityLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(capacityLbl);
		
		capacitySpin = new JSpinner(new SpinnerNumberModel(20000, 1000, 80000, 1));
		capacitySpin.setBounds(10, 100, 300, 30);
		capacitySpin.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(capacitySpin);
		
		cityLbl = new JLabel("City: ");
		cityLbl.setBounds(10, 140, 300, 25);
		cityLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(cityLbl);
		
		cityCB = new JComboBox<>();
		cityCB.setBounds(10, 165, 300, 30);
		cityCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(cityCB);
		
		reloadCityCB("");
		
		addCityBtn = new JButton("Add city");
		addCityBtn.setBounds(200, 200, 110, 25);
		addCityBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addCityBtn.setBackground(Color.WHITE);
		contentPane.add(addCityBtn);
		
		saveBtn = new JButton("Save");
		saveBtn.setBounds(85, 240, 150, 30);
		saveBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		saveBtn.setBackground(Color.WHITE);
		contentPane.add(saveBtn);
	}
	
	public void reloadCityCB(String selectedItem) {
		DAOFactory factory = new MySQLDAOFactory();
		CityDAO cityDAO = factory.getCityDAO();
		ArrayList<CityDTO> cities = cityDAO.getAllCities();
		
		for(CityDTO city : cities) {
			cityCB.addItem(city.toString());
			citiesMap.put(city.toString(), city);
		}
		
		cityCB.setSelectedItem(selectedItem);
	}
	
	private void initButtonsListeners() {
		
		addCityBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addArenaFormController.createAddCityForm();
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addArenaFormController.save();
			}
		});
		
	}
	
	public String getName() {
		return nameTF.getText();
	}
	
	public int getCapacity() {
		return (Integer) capacitySpin.getValue();
	}
	
	public CityDTO getCity() {
		return citiesMap.get(cityCB.getSelectedItem());
	}

}
