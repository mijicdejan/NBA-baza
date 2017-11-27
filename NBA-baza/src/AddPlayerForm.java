import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.nba.persistence.model.dao.CityDAO;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dto.CityDTO;

import datechooser.beans.DateChooserCombo;
import datechooser.model.multiple.MultyModelBehavior;

@SuppressWarnings("serial")
public class AddPlayerForm extends JFrame {

	protected JPanel contentPane;
	
	protected JLabel firstNameLbl;
	protected JTextField firstNameTF;
	protected JLabel lastNameLbl;
	protected JTextField lastNameTF;
	protected JLabel birthdateLbl;
	protected DateChooserCombo dateChooserCombo;
	protected JLabel heightLbl;
	protected JComboBox<Integer> heightCB;
	protected JLabel weightLbl;
	protected JComboBox<Integer> weightCB;
	protected JLabel birthplaceLbl;
	protected JComboBox<String> birthplaceCB;
	protected JButton addCityBtn;
	protected JButton saveBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddPlayerForm frame = new AddPlayerForm();
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
	public AddPlayerForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 50, 330, 520);
		setBackground(new Color(255, 255, 255));
		setTitle("Add player");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		firstNameLbl = new JLabel("First name: ");
		firstNameLbl.setBounds(10, 10, 300, 25);
		firstNameLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(firstNameLbl);
		
		firstNameTF = new JTextField();
		firstNameTF.setBounds(10, 35, 300, 30);
		firstNameTF.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(firstNameTF);
		
		lastNameLbl = new JLabel("Last name: ");
		lastNameLbl.setBounds(10, 75, 300, 25);
		lastNameLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(lastNameLbl);
		
		lastNameTF = new JTextField();
		lastNameTF.setBounds(10, 100, 300, 30);
		lastNameTF.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(lastNameTF);
		
		birthdateLbl = new JLabel("Birthdate: ");
		birthdateLbl.setBounds(10, 140, 300, 25);
		birthdateLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(birthdateLbl);
		
		dateChooserCombo = new DateChooserCombo();
		dateChooserCombo.setBehavior(MultyModelBehavior.SELECT_SINGLE);
		dateChooserCombo.setBounds(10, 165, 300, 30);
		dateChooserCombo.setFont(new Font("Century Gothic", Font.BOLD, 18));
		dateChooserCombo.setCalendarBackground(Color.WHITE);
		dateChooserCombo.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
		contentPane.add(dateChooserCombo);
		
		heightLbl = new JLabel("Height: ");
		heightLbl.setBounds(10, 205, 300, 25);
		heightLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(heightLbl);
		
		Integer[] heightArr = new Integer[101];
		for(int i = 0; i < 101; i++) {
			heightArr[i] = i + 150;
		}
		
		heightCB = new JComboBox<>(heightArr);
		heightCB.setBounds(10, 230, 300, 30);
		heightCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		heightCB.setSelectedItem(200);
		contentPane.add(heightCB);
		
		weightLbl = new JLabel("Weight: ");
		weightLbl.setBounds(10, 270, 300, 25);
		weightLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(weightLbl);
		
		Integer[] weightArr = new Integer[101];
		for(int i = 0; i < 101; i++) {
			weightArr[i] = i + 50;
		}
		
		weightCB = new JComboBox<>(weightArr);
		weightCB.setBounds(10, 295, 300, 30);
		weightCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		weightCB.setSelectedItem(100);
		contentPane.add(weightCB);
		
		birthplaceLbl = new JLabel("Birthplace: ");
		birthplaceLbl.setBounds(10, 335, 300, 25);
		birthplaceLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(birthplaceLbl);
		
		DAOFactory factory = new MySQLDAOFactory();
		CityDAO cityDAO = factory.getCityDAO();
		ArrayList<CityDTO> cities = cityDAO.getAllCities();
		
		birthplaceCB = new JComboBox<>();
		birthplaceCB.setBounds(10, 360, 300, 30);
		birthplaceCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(birthplaceCB);
		
		for(CityDTO city : cities) {
			birthplaceCB.addItem(city.toString());
		}
		
		addCityBtn = new JButton("Add city");
		addCityBtn.setBounds(200, 395, 110, 25);
		addCityBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addCityBtn.setBackground(Color.WHITE);
		contentPane.add(addCityBtn);
		
		saveBtn = new JButton("Save");
		saveBtn.setBounds(85, 440, 150, 30);
		saveBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		saveBtn.setBackground(Color.WHITE);
		contentPane.add(saveBtn);
	}

}
