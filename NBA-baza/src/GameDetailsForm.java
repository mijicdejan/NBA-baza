import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.GameDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.PerformanceDAO;
import org.unibl.etf.nba.persistence.model.dto.GameDTO;
import org.unibl.etf.nba.persistence.model.dto.PerformanceDTO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;

@SuppressWarnings("serial")
public class GameDetailsForm extends JFrame {

	private JPanel contentPane;
	private JPanel statsPane;
	private JPanel performancesPane;
	
	private JComboBox<String> statsCB;
	private JComboBox<String> performancesCB;
	private JTable statsTbl;
	private JTable performancesTbl;
	private JScrollPane statsScroll;
	private JScrollPane performancesScroll;
	private DefaultTableModel statsDTM;
	private DefaultTableModel performancesDTM;
	
	private GameDTO game;
	private ArrayList<PlayerDTO> homePlayers;
	private ArrayList<PlayerDTO> awayPlayers;
	private ArrayList<PerformanceDTO> homePerformances;
	private ArrayList<PerformanceDTO> awayPerformances;

	/**
	 * Create the frame.
	 */
	public GameDetailsForm(GameDTO game) {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetGameDetailsFormOpened();
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
		setTitle("Details");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		DAOFactory factory = new MySQLDAOFactory();
		GameDAO gameDAO = factory.getGameDAO();
		PerformanceDAO performanceDAO = factory.getPerformanceDAO();
		this.game = game;
		homePlayers = gameDAO.getPlayersForGame(game.getHomeTeam(), game.getSeason(), game.getGameTime());
		awayPlayers = gameDAO.getPlayersForGame(game.getAwayTeam(), game.getSeason(), game.getGameTime());
		homePerformances = new ArrayList<>();
		for(int i = 0; i < homePlayers.size(); i++) {
			homePerformances.add(performanceDAO.getPerformance(homePlayers.get(i), game));
		}
		awayPerformances = new ArrayList<>();
		for(int i = 0; i < awayPlayers.size(); i++) {
			awayPerformances.add(performanceDAO.getPerformance(awayPlayers.get(i), game));
		}
		
		initComponents();
		initStatsCBListener();
	}
	
	private void initComponents() {
		String[] items = { "Team stats", "Player stats" };
		statsCB = new JComboBox<>(items);
		statsCB.setBounds(10, 10, 300, 30);
		statsCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(statsCB);
		
		initStatsPane();
	}
	
	private void initStatsPane() {
		statsPane = new JPanel();
		statsPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		statsPane.setLayout(null);
		statsPane.setBackground(new Color(0, 102, 204));
		statsPane.setBounds(0, 45, 900, 400);
		contentPane.add(statsPane);
		
		initStatsTbl();
	}
	
