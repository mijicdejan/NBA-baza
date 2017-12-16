import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.FranchiseDAO;
import org.unibl.etf.nba.persistence.model.dao.GameDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

@SuppressWarnings("serial")
public class AddGameForm extends JFrame {

	protected JPanel contentPane;
	
	protected JLabel timeLbl;
	protected JSpinner timeSpin;
	protected JLabel homeTeamLbl;
	protected JComboBox<String> homeTeamCB;
	protected JLabel awayTeamLbl;
	protected JComboBox<String> awayTeamCB;
	protected JCheckBox addScoreBox;
	protected JLabel homeTeamScoreLbl;
	protected JTextField homeTeamScoreTF;
	protected JLabel awayTeamScoreLbl;
	protected JTextField awayTeamScoreTF;
	protected JCheckBox neutralArenaBox;
	protected JLabel neutralArenaLbl;
	protected JComboBox<String> neutralArenaCB;
	protected JButton addArenaBtn;
	protected JButton saveBtn;
	
	protected Date date;
	protected SeasonDTO season;
	
	protected ArrayList<FranchiseDTO> franchises;
	
	protected AddGameFormController addGameFormController;

	/**
	 * Create the frame.
	 */
	public AddGameForm(Date date, SeasonDTO season) {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetAddGameFormOpened();
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
		setBounds(100, 50, 330, 580);
		setBackground(new Color(255, 255, 255));
		setTitle("Add game");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		this.date = date;
		this.season = season;
		
		addGameFormController = new AddGameFormController(this);
		
		initComponents();
		initHomeTeamCBValues();
		initAwayTeamCBValues();
		initCBListeners();
		initCheckBoxListeners();
	}
	
