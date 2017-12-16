import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.PlayerDAO;
import org.unibl.etf.nba.persistence.model.dao.SeasonDAO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

@SuppressWarnings("serial")
public class AddSeasonAwardsForm extends JFrame {

	private JPanel contentPane;
	
	private JLabel seasonLbl;
	private JComboBox<String> seasonCB;
	private JLabel mvpLbl;
	private JComboBox<String> mvpCB;
	private JLabel dpoyLbl;
	private JComboBox<String> dpoyCB;
	private JLabel sixthManLbl;
	private JComboBox<String> sixthManCB;
	private JLabel rotyLbl;
	private JComboBox<String> rotyCB;
	private JLabel mipLbl;
	private JComboBox<String> mipCB;
	private JButton saveBtn;
	
	private HashMap<String, SeasonDTO> seasonsMap;
	private HashMap<String, PlayerDTO> playersMap;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddSeasonAwardsForm frame = new AddSeasonAwardsForm();
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
	public AddSeasonAwardsForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 50, 330, 490);
		setBackground(new Color(255, 255, 255));
		setTitle("Add season awards");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		seasonsMap = new HashMap<>();
		playersMap = new HashMap<>();
		
		initComponents();
		initSeasonCB();
		initAwardsCB();
		initComboBoxListeners();
		initButtonListener();
	}
	
	private void initComponents() {
		seasonLbl = new JLabel("Season: ");
		seasonLbl.setBounds(10, 10, 300, 25);
		seasonLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(seasonLbl);
		
		seasonCB = new JComboBox<>();
		seasonCB.setBounds(10, 35, 300, 30);
		seasonCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		seasonCB.setSelectedItem(200);
		contentPane.add(seasonCB);
		
		mvpLbl = new JLabel("MVP: ");
		mvpLbl.setBounds(10, 75, 300, 25);
		mvpLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(mvpLbl);
		
		mvpCB = new JComboBox<>();
		mvpCB.setBounds(10, 100, 300, 30);
		mvpCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		mvpCB.setSelectedItem(200);
		contentPane.add(mvpCB);
		
		dpoyLbl = new JLabel("Defensive player: ");
		dpoyLbl.setBounds(10, 140, 300, 25);
		dpoyLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(dpoyLbl);
		
		dpoyCB = new JComboBox<>();
		dpoyCB.setBounds(10, 165, 300, 30);
		dpoyCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		dpoyCB.setSelectedItem(200);
		contentPane.add(dpoyCB);
		
		sixthManLbl = new JLabel("Sixth man: ");
		sixthManLbl.setBounds(10, 205, 300, 25);
		sixthManLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(sixthManLbl);
		
		sixthManCB = new JComboBox<>();
		sixthManCB.setBounds(10, 230, 300, 30);
		sixthManCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		sixthManCB.setSelectedItem(200);
		contentPane.add(sixthManCB);
		
		rotyLbl = new JLabel("Rookie of the year: ");
		rotyLbl.setBounds(10, 270, 300, 25);
		rotyLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(rotyLbl);
		
		rotyCB = new JComboBox<>();
		rotyCB.setBounds(10, 295, 300, 30);
		rotyCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		rotyCB.setSelectedItem(200);
		contentPane.add(rotyCB);
		
		mipLbl = new JLabel("MIP: ");
		mipLbl.setBounds(10, 335, 300, 25);
		mipLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(mipLbl);
		
		mipCB = new JComboBox<>();
		mipCB.setBounds(10, 360, 300, 30);
		mipCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		mipCB.setSelectedItem(200);
		contentPane.add(mipCB);
		
		saveBtn = new JButton("Save");
		saveBtn.setBounds(85, 410, 150, 30);
		saveBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		saveBtn.setBackground(Color.WHITE);
		contentPane.add(saveBtn);
	}
	
	private void initSeasonCB() {
		DAOFactory factory = new MySQLDAOFactory();
		SeasonDAO seasonDAO = factory.getSeasonDAO();
		ArrayList<SeasonDTO> seasons = seasonDAO.getAllSeasons();
		
		for(int i = 0; i < seasons.size(); i++) {
			if(seasons.get(i).getMvp() != null) {
				seasons.remove(seasons.get(i));
				i--;
			}
		}
		
		Calendar calendar = new GregorianCalendar();
		
		for(int i = 0; i < seasons.size(); i++) {
			calendar.setTime(seasons.get(i).getStartDate());
			int year = calendar.get(Calendar.YEAR);
			String s = String.valueOf(year);
			s += "/";
			calendar.setTime(seasons.get(i).getEndDate());
			year = calendar.get(Calendar.YEAR);
			s += year;
			seasonCB.addItem(s);
			seasonsMap.put(s, seasons.get(i));
		}
	}
	
	private void initAwardsCB() {
		mvpCB.removeAllItems();
		dpoyCB.removeAllItems();
		sixthManCB.removeAllItems();
		rotyCB.removeAllItems();
		mipCB.removeAllItems();
		DAOFactory factory = new MySQLDAOFactory();
		PlayerDAO playerDAO = factory.getPlayerDAO();
		ArrayList<PlayerDTO> players = playerDAO.getPlayersInSeason(seasonsMap.get(seasonCB.getSelectedItem()));
		ArrayList<PlayerDTO> rookies = playerDAO.getRookies(seasonsMap.get(seasonCB.getSelectedItem()));
		for(int i = 0; i < players.size(); i++) {
			String name = players.get(i).getFirstName() + " " + players.get(i).getLastName();
			mvpCB.addItem(name);
			dpoyCB.addItem(name);
			sixthManCB.addItem(name);
			mipCB.addItem(name);
			playersMap.put(name, players.get(i));
		}
		for(int i = 0; i < rookies.size(); i++) {
			rotyCB.addItem(rookies.get(i).getFirstName() + " " + rookies.get(i).getLastName());
		}
	}
	
	private void initComboBoxListeners() {
		
		seasonCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initAwardsCB();
			}
		});
		
	}
	
	private void initButtonListener() {
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DAOFactory factory = new MySQLDAOFactory();
				SeasonDAO seasonDAO = factory.getSeasonDAO();
				SeasonDTO season = seasonsMap.get(seasonCB.getSelectedItem());
				PlayerDTO mvp = playersMap.get(mvpCB.getSelectedItem());
				PlayerDTO dpoy = playersMap.get(dpoyCB.getSelectedItem());
				PlayerDTO sixthMan = playersMap.get(sixthManCB.getSelectedItem());
				PlayerDTO roty = playersMap.get(rotyCB.getSelectedItem());
				PlayerDTO mip = playersMap.get(mipCB.getSelectedItem());
				season.setMvp(mvp);
				season.setDefensivePlayer(dpoy);
				season.setSixthMan(sixthMan);
				season.setRoty(roty);
				season.setMip(mip);
				if(seasonDAO.updateSeason(season)) {
					JOptionPane.showMessageDialog(AddSeasonAwardsForm.this, "Season awards successfully added.");
					AddSeasonAwardsForm.this.dispose();
					MainFormController.resetAddSeasonAwardsFormOpened();
				}
			}
		});
		
	}

}
