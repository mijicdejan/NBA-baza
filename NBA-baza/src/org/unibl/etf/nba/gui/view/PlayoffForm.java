package org.unibl.etf.nba.gui.view;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.nba.logic.control.MainFormController;
import org.unibl.etf.nba.logic.control.PlayoffFormController;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.FranchiseDAO;
import org.unibl.etf.nba.persistence.model.dao.GameDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

@SuppressWarnings("serial")
public class PlayoffForm extends JFrame {

	private JPanel contentPane;
	
	private JLabel roundLbl;
	private JComboBox<String> roundCB;
	private JLabel matchUpLbl;
	private JComboBox<String> matchUpCB;
	private JLabel game1Lbl;
	private JTextField game1TF;
	private JButton addGame1Btn;
	private JLabel game2Lbl;
	private JTextField game2TF;
	private JButton addGame2Btn;
	private JLabel game3Lbl;
	private JTextField game3TF;
	private JButton addGame3Btn;
	private JLabel game4Lbl;
	private JTextField game4TF;
	private JButton addGame4Btn;
	private JLabel game5Lbl;
	private JTextField game5TF;
	private JButton addGame5Btn;
	private JLabel game6Lbl;
	private JTextField game6TF;
	private JButton addGame6Btn;
	private JLabel game7Lbl;
	private JTextField game7TF;
	private JButton addGame7Btn;
	
	private static MainForm mainForm;
	private SeasonDTO season;
	private Date playoffStartDate;
	
	private PlayoffFormController playoffFormController;
	
	private ArrayList<FranchiseDTO[]> matchUpsList;

	/**
	 * Create the frame.
	 */
	public PlayoffForm(MainForm mf) {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetPlayoffFormOpened();
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
		setBounds(100, 50, 510, 635);
		setBackground(new Color(255, 255, 255));
		setTitle("Playoff");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		playoffFormController = new PlayoffFormController(this);
		mainForm = mf;
		this.season = mf.getSelectedSeason();
		this.playoffStartDate = season.getPlayoffStartDate();
		
		initComponents();
		initMatchUpCBValues();
		initButtonsListeners();
		initCBListeners();
	}
	
	private void initComponents() {
		roundLbl = new JLabel("Round: ");
		roundLbl.setBounds(10, 10, 300, 25);
		roundLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(roundLbl);
		
		String[] items = { "First round", "Conference semi-finals", "Conference finals", "NBA finals" };
		roundCB = new JComboBox<>(items);
		roundCB.setBounds(10, 35, 300, 30);
		roundCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(roundCB);
		
		matchUpLbl = new JLabel("Match-up: ");
		matchUpLbl.setBounds(10, 75, 300, 25);
		matchUpLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(matchUpLbl);
		
		matchUpCB = new JComboBox<>();
		matchUpCB.setBounds(10, 100, 480, 30);
		matchUpCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(matchUpCB);
		
		game1Lbl = new JLabel("Game 1: ");
		game1Lbl.setBounds(10, 140, 300, 25);
		game1Lbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(game1Lbl);
		
		game1TF = new JTextField();
		game1TF.setBounds(10, 165, 300, 30);
		game1TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		game1TF.setEnabled(false);
		game1TF.setDisabledTextColor(Color.BLACK);
		contentPane.add(game1TF);
		
		addGame1Btn = new JButton("Add game");
		addGame1Btn.setBounds(320, 165, 170, 30);
		addGame1Btn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addGame1Btn.setBackground(Color.WHITE);
		contentPane.add(addGame1Btn);
		
		game2Lbl = new JLabel("Game 2: ");
		game2Lbl.setBounds(10, 205, 300, 25);
		game2Lbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(game2Lbl);
		
		game2TF = new JTextField();
		game2TF.setBounds(10, 230, 300, 30);
		game2TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		game2TF.setEnabled(false);
		game2TF.setDisabledTextColor(Color.BLACK);
		contentPane.add(game2TF);
		
		addGame2Btn = new JButton("Add game");
		addGame2Btn.setBounds(320, 230, 170, 30);
		addGame2Btn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addGame2Btn.setBackground(Color.WHITE);
		contentPane.add(addGame2Btn);
		
		game3Lbl = new JLabel("Game 3: ");
		game3Lbl.setBounds(10, 270, 300, 25);
		game3Lbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(game3Lbl);
		
		game3TF = new JTextField();
		game3TF.setBounds(10, 295, 300, 30);
		game3TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		game3TF.setEnabled(false);
		game3TF.setDisabledTextColor(Color.BLACK);
		contentPane.add(game3TF);
		
		addGame3Btn = new JButton("Add game");
		addGame3Btn.setBounds(320, 295, 170, 30);
		addGame3Btn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addGame3Btn.setBackground(Color.WHITE);
		contentPane.add(addGame3Btn);
		
		game4Lbl = new JLabel("Game 4: ");
		game4Lbl.setBounds(10, 335, 300, 25);
		game4Lbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(game4Lbl);
		
		game4TF = new JTextField();
		game4TF.setBounds(10, 360, 300, 30);
		game4TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		game4TF.setEnabled(false);
		game4TF.setDisabledTextColor(Color.BLACK);
		contentPane.add(game4TF);
		
		addGame4Btn = new JButton("Add game");
		addGame4Btn.setBounds(320, 360, 170, 30);
		addGame4Btn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addGame4Btn.setBackground(Color.WHITE);
		contentPane.add(addGame4Btn);
		
		game5Lbl = new JLabel("Game 5: ");
		game5Lbl.setBounds(10, 400, 300, 25);
		game5Lbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(game5Lbl);
		
		game5TF = new JTextField();
		game5TF.setBounds(10, 425, 300, 30);
		game5TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		game5TF.setEnabled(false);
		game5TF.setDisabledTextColor(Color.BLACK);
		contentPane.add(game5TF);
		
		addGame5Btn = new JButton("Add game");
		addGame5Btn.setBounds(320, 425, 170, 30);
		addGame5Btn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addGame5Btn.setBackground(Color.WHITE);
		contentPane.add(addGame5Btn);
		
		game6Lbl = new JLabel("Game 6: ");
		game6Lbl.setBounds(10, 465, 300, 25);
		game6Lbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(game6Lbl);
		
		game6TF = new JTextField();
		game6TF.setBounds(10, 490, 300, 30);
		game6TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		game6TF.setEnabled(false);
		game6TF.setDisabledTextColor(Color.BLACK);
		contentPane.add(game6TF);
		
		addGame6Btn = new JButton("Add game");
		addGame6Btn.setBounds(320, 490, 170, 30);
		addGame6Btn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addGame6Btn.setBackground(Color.WHITE);
		contentPane.add(addGame6Btn);
		
		game7Lbl = new JLabel("Game 7: ");
		game7Lbl.setBounds(10, 530, 300, 25);
		game7Lbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(game7Lbl);
		
		game7TF = new JTextField();
		game7TF.setBounds(10, 555, 300, 30);
		game7TF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		game7TF.setEnabled(false);
		game7TF.setDisabledTextColor(Color.BLACK);
		contentPane.add(game7TF);
		
		addGame7Btn = new JButton("Add game");
		addGame7Btn.setBounds(320, 555, 170, 30);
		addGame7Btn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addGame7Btn.setBackground(Color.WHITE);
		contentPane.add(addGame7Btn);
	}
	