	private void initStatsTbl() {
		statsDTM = new DefaultTableModel();
		statsDTM.addColumn(game.getHomeTeam().getTeamNames().get(game.getSeason()));
		statsDTM.addColumn("");
		statsDTM.addColumn(game.getAwayTeam().getTeamNames().get(game.getSeason()));
		
		int homePoints = game.getHomeTeamScore();
		int homeFga = 0;
		int homeFgm = 0;
		int homeThreePA = 0;
		int homeThreePM = 0;
		int homeFta = 0;
		int homeFtm = 0;
		int homeAssists = 0;
		int homeOffensiveRebounds = 0;
		int homeDefensiveRebounds = 0;
		int homeSteals = 0;
		int homeBlocks = 0;
		int homeFouls = 0;
		
		for(int i = 0; i < homePerformances.size(); i++) {
			PerformanceDTO performance = homePerformances.get(i);
			homeFga += performance.getFga();
			homeFgm += performance.getFgm();
			homeThreePA += performance.getThreepa();
			homeThreePM += performance.getThreepm();
			homeFta += performance.getFta();
			homeFtm += performance.getFtm();
			homeAssists += performance.getAssists();
			homeOffensiveRebounds += performance.getOffensiveRebounds();
			homeDefensiveRebounds += performance.getDefensiveRebounds();
			homeSteals += performance.getSteals();
			homeBlocks += performance.getBlocks();
			homeFouls += performance.getFouls();
		}
		
		int awayPoints = game.getAwayTeamScore();
		int awayFga = 0;
		int awayFgm = 0;
		int awayThreePA = 0;
		int awayThreePM = 0;
		int awayFta = 0;
		int awayFtm = 0;
		int awayAssists = 0;
		int awayOffensiveRebounds = 0;
		int awayDefensiveRebounds = 0;
		int awaySteals = 0;
		int awayBlocks = 0;
		int awayFouls = 0;
		
		for(int i = 0; i < awayPerformances.size(); i++) {
			PerformanceDTO performance = awayPerformances.get(i);
			awayFga += performance.getFga();
			awayFgm += performance.getFgm();
			awayThreePA += performance.getThreepa();
			awayThreePM += performance.getThreepm();
			awayFta += performance.getFta();
			awayFtm += performance.getFtm();
			awayAssists += performance.getAssists();
			awayOffensiveRebounds += performance.getOffensiveRebounds();
			awayDefensiveRebounds += performance.getDefensiveRebounds();
			awaySteals += performance.getSteals();
			awayBlocks += performance.getBlocks();
			awayFouls += performance.getFouls();
		}
		
		Object[] points = { homePoints, "Points", awayPoints };
		Object[] fga = { homeFga, "FGA", awayFga };
		Object[] fgm = { homeFgm, "FGM", awayFgm };
		Object[] threePA = { homeThreePA, "3PA", awayThreePA };
		Object[] threePM = { homeThreePM, "3PM", awayThreePM };
		Object[] fta = { homeFta, "FTA", awayFta };
		Object[] ftm = { homeFtm, "FTM", awayFtm };
		Object[] assists = { homeAssists, "Asistss", awayAssists };
		Object[] offensiveRebounds = { homeOffensiveRebounds, "Offensive rebounds", awayOffensiveRebounds };
		Object[] defensiveRebounds = { homeDefensiveRebounds, "Defensive rebounds", awayDefensiveRebounds };
		Object[] steals = { homeSteals, "Steals", awaySteals };
		Object[] blocks = { homeBlocks, "Blocks", awayBlocks };
		Object[] fouls = { homeFouls, "Fouls", awayFouls };
		
		statsDTM.addRow(points);
		statsDTM.addRow(fga);
		statsDTM.addRow(fgm);
		statsDTM.addRow(threePA);
		statsDTM.addRow(threePM);
		statsDTM.addRow(fta);
		statsDTM.addRow(ftm);
		statsDTM.addRow(assists);
		statsDTM.addRow(offensiveRebounds);
		statsDTM.addRow(defensiveRebounds);
		statsDTM.addRow(steals);
		statsDTM.addRow(blocks);
		statsDTM.addRow(fouls);
		
		statsTbl = new JTable(statsDTM) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		statsTbl.setAutoCreateRowSorter(false);
		statsTbl.setFont(new Font("Century Gothic", Font.BOLD, 14));
		statsTbl.setRowSelectionAllowed(false);
		statsTbl.setForeground(new Color(0, 0, 139));
		statsTbl.setBackground(Color.white);
		statsTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 16));
		statsTbl.getTableHeader().setBackground(Color.white);
		statsTbl.setRowHeight(20);
		((DefaultTableModel) statsTbl.getModel()).fireTableDataChanged();
		
		statsScroll = new JScrollPane();
		statsScroll.setViewportView(statsTbl);
		statsScroll.setBounds(10, 10, 500, 288);
		statsScroll.setBackground(new Color(173, 216, 230));
		statsScroll.getViewport().setBackground(Color.white);
		statsPane.add(statsScroll);
	}
	
	private void initPerformancesPane() {
		performancesPane = new JPanel();
		performancesPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		performancesPane.setLayout(null);
		performancesPane.setBackground(new Color(0, 102, 204));
		performancesPane.setBounds(0, 45, 850, 500);
		contentPane.add(performancesPane);
		
		String[] items = { "All", game.getHomeTeam().getTeamNames().get(game.getSeason()), game.getAwayTeam().getTeamNames().get(game.getSeason()) };
		performancesCB = new JComboBox<>(items);
		performancesCB.setBounds(10, 10, 300, 30);
		performancesCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		performancesPane.add(performancesCB);
		
		initPerformancesCBListener();
		initPerformancesTblAll();
	}
	
	private void initPerformancesTblAll() {
		try {
			performancesPane.remove(performancesScroll);
		} catch (Exception e) {
			
		}
		
		performancesDTM = new DefaultTableModel() {
			@Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }
		};
		performancesDTM.addColumn("First name");
		performancesDTM.addColumn("Last name");
		performancesDTM.addColumn("Min");
		performancesDTM.addColumn("Poi");
		performancesDTM.addColumn("FGA");
		performancesDTM.addColumn("FGM");
		performancesDTM.addColumn("3PA");
		performancesDTM.addColumn("3PM");
		performancesDTM.addColumn("FTA");
		performancesDTM.addColumn("FTM");
		performancesDTM.addColumn("Ass");
		performancesDTM.addColumn("OR");
		performancesDTM.addColumn("DR");
		performancesDTM.addColumn("Ste");
		performancesDTM.addColumn("Blo");
		performancesDTM.addColumn("Fou");
		
		ArrayList<PlayerDTO> players = new ArrayList<>();
		players.addAll(homePlayers);
		players.addAll(awayPlayers);
		ArrayList<PerformanceDTO> performances = new ArrayList<>();
		performances.addAll(homePerformances);
		performances.addAll(awayPerformances);
		
		for(int i = 0; i < players.size(); i++) {
			PlayerDTO player = players.get(i);
			PerformanceDTO performance = performances.get(i);
			int minutes = performance.getSeconds() / 60;
			int seconds = performance.getSeconds() % 60;
			Object[] rowData = { player.getFirstName(), player.getLastName(), String.format("%02d", minutes) + ":" + String.format("%02d", seconds), performance.getPoints(),
					performance.getFga(), performance.getFgm(), performance.getThreepa(), performance.getThreepm(),
					performance.getFta(), performance.getFtm(), performance.getAssists(), performance.getOffensiveRebounds(),
					performance.getDefensiveRebounds(), performance.getSteals(), performance.getBlocks(),
					performance.getFouls()};
			performancesDTM.addRow(rowData);
		}
		
		performancesTbl = new JTable(performancesDTM) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		performancesTbl.setAutoCreateRowSorter(true);
		performancesTbl.setFont(new Font("Century Gothic", Font.BOLD, 14));
		performancesTbl.setRowSelectionAllowed(false);
		performancesTbl.setForeground(new Color(0, 0, 139));
		performancesTbl.setBackground(Color.white);
		performancesTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 14));
		performancesTbl.getTableHeader().setBackground(Color.white);
		performancesTbl.setRowHeight(20);
		((DefaultTableModel) performancesTbl.getModel()).fireTableDataChanged();
		
		performancesTbl.getColumnModel().getColumn(0).setPreferredWidth(230);
		performancesTbl.getColumnModel().getColumn(1).setPreferredWidth(230);
		performancesTbl.getColumnModel().getColumn(2).setPreferredWidth(80);
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(performancesDTM);
		performancesTbl.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		performancesScroll = new JScrollPane();
		performancesScroll.setViewportView(performancesTbl);
		performancesScroll.setBounds(10, 50, 800, 350);
		performancesScroll.setBackground(new Color(173, 216, 230));
		performancesScroll.getViewport().setBackground(Color.white);
		performancesPane.add(performancesScroll);
	}
	
	private void initPerformancesTblHome() {
		try {
			performancesPane.remove(performancesScroll);
		} catch (Exception e) {
			
		}
		
		performancesDTM = new DefaultTableModel() {
			@Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }
		};
		performancesDTM.addColumn("First name");
		performancesDTM.addColumn("Last name");
		performancesDTM.addColumn("Min");
		performancesDTM.addColumn("Poi");
		performancesDTM.addColumn("FGA");
		performancesDTM.addColumn("FGM");
		performancesDTM.addColumn("3PA");
		performancesDTM.addColumn("3PM");
		performancesDTM.addColumn("FTA");
		performancesDTM.addColumn("FTM");
		performancesDTM.addColumn("Ass");
		performancesDTM.addColumn("OR");
		performancesDTM.addColumn("DR");
		performancesDTM.addColumn("Ste");
		performancesDTM.addColumn("Blo");
		performancesDTM.addColumn("Fou");
		
		for(int i = 0; i < homePlayers.size(); i++) {
			PlayerDTO player = homePlayers.get(i);
			PerformanceDTO performance = homePerformances.get(i);
			int minutes = performance.getSeconds() / 60;
			int seconds = performance.getSeconds() % 60;
			Object[] rowData = { player.getFirstName(), player.getLastName(), String.format("%02d", minutes) + ":" + String.format("%02d", seconds), performance.getPoints(),
					performance.getFga(), performance.getFgm(), performance.getThreepa(), performance.getThreepm(),
					performance.getFta(), performance.getFtm(), performance.getAssists(), performance.getOffensiveRebounds(),
					performance.getDefensiveRebounds(), performance.getSteals(), performance.getBlocks(),
					performance.getFouls()};
			performancesDTM.addRow(rowData);
		}
		
		performancesTbl = new JTable(performancesDTM) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		performancesTbl.setAutoCreateRowSorter(true);
		performancesTbl.setFont(new Font("Century Gothic", Font.BOLD, 14));
		performancesTbl.setRowSelectionAllowed(false);
		performancesTbl.setForeground(new Color(0, 0, 139));
		performancesTbl.setBackground(Color.white);
		performancesTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 14));
		performancesTbl.getTableHeader().setBackground(Color.white);
		performancesTbl.setRowHeight(20);
		((DefaultTableModel) performancesTbl.getModel()).fireTableDataChanged();
		
		performancesTbl.getColumnModel().getColumn(0).setPreferredWidth(230);
		performancesTbl.getColumnModel().getColumn(1).setPreferredWidth(230);
		performancesTbl.getColumnModel().getColumn(2).setPreferredWidth(80);
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(performancesDTM);
		performancesTbl.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		performancesScroll = new JScrollPane();
		performancesScroll.setViewportView(performancesTbl);
		performancesScroll.setBounds(10, 50, 800, 350);
		performancesScroll.setBackground(new Color(173, 216, 230));
		performancesScroll.getViewport().setBackground(Color.white);
		performancesPane.add(performancesScroll);
	}
	
	private void initPerformancesTblAway() {
		try {
			performancesPane.remove(performancesScroll);
		} catch (Exception e) {
			
		}
		
		performancesDTM = new DefaultTableModel() {
			@Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }
		};
		performancesDTM.addColumn("First name");
		performancesDTM.addColumn("Last name");
		performancesDTM.addColumn("Min");
		performancesDTM.addColumn("Poi");
		performancesDTM.addColumn("FGA");
		performancesDTM.addColumn("FGM");
		performancesDTM.addColumn("3PA");
		performancesDTM.addColumn("3PM");
		performancesDTM.addColumn("FTA");
		performancesDTM.addColumn("FTM");
		performancesDTM.addColumn("Ass");
		performancesDTM.addColumn("OR");
		performancesDTM.addColumn("DR");
		performancesDTM.addColumn("Ste");
		performancesDTM.addColumn("Blo");
		performancesDTM.addColumn("Fou");
		
		for(int i = 0; i < awayPlayers.size(); i++) {
			PlayerDTO player = awayPlayers.get(i);
			PerformanceDTO performance = awayPerformances.get(i);
			int minutes = performance.getSeconds() / 60;
			int seconds = performance.getSeconds() % 60;
			Object[] rowData = { player.getFirstName(), player.getLastName(), String.format("%02d", minutes) + ":" + String.format("%02d", seconds), performance.getPoints(),
					performance.getFga(), performance.getFgm(), performance.getThreepa(), performance.getThreepm(),
					performance.getFta(), performance.getFtm(), performance.getAssists(), performance.getOffensiveRebounds(),
					performance.getDefensiveRebounds(), performance.getSteals(), performance.getBlocks(),
					performance.getFouls()};
			performancesDTM.addRow(rowData);
		}
		
		performancesTbl = new JTable(performancesDTM) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		performancesTbl.setAutoCreateRowSorter(true);
		performancesTbl.setFont(new Font("Century Gothic", Font.BOLD, 14));
		performancesTbl.setRowSelectionAllowed(false);
		performancesTbl.setForeground(new Color(0, 0, 139));
		performancesTbl.setBackground(Color.white);
		performancesTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 14));
		performancesTbl.getTableHeader().setBackground(Color.white);
		performancesTbl.setRowHeight(20);
		((DefaultTableModel) performancesTbl.getModel()).fireTableDataChanged();
		
		performancesTbl.getColumnModel().getColumn(0).setPreferredWidth(230);
		performancesTbl.getColumnModel().getColumn(1).setPreferredWidth(230);
		performancesTbl.getColumnModel().getColumn(2).setPreferredWidth(80);
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(performancesDTM);
		performancesTbl.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		performancesScroll = new JScrollPane();
		performancesScroll.setViewportView(performancesTbl);
		performancesScroll.setBounds(10, 50, 800, 350);
		performancesScroll.setBackground(new Color(173, 216, 230));
		performancesScroll.getViewport().setBackground(Color.white);
		performancesPane.add(performancesScroll);
	}
	
	private void initStatsCBListener() {
		
		statsCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(statsCB.getSelectedIndex() == 0) {
					try {
						contentPane.remove(performancesPane);
					} catch (Exception ex) {
						
					}
					contentPane.revalidate();
					contentPane.repaint();
					initStatsPane();
				} else {
					try {
						contentPane.remove(statsPane);
					} catch (Exception ex) {
						
					}
					contentPane.revalidate();
					contentPane.repaint();
					initPerformancesPane();
				}
			}
		});
		
	}
	
	private void initPerformancesCBListener() {
		
		performancesCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(performancesCB.getSelectedIndex() == 0) {
					initPerformancesTblAll();
				} else if(performancesCB.getSelectedIndex() == 1) {
					initPerformancesTblHome();
				} else {
					initPerformancesTblAway();
				}
			}
		});
		
	}

}