	private void initComponents() {
		timeLbl = new JLabel("Time: ");
		timeLbl.setBounds(10, 10, 300, 25);
		timeLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(timeLbl);
		
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 20, 0);
		SpinnerDateModel sdm = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.HOUR_OF_DAY);
		
		timeSpin = new JSpinner(sdm);
		JSpinner.DateEditor de = new JSpinner.DateEditor(timeSpin, "HH:mm");
		timeSpin.setEditor(de);
		timeSpin.setBounds(10, 35, 100, 30);
		timeSpin.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(timeSpin);
		
		homeTeamLbl = new JLabel("Home team: ");
		homeTeamLbl.setBounds(10, 75, 300, 25);
		homeTeamLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(homeTeamLbl);
		
		homeTeamCB = new JComboBox<>();
		homeTeamCB.setBounds(10, 100, 300, 30);
		homeTeamCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(homeTeamCB);
		
		awayTeamLbl = new JLabel("Away team: ");
		awayTeamLbl.setBounds(10, 140, 300, 25);
		awayTeamLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(awayTeamLbl);
		
		awayTeamCB = new JComboBox<>();
		awayTeamCB.setBounds(10, 165, 300, 30);
		awayTeamCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(awayTeamCB);
		
		addScoreBox = new JCheckBox("Add score");
		addScoreBox.setBounds(10, 205, 300, 25);
		addScoreBox.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addScoreBox.setBackground(new Color(0, 102, 204));
		contentPane.add(addScoreBox);
		
		homeTeamScoreLbl = new JLabel("Home team score: ");
		homeTeamScoreLbl.setBounds(10, 240, 300, 25);
		homeTeamScoreLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(homeTeamScoreLbl);
		
		homeTeamScoreTF = new JTextField();
		homeTeamScoreTF.setBounds(10, 265, 300, 30);
		homeTeamScoreTF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		homeTeamScoreTF.setEnabled(false);
		contentPane.add(homeTeamScoreTF);
		
		awayTeamScoreLbl = new JLabel("Away team score: ");
		awayTeamScoreLbl.setBounds(10, 305, 300, 25);
		awayTeamScoreLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(awayTeamScoreLbl);
		
		awayTeamScoreTF = new JTextField();
		awayTeamScoreTF.setBounds(10, 330, 300, 30);
		awayTeamScoreTF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		awayTeamScoreTF.setEnabled(false);
		contentPane.add(awayTeamScoreTF);
		
		neutralArenaBox = new JCheckBox("Neutral arena");
		neutralArenaBox.setBounds(10, 370, 300, 25);
		neutralArenaBox.setFont(new Font("Century Gothic", Font.BOLD, 18));
		neutralArenaBox.setBackground(new Color(0, 102, 204));
		contentPane.add(neutralArenaBox);
		
		neutralArenaLbl = new JLabel("Neutral arena: ");
		neutralArenaLbl.setBounds(10, 405, 300, 25);
		neutralArenaLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(neutralArenaLbl);
		
		neutralArenaCB = new JComboBox<>();
		neutralArenaCB.setBounds(10, 430, 300, 30);
		neutralArenaCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		neutralArenaCB.setEnabled(false);
		contentPane.add(neutralArenaCB);
		
		addArenaBtn = new JButton("Add arena");
		addArenaBtn.setBounds(180, 465, 130, 25);
		addArenaBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addArenaBtn.setBackground(Color.WHITE);
		addArenaBtn.setEnabled(false);
		contentPane.add(addArenaBtn);
		
		saveBtn = new JButton("Save");
		saveBtn.setBounds(85, 500, 150, 30);
		saveBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		saveBtn.setBackground(Color.WHITE);
		contentPane.add(saveBtn);
	}
	
	private void initHomeTeamCBValues() {
		DAOFactory factory = new MySQLDAOFactory();
		FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
		GameDAO gameDAO = factory.getGameDAO();
		franchises = franchiseDAO.getAllFranchisesInSeason(season);
		for(int i = 0; i < franchises.size(); i++) {
			if(gameDAO.doesTeamHaveMatchOnDate(franchises.get(i), date)) {
				franchises.remove(franchises.get(i));
				i--;
			}
		}
		for(int i = 0; i < franchises.size(); i++) {
			if(gameDAO.getNumberOfHomeGamesForTeamInSeason(franchises.get(i), season) == 41) {
				franchises.remove(franchises.get(i));
				i--;
			}
		}
		for(int i = 0; i < franchises.size(); i++) {
			homeTeamCB.addItem(franchises.get(i).getTeamNames().get(season));
		}
	}
	
	private void initAwayTeamCBValues() {
		awayTeamCB.removeAllItems();
		FranchiseDTO homeFranchise = franchises.get(homeTeamCB.getSelectedIndex());
		DAOFactory factory = new MySQLDAOFactory();
		GameDAO gameDAO = factory.getGameDAO();
		FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
		ArrayList<FranchiseDTO> franchisesAway = franchiseDAO.getAllFranchisesInSeason(season);
		ArrayList<FranchiseDTO> sameDivision = new ArrayList<>();
		ArrayList<FranchiseDTO> sameConference = new ArrayList<>();
		ArrayList<FranchiseDTO> differentConference = new ArrayList<>();
		franchisesAway.remove(homeFranchise);
		for(int i = 0; i < franchisesAway.size(); i++) {
			if(gameDAO.doesTeamHaveMatchOnDate(franchisesAway.get(i), date)) {
				franchisesAway.remove(franchisesAway.get(i));
				i--;
			}
		}
		for(int i = 0; i < franchisesAway.size(); i++) {
			if(homeFranchise.getDivisionId() == franchisesAway.get(i).getDivisionId()) {
				sameDivision.add(franchisesAway.get(i));
			}
		}
		for(int i = 0; i < franchisesAway.size(); i++) {
			if(homeFranchise.getConferenceId() == franchisesAway.get(i).getConferenceId() && homeFranchise.getDivisionId() != franchisesAway.get(i).getDivisionId()) {
				sameConference.add(franchisesAway.get(i));
			}
		}
		for(int i = 0; i < franchisesAway.size(); i++) {
			if(homeFranchise.getConferenceId() == franchisesAway.get(i).getConferenceId()) {
				differentConference.add(franchisesAway.get(i));
			}
		}
		for(int i = 0; i < sameDivision.size(); i++) {
			if(gameDAO.getNumberOfGamesBetweenTeamsInSeason(homeFranchise, sameDivision.get(i), season) == 2) {
				franchisesAway.remove(sameDivision.get(i));
			}
		}
		for(int i = 0; i < sameConference.size(); i++) {
			int n1 = gameDAO.getNumberOfGamesBetweenTeamsInSeason(homeFranchise, sameConference.get(i), season);
			int n2 = gameDAO.getNumberOfGamesBetweenTeamsInSeason(sameConference.get(i), homeFranchise, season);
			if(n1 == 2 || (n1 + n2) == 3) {
				franchisesAway.remove(sameConference.get(i));
			}
		}
		for(int i = 0; i < differentConference.size(); i++) {
			if(gameDAO.getNumberOfGamesBetweenTeamsInSeason(homeFranchise, differentConference.get(i), season) == 1) {
				franchisesAway.remove(differentConference.get(i));
			}
		}
		
		for(int i = 0; i < franchisesAway.size(); i++) {
			awayTeamCB.addItem(franchisesAway.get(i).getTeamNames().get(season));
		}
	}
	
	private void initCBListeners() {
		
		homeTeamCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initAwayTeamCBValues();
			}
		});
		
	}
	
	private void initCheckBoxListeners() {
		
		addScoreBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				addGameFormController.scoreItemStateChanged(e);
			}
		});
		
		neutralArenaBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				addGameFormController.arenaItemStateChanged(e);
			}
		});
		
	}
	
	public JCheckBox getAddScoreBox() {
		return addScoreBox;
	}
	
	public JCheckBox getNeutralArenaBox() {
		return neutralArenaBox;
	}
	
	public void setEnabledScore(boolean b) {
		homeTeamScoreTF.setEnabled(b);
		awayTeamScoreTF.setEnabled(b);
	}
	
	public void setEnabledArena(boolean b) {
		neutralArenaCB.setEnabled(b);
		addArenaBtn.setEnabled(b);
	}

}