	private void initButtonsListeners() {
		
		addGame1Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playoffFormController.addGame1();
			}
		});
		
		addGame2Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playoffFormController.addGame2();
			}
		});
		
		addGame3Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playoffFormController.addGame3();
			}
		});
		
		addGame4Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playoffFormController.addGame4();
			}
		});
		
		addGame5Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playoffFormController.addGame5();
			}
		});
		
		addGame6Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playoffFormController.addGame6();
			}
		});
		
		addGame7Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playoffFormController.addGame7();
			}
		});
		
	}
	
	private void initCBListeners() {
		
		roundCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initMatchUpCBValues();
			}
		});
		
		matchUpCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTFValues();
				setButtonsEnabled();
			}
		});
		
	}
	
	private void initMatchUpCBValues() {
		matchUpCB.removeAllItems();
		if("First round".equals((String) roundCB.getSelectedItem())) {
			matchUpCB.setEnabled(true);
			addGame1Btn.setEnabled(true);;
			addGame2Btn.setEnabled(true);
			addGame3Btn.setEnabled(true);
			addGame4Btn.setEnabled(true);
			addGame5Btn.setEnabled(true);
			addGame6Btn.setEnabled(true);
			addGame7Btn.setEnabled(true);
			
			DAOFactory factory = new MySQLDAOFactory();
			FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
			GameDAO gameDAO = factory.getGameDAO();
			ArrayList<FranchiseDTO> franchises = franchiseDAO.getAllFranchisesFromConference("Eastern");
			Collections.sort(franchises, new Comparator<FranchiseDTO>() {
				@Override
				public int compare(FranchiseDTO o1, FranchiseDTO o2) {
					int o1played = gameDAO.getNumberOfGamesForTeamInSeason(o1, season);
					int o2played = gameDAO.getNumberOfGamesForTeamInSeason(o2, season);
					int o1wins = gameDAO.getNumberOfWinsForTeamInSeason(o1, season);
					int o2wins = gameDAO.getNumberOfWinsForTeamInSeason(o2, season);
					double o1pct = (o1played == 0) ? 0 : (double) o1wins / o1played;
					double o2pct = (o2played == 0) ? 0 : (double) o2wins / o2played;
					if(o1pct > o2pct) {
						return -1;
					} else if(o1pct < o2pct) {
						return 1;
					}
					return 0;
				}
			});
			matchUpsList = new ArrayList<>();
			FranchiseDTO[] matchUp1 = { franchises.get(0), franchises.get(7) };
			matchUpsList.add(matchUp1);
			FranchiseDTO[] matchUp2 = { franchises.get(1), franchises.get(6) };
			matchUpsList.add(matchUp2);
			FranchiseDTO[] matchUp3 = { franchises.get(2), franchises.get(5) };
			matchUpsList.add(matchUp3);
			FranchiseDTO[] matchUp4 = { franchises.get(3), franchises.get(4) };
			matchUpsList.add(matchUp4);
			matchUpCB.addItem(franchises.get(0).getTeamNames().get(season) + " vs " + franchises.get(7).getTeamNames().get(season));
			matchUpCB.addItem(franchises.get(1).getTeamNames().get(season) + " vs " + franchises.get(6).getTeamNames().get(season));
			matchUpCB.addItem(franchises.get(2).getTeamNames().get(season) + " vs " + franchises.get(5).getTeamNames().get(season));
			matchUpCB.addItem(franchises.get(3).getTeamNames().get(season) + " vs " + franchises.get(4).getTeamNames().get(season));
		
			franchises = franchiseDAO.getAllFranchisesFromConference("Western");
			Collections.sort(franchises, new Comparator<FranchiseDTO>() {
				@Override
				public int compare(FranchiseDTO o1, FranchiseDTO o2) {
					int o1played = gameDAO.getNumberOfGamesForTeamInSeason(o1, season);
					int o2played = gameDAO.getNumberOfGamesForTeamInSeason(o2, season);
					int o1wins = gameDAO.getNumberOfWinsForTeamInSeason(o1, season);
					int o2wins = gameDAO.getNumberOfWinsForTeamInSeason(o2, season);
					double o1pct = (o1played == 0) ? 0 : (double) o1wins / o1played;
					double o2pct = (o2played == 0) ? 0 : (double) o2wins / o2played;
					if(o1pct > o2pct) {
						return -1;
					} else if(o1pct < o2pct) {
						return 1;
					}
					return 0;
				}
			});
			FranchiseDTO[] matchUp5 = { franchises.get(0), franchises.get(7) };
			matchUpsList.add(matchUp5);
			FranchiseDTO[] matchUp6 = { franchises.get(1), franchises.get(6) };
			matchUpsList.add(matchUp6);
			FranchiseDTO[] matchUp7 = { franchises.get(2), franchises.get(5) };
			matchUpsList.add(matchUp7);
			FranchiseDTO[] matchUp8 = { franchises.get(3), franchises.get(4) };
			matchUpsList.add(matchUp8);
			matchUpCB.addItem(franchises.get(0).getTeamNames().get(season) + " vs " + franchises.get(7).getTeamNames().get(season));
			matchUpCB.addItem(franchises.get(1).getTeamNames().get(season) + " vs " + franchises.get(6).getTeamNames().get(season));
			matchUpCB.addItem(franchises.get(2).getTeamNames().get(season) + " vs " + franchises.get(5).getTeamNames().get(season));
			matchUpCB.addItem(franchises.get(3).getTeamNames().get(season) + " vs " + franchises.get(4).getTeamNames().get(season));
			setTFValues();
			setButtonsEnabled();
		} else if("Conference semi-finals".equals((String) roundCB.getSelectedItem())) {
			matchUpCB.setEnabled(true);
			DAOFactory factory = new MySQLDAOFactory();
			FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
			GameDAO gameDAO = factory.getGameDAO();
			
			ArrayList<FranchiseDTO> allFranchises = franchiseDAO.getAllFranchisesInSeason(season);
			int n = 0;
			for(int i = 0; i < allFranchises.size(); i++) {
				if(gameDAO.getNumberOfWinsForTeamInPlayoff(allFranchises.get(i), season) >= 4) {
					n++;
				}
			}
			
			if(n == 8) {
				ArrayList<FranchiseDTO> franchises = franchiseDAO.getAllFranchisesFromConference("Eastern");
				Collections.sort(franchises, new Comparator<FranchiseDTO>() {
					@Override
					public int compare(FranchiseDTO o1, FranchiseDTO o2) {
						int o1played = gameDAO.getNumberOfGamesForTeamInSeason(o1, season);
						int o2played = gameDAO.getNumberOfGamesForTeamInSeason(o2, season);
						int o1wins = gameDAO.getNumberOfWinsForTeamInSeason(o1, season);
						int o2wins = gameDAO.getNumberOfWinsForTeamInSeason(o2, season);
						double o1pct = (o1played == 0) ? 0 : (double) o1wins / o1played;
						double o2pct = (o2played == 0) ? 0 : (double) o2wins / o2played;
						if(o1pct > o2pct) {
							return -1;
						} else if(o1pct < o2pct) {
							return 1;
						}
						return 0;
					}
				});
				matchUpsList = new ArrayList<>();
				FranchiseDTO team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(0), franchises.get(7), season);
				FranchiseDTO team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(3), franchises.get(4), season);
				FranchiseDTO[] matchUp1 = new FranchiseDTO[2];
				if(gameDAO.getNumberOfWinsForTeamInSeason(team1, season) >= gameDAO.getNumberOfWinsForTeamInSeason(team2, season)) {
					matchUp1[0] = team1;
					matchUp1[1] = team2;
				} else {
					matchUp1[0] = team2;
					matchUp1[1] = team1;
				}
				matchUpsList.add(matchUp1);
				team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(1), franchises.get(6), season);
				team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(2), franchises.get(5), season);
				matchUp1 = new FranchiseDTO[2];
				if(gameDAO.getNumberOfWinsForTeamInSeason(team1, season) >= gameDAO.getNumberOfWinsForTeamInSeason(team2, season)) {
					matchUp1[0] = team1;
					matchUp1[1] = team2;
				} else {
					matchUp1[0] = team2;
					matchUp1[1] = team1;
				}
				matchUpsList.add(matchUp1);
				matchUpCB.addItem(matchUpsList.get(0)[0].getTeamNames().get(season) + " vs " + matchUpsList.get(0)[1].getTeamNames().get(season));
				matchUpCB.addItem(matchUpsList.get(1)[0].getTeamNames().get(season) + " vs " + matchUpsList.get(1)[1].getTeamNames().get(season));
				
				franchises = franchiseDAO.getAllFranchisesFromConference("Western");
				Collections.sort(franchises, new Comparator<FranchiseDTO>() {
					@Override
					public int compare(FranchiseDTO o1, FranchiseDTO o2) {
						int o1played = gameDAO.getNumberOfGamesForTeamInSeason(o1, season);
						int o2played = gameDAO.getNumberOfGamesForTeamInSeason(o2, season);
						int o1wins = gameDAO.getNumberOfWinsForTeamInSeason(o1, season);
						int o2wins = gameDAO.getNumberOfWinsForTeamInSeason(o2, season);
						double o1pct = (o1played == 0) ? 0 : (double) o1wins / o1played;
						double o2pct = (o2played == 0) ? 0 : (double) o2wins / o2played;
						if(o1pct > o2pct) {
							return -1;
						} else if(o1pct < o2pct) {
							return 1;
						}
						return 0;
					}
				});
				team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(0), franchises.get(7), season);
				team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(3), franchises.get(4), season);
				matchUp1 = new FranchiseDTO[2];
				if(gameDAO.getNumberOfWinsForTeamInSeason(team1, season) >= gameDAO.getNumberOfWinsForTeamInSeason(team2, season)) {
					matchUp1[0] = team1;
					matchUp1[1] = team2;
				} else {
					matchUp1[0] = team2;
					matchUp1[1] = team1;
				}
				matchUpsList.add(matchUp1);
				team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(1), franchises.get(6), season);
				team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(2), franchises.get(5), season);
				matchUp1 = new FranchiseDTO[2];
				if(gameDAO.getNumberOfWinsForTeamInSeason(team1, season) >= gameDAO.getNumberOfWinsForTeamInSeason(team2, season)) {
					matchUp1[0] = team1;
					matchUp1[1] = team2;
				} else {
					matchUp1[0] = team2;
					matchUp1[1] = team1;
				}
				matchUpsList.add(matchUp1);
				matchUpCB.addItem(matchUpsList.get(2)[0].getTeamNames().get(season) + " vs " + matchUpsList.get(2)[1].getTeamNames().get(season));
				matchUpCB.addItem(matchUpsList.get(3)[0].getTeamNames().get(season) + " vs " + matchUpsList.get(3)[1].getTeamNames().get(season));
				setTFValues();
				setButtonsEnabled();
			} else {
				matchUpCB.setEnabled(false);
				game1TF.setEnabled(false);
				game1TF.setText("");
				addGame1Btn.setEnabled(false);;
				game2TF.setEnabled(false);
				game2TF.setText("");
				addGame2Btn.setEnabled(false);
				game3TF.setEnabled(false);
				game3TF.setText("");
				addGame3Btn.setEnabled(false);
				game4TF.setEnabled(false);
				game4TF.setText("");
				addGame4Btn.setEnabled(false);
				game5TF.setEnabled(false);
				game5TF.setText("");
				addGame5Btn.setEnabled(false);
				game6TF.setEnabled(false);
				game6TF.setText("");
				addGame6Btn.setEnabled(false);
				game7TF.setEnabled(false);
				game7TF.setText("");
				addGame7Btn.setEnabled(false);
			}
		} else if("Conference finals".equals((String) roundCB.getSelectedItem())) {
			matchUpCB.setEnabled(true);
			DAOFactory factory = new MySQLDAOFactory();
			FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
			GameDAO gameDAO = factory.getGameDAO();
			
			ArrayList<FranchiseDTO> allFranchises = franchiseDAO.getAllFranchisesInSeason(season);
			int n = 0;
			for(int i = 0; i < allFranchises.size(); i++) {
				if(gameDAO.getNumberOfWinsForTeamInPlayoff(allFranchises.get(i), season) >= 8) {
					n++;
				}
			}
			
			if(n == 4) {
				ArrayList<FranchiseDTO> franchises = franchiseDAO.getAllFranchisesFromConference("Eastern");
				Collections.sort(franchises, new Comparator<FranchiseDTO>() {
					@Override
					public int compare(FranchiseDTO o1, FranchiseDTO o2) {
						int o1played = gameDAO.getNumberOfGamesForTeamInSeason(o1, season);
						int o2played = gameDAO.getNumberOfGamesForTeamInSeason(o2, season);
						int o1wins = gameDAO.getNumberOfWinsForTeamInSeason(o1, season);
						int o2wins = gameDAO.getNumberOfWinsForTeamInSeason(o2, season);
						double o1pct = (o1played == 0) ? 0 : (double) o1wins / o1played;
						double o2pct = (o2played == 0) ? 0 : (double) o2wins / o2played;
						if(o1pct > o2pct) {
							return -1;
						} else if(o1pct < o2pct) {
							return 1;
						}
						return 0;
					}
				});
				matchUpsList = new ArrayList<>();
				FranchiseDTO team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(0), franchises.get(7), season);
				FranchiseDTO team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(3), franchises.get(4), season);
				FranchiseDTO team3 = gameDAO.getWinnerOfPlayoffSeries(team1, team2, season);
				team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(1), franchises.get(6), season);
				team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(2), franchises.get(5), season);
				FranchiseDTO team4 = gameDAO.getWinnerOfPlayoffSeries(team1, team2, season);
				FranchiseDTO[] matchUp1 = new FranchiseDTO[2];
				if(gameDAO.getNumberOfWinsForTeamInSeason(team3, season) >= gameDAO.getNumberOfWinsForTeamInSeason(team4, season)) {
					matchUp1[0] = team3;
					matchUp1[1] = team4;
				} else {
					matchUp1[0] = team3;
					matchUp1[1] = team4;
				}
				matchUpsList.add(matchUp1);
				matchUpCB.addItem(matchUpsList.get(0)[0].getTeamNames().get(season) + " vs " + matchUpsList.get(0)[1].getTeamNames().get(season));
							
				franchises = franchiseDAO.getAllFranchisesFromConference("Western");
				Collections.sort(franchises, new Comparator<FranchiseDTO>() {
					@Override
					public int compare(FranchiseDTO o1, FranchiseDTO o2) {
						int o1played = gameDAO.getNumberOfGamesForTeamInSeason(o1, season);
						int o2played = gameDAO.getNumberOfGamesForTeamInSeason(o2, season);
						int o1wins = gameDAO.getNumberOfWinsForTeamInSeason(o1, season);
						int o2wins = gameDAO.getNumberOfWinsForTeamInSeason(o2, season);
						double o1pct = (o1played == 0) ? 0 : (double) o1wins / o1played;
						double o2pct = (o2played == 0) ? 0 : (double) o2wins / o2played;
						if(o1pct > o2pct) {
							return -1;
						} else if(o1pct < o2pct) {
							return 1;
						}
						return 0;
					}
				});
				team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(0), franchises.get(7), season);
				team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(3), franchises.get(4), season);
				team3 = gameDAO.getWinnerOfPlayoffSeries(team1, team2, season);
				team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(1), franchises.get(6), season);
				team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(2), franchises.get(5), season);
				team4 = gameDAO.getWinnerOfPlayoffSeries(team1, team2, season);
				matchUp1 = new FranchiseDTO[2];
				if(gameDAO.getNumberOfWinsForTeamInSeason(team3, season) >= gameDAO.getNumberOfWinsForTeamInSeason(team4, season)) {
					matchUp1[0] = team3;
					matchUp1[1] = team4;
				} else {
					matchUp1[0] = team3;
					matchUp1[1] = team4;
				}
				matchUpsList.add(matchUp1);
				matchUpCB.addItem(matchUpsList.get(1)[0].getTeamNames().get(season) + " vs " + matchUpsList.get(1)[1].getTeamNames().get(season));
				setTFValues();
				setButtonsEnabled();
			} else {
				matchUpCB.setEnabled(false);
				game1TF.setEnabled(false);
				game1TF.setText("");
				addGame1Btn.setEnabled(false);;
				game2TF.setEnabled(false);
				game2TF.setText("");
				addGame2Btn.setEnabled(false);
				game3TF.setEnabled(false);
				game3TF.setText("");
				addGame3Btn.setEnabled(false);
				game4TF.setEnabled(false);
				game4TF.setText("");
				addGame4Btn.setEnabled(false);
				game5TF.setEnabled(false);
				game5TF.setText("");
				addGame5Btn.setEnabled(false);
				game6TF.setEnabled(false);
				game6TF.setText("");
				addGame6Btn.setEnabled(false);
				game7TF.setEnabled(false);
				game7TF.setText("");
				addGame7Btn.setEnabled(false);
			}
		} else {
			matchUpCB.setEnabled(true);
			DAOFactory factory = new MySQLDAOFactory();
			FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
			GameDAO gameDAO = factory.getGameDAO();
			
			ArrayList<FranchiseDTO> allFranchises = franchiseDAO.getAllFranchisesInSeason(season);
			int n = 0;
			for(int i = 0; i < allFranchises.size(); i++) {
				if(gameDAO.getNumberOfWinsForTeamInPlayoff(allFranchises.get(i), season) >= 12) {
					n++;
				}
			}
			
			if(n == 2) {
				ArrayList<FranchiseDTO> franchises = franchiseDAO.getAllFranchisesFromConference("Eastern");
				Collections.sort(franchises, new Comparator<FranchiseDTO>() {
					@Override
					public int compare(FranchiseDTO o1, FranchiseDTO o2) {
						int o1played = gameDAO.getNumberOfGamesForTeamInSeason(o1, season);
						int o2played = gameDAO.getNumberOfGamesForTeamInSeason(o2, season);
						int o1wins = gameDAO.getNumberOfWinsForTeamInSeason(o1, season);
						int o2wins = gameDAO.getNumberOfWinsForTeamInSeason(o2, season);
						double o1pct = (o1played == 0) ? 0 : (double) o1wins / o1played;
						double o2pct = (o2played == 0) ? 0 : (double) o2wins / o2played;
						if(o1pct > o2pct) {
							return -1;
						} else if(o1pct < o2pct) {
							return 1;
						}
						return 0;
					}
				});
				matchUpsList = new ArrayList<>();
				FranchiseDTO team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(0), franchises.get(7), season);
				FranchiseDTO team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(3), franchises.get(4), season);
				FranchiseDTO team3 = gameDAO.getWinnerOfPlayoffSeries(team1, team2, season);
				team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(1), franchises.get(6), season);
				team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(2), franchises.get(5), season);
				FranchiseDTO team4 = gameDAO.getWinnerOfPlayoffSeries(team1, team2, season);
				FranchiseDTO team5 = gameDAO.getWinnerOfPlayoffSeries(team3, team4, season);
				
				franchises = franchiseDAO.getAllFranchisesFromConference("Western");
				Collections.sort(franchises, new Comparator<FranchiseDTO>() {
					@Override
					public int compare(FranchiseDTO o1, FranchiseDTO o2) {
						int o1played = gameDAO.getNumberOfGamesForTeamInSeason(o1, season);
						int o2played = gameDAO.getNumberOfGamesForTeamInSeason(o2, season);
						int o1wins = gameDAO.getNumberOfWinsForTeamInSeason(o1, season);
						int o2wins = gameDAO.getNumberOfWinsForTeamInSeason(o2, season);
						double o1pct = (o1played == 0) ? 0 : (double) o1wins / o1played;
						double o2pct = (o2played == 0) ? 0 : (double) o2wins / o2played;
						if(o1pct > o2pct) {
							return -1;
						} else if(o1pct < o2pct) {
							return 1;
						}
						return 0;
					}
				});
				team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(0), franchises.get(7), season);
				team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(3), franchises.get(4), season);
				team3 = gameDAO.getWinnerOfPlayoffSeries(team1, team2, season);
				team1 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(1), franchises.get(6), season);
				team2 = gameDAO.getWinnerOfPlayoffSeries(franchises.get(2), franchises.get(5), season);
				team4 = gameDAO.getWinnerOfPlayoffSeries(team1, team2, season);
				FranchiseDTO team6 = gameDAO.getWinnerOfPlayoffSeries(team3, team4, season);
				FranchiseDTO[] matchUp1 = new FranchiseDTO[2];
				if(gameDAO.getNumberOfWinsForTeamInSeason(team5, season) >= gameDAO.getNumberOfWinsForTeamInSeason(team6, season)) {
					matchUp1[0] = team5;
					matchUp1[1] = team6;
				} else {
					matchUp1[0] = team6;
					matchUp1[1] = team5;
				}
				matchUpsList.add(matchUp1);
				matchUpCB.addItem(matchUpsList.get(0)[0].getTeamNames().get(season) + " vs " + matchUpsList.get(0)[1].getTeamNames().get(season));
				setTFValues();
				setButtonsEnabled();
			} else {
				matchUpCB.setEnabled(false);
				game1TF.setEnabled(false);
				game1TF.setText("");
				addGame1Btn.setEnabled(false);;
				game2TF.setEnabled(false);
				game2TF.setText("");
				addGame2Btn.setEnabled(false);
				game3TF.setEnabled(false);
				game3TF.setText("");
				addGame3Btn.setEnabled(false);
				game4TF.setEnabled(false);
				game4TF.setText("");
				addGame4Btn.setEnabled(false);
				game5TF.setEnabled(false);
				game5TF.setText("");
				addGame5Btn.setEnabled(false);
				game6TF.setEnabled(false);
				game6TF.setText("");
				addGame6Btn.setEnabled(false);
				game7TF.setEnabled(false);
				game7TF.setText("");
				addGame7Btn.setEnabled(false);
			}
		}
	}
	
	public void setButtonsEnabled() {
		FranchiseDTO seededTeam = getSeededTeam();
		FranchiseDTO unseededTeam = getUnseededTeam();
		if(seededTeam != null && unseededTeam != null) {
			DAOFactory factory = new MySQLDAOFactory();
			GameDAO gameDAO = factory.getGameDAO();
			int gamesFinished = gameDAO.getNumberOfFinishedGamesInPlayoffSeries(seededTeam, unseededTeam, season);
			int seededTeamWins = gameDAO.getNumberOfWinsForTeamInPlayoffSeries(seededTeam, unseededTeam, season);
			int unseededTeamWins = gamesFinished - seededTeamWins;
			if(seededTeamWins == 4 || unseededTeamWins == 4) {
				addGame1Btn.setEnabled(false);
				addGame2Btn.setEnabled(false);
				addGame3Btn.setEnabled(false);
				addGame4Btn.setEnabled(false);
				addGame5Btn.setEnabled(false);
				addGame6Btn.setEnabled(false);
				addGame7Btn.setEnabled(false);
			} else if(gamesFinished == 0) {
				addGame1Btn.setEnabled(true);
				addGame2Btn.setEnabled(false);
				addGame3Btn.setEnabled(false);
				addGame4Btn.setEnabled(false);
				addGame5Btn.setEnabled(false);
				addGame6Btn.setEnabled(false);
				addGame7Btn.setEnabled(false);
			} else if(gamesFinished == 1) {
				addGame1Btn.setEnabled(false);
				addGame2Btn.setEnabled(true);
				addGame3Btn.setEnabled(false);
				addGame4Btn.setEnabled(false);
				addGame5Btn.setEnabled(false);
				addGame6Btn.setEnabled(false);
				addGame7Btn.setEnabled(false);
			} else if(gamesFinished == 2) {
				addGame1Btn.setEnabled(false);
				addGame2Btn.setEnabled(false);
				addGame3Btn.setEnabled(true);
				addGame4Btn.setEnabled(false);
				addGame5Btn.setEnabled(false);
				addGame6Btn.setEnabled(false);
				addGame7Btn.setEnabled(false);
			} else if(gamesFinished == 3) {
				addGame1Btn.setEnabled(false);
				addGame2Btn.setEnabled(false);
				addGame3Btn.setEnabled(false);
				addGame4Btn.setEnabled(true);
				addGame5Btn.setEnabled(false);
				addGame6Btn.setEnabled(false);
				addGame7Btn.setEnabled(false);
			} else if(gamesFinished == 4) {
				addGame1Btn.setEnabled(false);
				addGame2Btn.setEnabled(false);
				addGame3Btn.setEnabled(false);
				addGame4Btn.setEnabled(false);
				addGame5Btn.setEnabled(true);
				addGame6Btn.setEnabled(false);
				addGame7Btn.setEnabled(false);
			} else if(gamesFinished == 5) {
				addGame1Btn.setEnabled(false);
				addGame2Btn.setEnabled(false);
				addGame3Btn.setEnabled(false);
				addGame4Btn.setEnabled(false);
				addGame5Btn.setEnabled(false);
				addGame6Btn.setEnabled(true);
				addGame7Btn.setEnabled(false);
			} else if(gamesFinished == 6) {
				addGame1Btn.setEnabled(false);
				addGame2Btn.setEnabled(false);
				addGame3Btn.setEnabled(false);
				addGame4Btn.setEnabled(false);
				addGame5Btn.setEnabled(false);
				addGame6Btn.setEnabled(false);
				addGame7Btn.setEnabled(true);
			}
		}
	}
	
	public void setTFValues() {
		FranchiseDTO seededTeam = getSeededTeam();
		FranchiseDTO unseededTeam = getUnseededTeam();
		if(seededTeam != null & unseededTeam != null) {
			DAOFactory factory = new MySQLDAOFactory();
			GameDAO gameDAO = factory.getGameDAO();
			Date date = playoffFormController.getGameDate(1, getSelectedRound());
			if(gameDAO.checkForGame(seededTeam, unseededTeam, date)) {
				int[] result = gameDAO.getGameResult(seededTeam, unseededTeam, date);
				game1TF.setText(seededTeam.getFranchiseAbrv() + " " + result[0] + " - " + result[1] + " " + unseededTeam.getFranchiseAbrv());
				addGame1Btn.setEnabled(false);
			} else {
				game1TF.setText("");
				addGame1Btn.setEnabled(true);
			}
			date = playoffFormController.getGameDate(2, getSelectedRound());
			if(gameDAO.checkForGame(seededTeam, unseededTeam, date)) {
				int[] result = gameDAO.getGameResult(seededTeam, unseededTeam, date);
				game2TF.setText(seededTeam.getFranchiseAbrv() + " " + result[0] + " - " + result[1] + " " + unseededTeam.getFranchiseAbrv());
				addGame2Btn.setEnabled(false);
			} else {
				game2TF.setText("");
				addGame2Btn.setEnabled(true);
			}
			date = playoffFormController.getGameDate(3, getSelectedRound());
			if(gameDAO.checkForGame(unseededTeam, seededTeam, date)) {
				int[] result = gameDAO.getGameResult(unseededTeam, seededTeam, date);
				game3TF.setText(unseededTeam.getFranchiseAbrv() + " " + result[0] + " - " + result[1] + " " + seededTeam.getFranchiseAbrv());
				addGame3Btn.setEnabled(false);
			} else {
				game3TF.setText("");
				addGame3Btn.setEnabled(true);
			}
			date = playoffFormController.getGameDate(4, getSelectedRound());
			if(gameDAO.checkForGame(unseededTeam, seededTeam, date)) {
				int[] result = gameDAO.getGameResult(unseededTeam, seededTeam, date);
				game4TF.setText(unseededTeam.getFranchiseAbrv() + " " + result[0] + " - " + result[1] + " " + seededTeam.getFranchiseAbrv());
				addGame4Btn.setEnabled(false);
			} else {
				game4TF.setText("");
				addGame4Btn.setEnabled(true);
			}
			date = playoffFormController.getGameDate(5, getSelectedRound());
			if(gameDAO.checkForGame(seededTeam, unseededTeam, date)) {
				int[] result = gameDAO.getGameResult(seededTeam, unseededTeam, date);
				game5TF.setText(seededTeam.getFranchiseAbrv() + " " + result[0] + " - " + result[1] + " " + unseededTeam.getFranchiseAbrv());
				addGame5Btn.setEnabled(false);
			} else {
				game5TF.setText("");
				addGame5Btn.setEnabled(true);
			}
			date = playoffFormController.getGameDate(6, getSelectedRound());
			if(gameDAO.checkForGame(unseededTeam, seededTeam, date)) {
				int[] result = gameDAO.getGameResult(unseededTeam, seededTeam, date);
				game6TF.setText(unseededTeam.getFranchiseAbrv() + " " + result[0] + " - " + result[1] + " " + seededTeam.getFranchiseAbrv());
				addGame6Btn.setEnabled(false);
			} else {
				game6TF.setText("");
				addGame6Btn.setEnabled(true);
			}
			date = playoffFormController.getGameDate(7, getSelectedRound());
			if(gameDAO.checkForGame(seededTeam, unseededTeam, date)) {
				int[] result = gameDAO.getGameResult(seededTeam, unseededTeam, date);
				game7TF.setText(seededTeam.getFranchiseAbrv() + " " + result[0] + " - " + result[1] + " " + unseededTeam.getFranchiseAbrv());
				addGame7Btn.setEnabled(false);
			} else {
				game7TF.setText("");
				addGame7Btn.setEnabled(true);
			}
		}
	}
	
	public String getSelectedRound() {
		return (String) roundCB.getSelectedItem();
	}
	
	public Date getPlayoffStartDate() {
		return playoffStartDate;
	}
	
	public SeasonDTO getSeason() {
		return season;
	}
	
	public FranchiseDTO getSeededTeam() {
		return (matchUpCB.getItemCount() == 0) ? null : matchUpsList.get(matchUpCB.getSelectedIndex())[0];
	}
	
	public FranchiseDTO getUnseededTeam() {
		return (matchUpCB.getItemCount() == 0) ? null : matchUpsList.get(matchUpCB.getSelectedIndex())[1];
	}
	
	public static MainForm getMainForm() {
		return mainForm;
	}
	
}
