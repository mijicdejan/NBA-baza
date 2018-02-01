package org.unibl.etf.nba.gui.view;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.nba.logic.control.AddGameFormController;
import org.unibl.etf.nba.logic.control.MainFormController;
import org.unibl.etf.nba.logic.control.PlayoffFormController;
import org.unibl.etf.nba.logic.utility.RefereeListModel;
import org.unibl.etf.nba.persistence.model.dao.ArenaDAO;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.FranchiseDAO;
import org.unibl.etf.nba.persistence.model.dao.GameDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.RefereeDAO;
import org.unibl.etf.nba.persistence.model.dto.ArenaDTO;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.RefereeDTO;
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
	
	protected JLabel availableRefereesLbl;
	protected JLabel chosenRefereesLbl;
	protected JList<RefereeDTO> availableRefereesList;
	protected JList<RefereeDTO> chosenRefereesList;
	protected JScrollPane availableScroll;
	protected JScrollPane chosenScroll;
	protected JButton addRefereeBtn;
	protected JButton removeRefereeBtn;
	
	protected Date date;
	protected SeasonDTO season;
	
	protected ArrayList<FranchiseDTO> franchises;
	protected ArrayList<FranchiseDTO> franchisesAway;
	protected ArrayList<ArenaDTO> arenas;
	
	protected AddGameFormController addGameFormController;
	protected MainForm mainForm;

	/**
	 * Create the frame.
	 */
	public AddGameForm(MainForm mainForm) {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				PlayoffFormController.resetAddGameFormOpened();
				MainFormController.resetAddGameFormOpened();
				MainFormController.resetEditGameFormOpened();
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
		setBounds(100, 50, 880, 580);
		setBackground(new Color(255, 255, 255));
		setTitle("Add game");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		this.mainForm = mainForm;
		this.date = mainForm.getSelectedDate();
		this.season = mainForm.getSelectedSeason();
		
		addGameFormController = new AddGameFormController(this);
		
		initComponents();
		initHomeTeamCBValues();
		initAwayTeamCBValues();
		initArenaCBValues();
		initCBListeners();
		initCheckBoxListeners();
		initButtonsListeners();
	}
	
	public AddGameForm(SeasonDTO season, Date date) {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetAddGameFormOpened();
				MainFormController.resetEditGameFormOpened();
				PlayoffFormController.resetAddGameFormOpened();
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
		setBounds(100, 50, 880, 580);
		setBackground(new Color(255, 255, 255));
		setTitle("Add game");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		this.mainForm = null;
		this.date = date;
		this.season = season;
		
		addGameFormController = new AddGameFormController(this);
		
		initComponents();
		initHomeTeamCBValues();
		initAwayTeamCBValues();
		initArenaCBValues();
		initCBListeners();
		initCheckBoxListeners();
		initButtonsListeners();
	}
	
	private void initComponents() {
		timeLbl = new JLabel("Time: ");
		timeLbl.setBounds(10, 10, 300, 25);
		timeLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(timeLbl);
		
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
		
		DAOFactory factory = new MySQLDAOFactory();
		RefereeDAO refereeDAO = factory.getRefereeDAO();
		ArrayList<RefereeDTO> available = refereeDAO.getAllReferees();
		ArrayList<RefereeDTO> unavailable = refereeDAO.getAllUnavailableReferees(date);
		available.removeAll(unavailable);
		
		availableRefereesLbl = new JLabel("Available referees: ");
		availableRefereesLbl.setBounds(340, 55, 200, 25);
		availableRefereesLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(availableRefereesLbl);
		
		availableRefereesList = new JList<>(new RefereeListModel(available));
		availableRefereesList.setFont(new Font("Century Gothic", Font.BOLD, 15));
		availableRefereesList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		availableScroll = new JScrollPane();
		availableScroll.setViewportView(availableRefereesList);
		availableScroll.setBounds(340, 80, 200, 300);
		contentPane.add(availableScroll);
		
		chosenRefereesLbl = new JLabel("Chosen referees: ");
		chosenRefereesLbl.setBounds(640, 55, 200, 25);
		chosenRefereesLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(chosenRefereesLbl);
		
		chosenRefereesList = new JList<>(new RefereeListModel(new ArrayList<>()));
		chosenRefereesList.setFont(new Font("Century Gothic", Font.BOLD, 15));
		chosenRefereesList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		chosenScroll = new JScrollPane();
		chosenScroll.setViewportView(chosenRefereesList);
		chosenScroll.setBounds(640, 80, 200, 300);
		contentPane.add(chosenScroll);
		
		addRefereeBtn = new JButton(">");
		addRefereeBtn.setBounds(560, 140, 60, 60);
		addRefereeBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addRefereeBtn.setBackground(Color.WHITE);
		contentPane.add(addRefereeBtn);
		
		removeRefereeBtn = new JButton("<");
		removeRefereeBtn.setBounds(560, 260, 60, 60);
		removeRefereeBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		removeRefereeBtn.setBackground(Color.WHITE);
		removeRefereeBtn.setEnabled(false);
		contentPane.add(removeRefereeBtn);
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
	
	protected void initAwayTeamCBValues() {
		awayTeamCB.removeAllItems();
		FranchiseDTO homeFranchise = (homeTeamCB.getSelectedIndex() == -1) ? null : franchises.get(homeTeamCB.getSelectedIndex());
		if(homeFranchise != null) {
			DAOFactory factory = new MySQLDAOFactory();
			GameDAO gameDAO = factory.getGameDAO();
			FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
			franchisesAway = franchiseDAO.getAllFranchisesInSeason(season);
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
	}
	
	private void initArenaCBValues() {
		DAOFactory factory = new MySQLDAOFactory();
		ArenaDAO arenaDAO = factory.getArenaDAO();
		arenas = arenaDAO.getAllArenas();
		for(int i = 0; i < arenas.size(); i++) {
			neutralArenaCB.addItem(arenas.get(i).getName());
		}
		neutralArenaCB.addItem("");
		neutralArenaCB.setSelectedItem("");
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
	
	private void initButtonsListeners() {
		
		addArenaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddGameFormController.createAddArenaForm();
			}
		});
		
		addRefereeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!availableRefereesList.isSelectionEmpty()) {
					addGameFormController.addReferee(availableRefereesList, chosenRefereesList);
					availableRefereesList.clearSelection();
					if(chosenRefereesList.getModel().getSize() == 3) {
						addRefereeBtn.setEnabled(false);
					}
					removeRefereeBtn.setEnabled(true);
				}
			}
		});
		
		removeRefereeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!chosenRefereesList.isSelectionEmpty()) {
					addGameFormController.removeReferee(availableRefereesList, chosenRefereesList);
					chosenRefereesList.clearSelection();
					if(chosenRefereesList.getModel().getSize() == 0) {
						removeRefereeBtn.setEnabled(false);
					}
					addRefereeBtn.setEnabled(true);
				}
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addGameFormController.save();
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
		if(b == true) {
			neutralArenaCB.removeItem("");
			neutralArenaCB.setSelectedIndex(0);
		} else {
			neutralArenaCB.addItem("");
			neutralArenaCB.setSelectedItem("");
		}
	}
	
	public FranchiseDTO getHomeTeam() {
		return franchises.get(homeTeamCB.getSelectedIndex());
	}
	
	public FranchiseDTO getAwayTeam() {
		return franchisesAway.get(awayTeamCB.getSelectedIndex());
	}
	
	public Date getGameTime() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime((Date) timeSpin.getModel().getValue());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
		return calendar.getTime();
	}
	
	public ArenaDTO getArena() {
		return arenas.get(neutralArenaCB.getSelectedIndex());
	}
	
	public SeasonDTO getSeason() {
		return season;
	}
	
	public ArrayList<RefereeDTO> getChosenReferees() {
		return ((RefereeListModel) chosenRefereesList.getModel()).getData();
	}
	
	public int getHomeTeamScore() throws NumberFormatException {
		int score = Integer.parseInt(homeTeamScoreTF.getText());
		return score;
	}
	
	public int getAwayTeamScore() throws NumberFormatException {
		int score = Integer.parseInt(awayTeamScoreTF.getText());
		return score;
	}
	
	public MainForm getMainForm() {
		return mainForm;
	}

}
