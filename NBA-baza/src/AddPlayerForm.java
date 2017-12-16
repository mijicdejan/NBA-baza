import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	protected JLabel positionsLbl;
	protected JCheckBox pgBox;
	protected JCheckBox sgBox;
	protected JCheckBox sfBox;
	protected JCheckBox pfBox;
	protected JCheckBox cBox;
	protected JButton saveBtn;
	
	protected HashMap<String, CityDTO> citiesMap;
	protected ArrayList<JCheckBox> checkedList;
	
	protected AddPlayerFormController addPlayerFormController;

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
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetAddPlayerFormOpened();
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
		setBounds(100, 50, 330, 570);
		setBackground(new Color(255, 255, 255));
		setTitle("Add player");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		addPlayerFormController = new AddPlayerFormController(this);
		citiesMap = new HashMap<>();
		checkedList = new ArrayList<>();
		
		initComponents();
		initButtonsListeners();
		initCheckBoxListeners();
	}
	
	private void initComponents() {
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
		
		birthplaceCB = new JComboBox<>();
		birthplaceCB.setBounds(10, 360, 300, 30);
		birthplaceCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(birthplaceCB);
		
		reloadBirthplaceCB("");
		
		addCityBtn = new JButton("Add city");
		addCityBtn.setBounds(200, 395, 110, 25);
		addCityBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addCityBtn.setBackground(Color.WHITE);
		contentPane.add(addCityBtn);
		
		positionsLbl = new JLabel("Positions: ");
		positionsLbl.setBounds(10, 420, 300, 25);
		positionsLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(positionsLbl);
		
		pgBox = new JCheckBox("PG");
		pgBox.setBounds(10, 445, 50, 25);
		pgBox.setFont(new Font("Century Gothic", Font.BOLD, 18));
		pgBox.setBackground(new Color(0, 102, 204));
		contentPane.add(pgBox);
		
		sgBox = new JCheckBox("SG");
		sgBox.setBounds(72, 445, 50, 25);
		sgBox.setFont(new Font("Century Gothic", Font.BOLD, 18));
		sgBox.setBackground(new Color(0, 102, 204));
		contentPane.add(sgBox);
		
		sfBox = new JCheckBox("SF");
		sfBox.setBounds(135, 445, 50, 25);
		sfBox.setFont(new Font("Century Gothic", Font.BOLD, 18));
		sfBox.setBackground(new Color(0, 102, 204));
		contentPane.add(sfBox);
		
		pfBox = new JCheckBox("PF");
		pfBox.setBounds(198, 445, 50, 25);
		pfBox.setFont(new Font("Century Gothic", Font.BOLD, 18));
		pfBox.setBackground(new Color(0, 102, 204));
		contentPane.add(pfBox);
		
		cBox = new JCheckBox("C");
		cBox.setBounds(260, 445, 50, 25);
		cBox.setFont(new Font("Century Gothic", Font.BOLD, 18));
		cBox.setBackground(new Color(0, 102, 204));
		contentPane.add(cBox);
		
		saveBtn = new JButton("Save");
		saveBtn.setBounds(85, 490, 150, 30);
		saveBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		saveBtn.setBackground(Color.WHITE);
		contentPane.add(saveBtn);
	}
	
	private void initButtonsListeners() {
		
		addCityBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addPlayerFormController.createAddCityForm();
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addPlayerFormController.save();
			}
		});
		
	}
	
	private void initCheckBoxListeners() {
		
		pgBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				addPlayerFormController.itemStateChanged(e);
			}
		});
		
		sgBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				addPlayerFormController.itemStateChanged(e);
			}
		});
		
		sfBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				addPlayerFormController.itemStateChanged(e);
			}
		});
		
		pfBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				addPlayerFormController.itemStateChanged(e);
			}
		});
		
		cBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				addPlayerFormController.itemStateChanged(e);
			}
		});
		
	}
	
	public void reloadBirthplaceCB(String selectedItem) {
		DAOFactory factory = new MySQLDAOFactory();
		CityDAO cityDAO = factory.getCityDAO();
		ArrayList<CityDTO> cities = cityDAO.getAllCities();
		
		birthplaceCB.addItem("");
		citiesMap.put("", null);
		for(CityDTO city : cities) {
			birthplaceCB.addItem(city.toString());
			citiesMap.put(city.toString(), city);
		}
		
		birthplaceCB.setSelectedItem(selectedItem);
	}
	
	public ArrayList<JCheckBox> getCheckedList() {
		return checkedList;
	}

	public JTextField getFirstNameTF() {
		return firstNameTF;
	}

	public JTextField getLastNameTF() {
		return lastNameTF;
	}

	public int getHeightCB() {
		return (Integer) heightCB.getSelectedItem();
	}

	public int getWeightCB() {
		return (Integer) weightCB.getSelectedItem();
	}

	public String getBirthplaceCB() {
		return (String) birthplaceCB.getSelectedItem();
	}
	
	public Date getBirthdate() {
		return dateChooserCombo.getSelectedDate().getTime();
	}
	
	public CityDTO getBirthplace() {
		return citiesMap.get(birthplaceCB.getSelectedItem());
	}

}
