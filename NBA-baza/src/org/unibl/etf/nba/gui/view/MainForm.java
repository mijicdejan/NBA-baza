package org.unibl.etf.nba.gui.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.unibl.etf.nba.logic.control.MainFormController;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.GameDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.SeasonDAO;
import org.unibl.etf.nba.persistence.model.dto.GameDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

@SuppressWarnings("serial")
public class MainForm extends JFrame {

	private JPanel contentPane;
	
	private JLabel seasonLbl;
	private JComboBox<String> seasonCB;
	
	private JLabel dateLbl;
	private JComboBox<Integer> dayCB;
	private JComboBox<String> monthCB;
	
	private JTable gamesTbl;
	private JScrollPane scroll;
	private DefaultTableModel dtm;
	
	private JButton addGameBtn;
	private JButton editGameBtn;
	private JButton gameDetailsBtn;
	
	private JPanel buttonPane;
	private ArrayList<JButton> buttons;
	private JButton addPlayerBtn;
	private JButton editPlayerBtn;
	private JButton addSeasonBtn;
	private JButton addSeasonAwardsBtn;
	private JButton addArenaBtn;
	private JButton addRosterBtn;
	private JButton standingsBtn;
	private JButton addPlayoffGamesBtn;
	private JButton playoffPictureBtn;
	
	private HashMap<Integer, Integer[]> calendarMap;
	private HashMap<String, SeasonDTO> seasonMap;
	private MainFormController mainFormController;
	
	private ArrayList<GameDTO> games;
	
	private boolean leapYear = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
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
	public MainForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 900, 520);
		setBackground(new Color(255, 255, 255));
		setTitle("NBA");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		seasonLbl = new JLabel("Season: ");
		seasonLbl.setBounds(10, 10, 200, 25);
		seasonLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(seasonLbl);
		
		seasonCB = new JComboBox<>();
		seasonCB.setBounds(10, 35, 200, 30);
		seasonCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		seasonCB.setBackground(Color.WHITE);
		contentPane.add(seasonCB);
		
		DAOFactory factory = new MySQLDAOFactory();
		SeasonDAO seasonDAO = factory.getSeasonDAO();
		ArrayList<SeasonDTO> seasons = seasonDAO.getAllSeasons();
		
		Calendar calendar = new GregorianCalendar();
		seasonMap = new HashMap<>();
		mainFormController = new MainFormController(this);
		
		for(int i = 0; i < seasons.size(); i++) {
			calendar.setTime(seasons.get(i).getStartDate());
			int year = calendar.get(Calendar.YEAR);
			String s = String.valueOf(year);
			s += "/";
			calendar.setTime(seasons.get(i).getEndDate());
			year = calendar.get(Calendar.YEAR);
			s += year;
			seasonCB.addItem(s);
			seasonMap.put(s, seasons.get(i));
		}
		
		checkForLeapYear();
		initMap(leapYear);
		
		dateLbl = new JLabel("Date: ");
		dateLbl.setBounds(10, 70, 200, 25);
		dateLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(dateLbl);
		
		dayCB = new JComboBox<>();
		dayCB.setBounds(10, 95, 70, 30);
		dayCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		dayCB.setBackground(Color.WHITE);
		contentPane.add(dayCB);
		
		monthCB = new JComboBox<>();
		monthCB.setBounds(90, 95, 120, 30);
		monthCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		monthCB.setBackground(Color.WHITE);
		contentPane.add(monthCB);
		
		initMonthCB();
		setMap();
		initDayCB(getMonthNumber((String) monthCB.getSelectedItem()));
		
		initButtons();
		
		initTable();
		initCBListeners();
		
		
	}
	
	public void initTable() {
		try {
			contentPane.remove(scroll);
		} catch (Exception e) {
			
		}
		scroll = new JScrollPane();

		dtm = new DefaultTableModel() {
			@Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 4:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }
		};
		dtm.addColumn("Gametime");
		dtm.addColumn("Home team");
		dtm.addColumn("Home team score");
		dtm.addColumn("Away team score");
		dtm.addColumn("Away team");
		dtm.setRowCount(0);
		
		DAOFactory factory = new MySQLDAOFactory();
		GameDAO gameDAO = factory.getGameDAO();
		games = gameDAO.getAllGamesOnDate(getSelectedDate());
		
		for(int i = 0; i < games.size(); i++) {
			GameDTO game = games.get(i);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(game.getGameTime());
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			Object[] rowData = { String.format("%02d", hour) + ":" + String.format("%02d", minute), game.getHomeTeam().getTeamNames().get(seasonMap.get(seasonCB.getSelectedItem())), (game.getHomeTeamScore() == 0) ? "" : game.getHomeTeamScore(), (game.getAwayTeamScore() == 0) ? "" : game.getAwayTeamScore(), game.getAwayTeam().getTeamNames().get(seasonMap.get(seasonCB.getSelectedItem())) };
			dtm.addRow(rowData);
		}
		
		gamesTbl = new JTable(dtm) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		gamesTbl.setAutoCreateRowSorter(true);
		gamesTbl.setFont(new Font("Century Gothic", Font.BOLD, 12));
		gamesTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gamesTbl.setForeground(new Color(0, 0, 139));
		gamesTbl.setBackground(Color.white);
		gamesTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 12));
		gamesTbl.getTableHeader().setBackground(Color.white);
		((DefaultTableModel) gamesTbl.getModel()).fireTableDataChanged();
		
		if(gamesTbl.getRowCount() == 15) {
			addGameBtn.setEnabled(false);
		} else {
			addGameBtn.setEnabled(true);
		}
		
//		if(gameDAO.getNumberOfFinishedGamesInRegularSeason(getSelectedSeason()) == 1230) {
//			addPlayoffGamesBtn.setEnabled(true);
//			playoffPictureBtn.setEnabled(true);
//		} else {
//			addPlayoffGamesBtn.setEnabled(false);
//			playoffPictureBtn.setEnabled(false);
//		}
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dtm) {
			@Override
		    public boolean isSortable(int column) {
		        if(column == 0)
		            return true;
		        else 
		            return false;
		    };
		};
		gamesTbl.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		scroll.setViewportView(gamesTbl);
		scroll.setBounds(10, 150, 600, 263);
		scroll.setBackground(new Color(173, 216, 230));
		scroll.getViewport().setBackground(Color.white);
		contentPane.add(scroll);
		
		setTableListener();
	}
	
	private void initMap(boolean leapYear) {
		calendarMap = new HashMap<>();
		Integer[] jan = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };
		Integer[] feb1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28 };
		Integer[] feb2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 };
		Integer[] apr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 };
		calendarMap.put(1, jan);
		if(leapYear) {
			calendarMap.put(2, feb2);
		} else {
			calendarMap.put(2, feb1);
		}
		calendarMap.put(3, jan);
		calendarMap.put(4, apr);
		calendarMap.put(5, jan);
		calendarMap.put(6, apr);
		calendarMap.put(7, jan);
		calendarMap.put(8, jan);
		calendarMap.put(9, apr);
		calendarMap.put(10, jan);
		calendarMap.put(11, apr);
		calendarMap.put(12, jan);
	}
	
	private void removeDays(int month, int startDay, int endDay) {
		Integer[] days = calendarMap.get(month);
		Integer[] replacement = new Integer[days.length - (endDay - startDay) + 1];
		int i = 0;
		for(Integer day : days) {
			if(day < startDay || day > endDay) {
				replacement[i++] = day;
			}
		}
		calendarMap.remove(month);
		calendarMap.put(month, replacement);
	}
	
	private void initDayCB(int month) {
		if(month != 0) {
			dayCB.removeAllItems();
			Integer[] days = calendarMap.get(month);
			for(Integer day : days) {
				if(day != null) {
					dayCB.addItem(day);
				}
			}
		}
	}
	
	private void initMonthCB() {
		if(monthCB.getItemCount() > 0) {
			monthCB.removeAllItems();
		}
		Calendar calendar = new GregorianCalendar();
		String season = (String) seasonCB.getSelectedItem();
		SeasonDTO selectedSeason = seasonMap.get(season);
		calendar.setTime(selectedSeason.getStartDate());
		int startMonth = calendar.get(Calendar.MONTH) + 1;
		calendar.setTime(selectedSeason.getPlayoffEndDate());
		int endMonth = calendar.get(Calendar.MONTH) + 1;
		
		for(int i = startMonth; i < 13; i++) {
			switch(i) {
				case 1:
					monthCB.addItem("January");
					break;
				case 2:
					monthCB.addItem("February");
					break;
				case 3:
					monthCB.addItem("March");
					break;
				case 4:
					monthCB.addItem("April");
					break;
				case 5:
					monthCB.addItem("May");
					break;
				case 6:
					monthCB.addItem("June");
					break;
				case 7:
					monthCB.addItem("July");
					break;
				case 8:
					monthCB.addItem("August");
					break;
				case 9:
					monthCB.addItem("September");
					break;
				case 10:
					monthCB.addItem("October");
					break;
				case 11:
					monthCB.addItem("November");
					break;
				case 12:
					monthCB.addItem("December");
					break;
			}
		}
		
		for(int i = 1; i <= endMonth; i++) {
			switch(i) {
				case 1:
					monthCB.addItem("January");
					break;
				case 2:
					monthCB.addItem("February");
					break;
				case 3:
					monthCB.addItem("March");
					break;
				case 4:
					monthCB.addItem("April");
					break;
				case 5:
					monthCB.addItem("May");
					break;
				case 6:
					monthCB.addItem("June");
					break;
				case 7:
					monthCB.addItem("July");
					break;
				case 8:
					monthCB.addItem("August");
					break;
				case 9:
					monthCB.addItem("September");
					break;
				case 10:
					monthCB.addItem("October");
					break;
				case 11:
					monthCB.addItem("November");
					break;
				case 12:
					monthCB.addItem("December");
					break;
			}
		}
	}
	
	private void checkForLeapYear() {
		String season = (String) seasonCB.getSelectedItem();
		int year = Integer.parseInt(season.substring(season.lastIndexOf('/') + 1));
		if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			leapYear = true;
		} else {
			leapYear = false;
		}
	}
	
	private void initCBListeners() {
		
		seasonCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initMonthCB();
			}
		});
		
		monthCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkForLeapYear();
				initMap(leapYear);
				setMap();
				initDayCB(getMonthNumber((String) monthCB.getSelectedItem()));
			}
		});
		
		dayCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dayCB.getItemCount() != 0) {
					initTable();
					editGameBtn.setEnabled(false);
					gameDetailsBtn.setEnabled(false);
				}
			}
		});
		
	}
	
	private void setMap() {
		Calendar calendar = new GregorianCalendar();
		String season = (String) seasonCB.getSelectedItem();
		SeasonDTO selectedSeason = seasonMap.get(season);
		calendar.setTime(selectedSeason.getStartDate());
		int startDay = calendar.get(Calendar.DAY_OF_MONTH);
		int startMonth = calendar.get(Calendar.MONTH) + 1;
		calendar.setTime(selectedSeason.getEndDate());
		int endDay = calendar.get(Calendar.DAY_OF_MONTH);
		int endMonth = calendar.get(Calendar.MONTH) + 1;
		calendar.setTime(selectedSeason.getPlayoffStartDate());
		int playoffStartDay = calendar.get(Calendar.DAY_OF_MONTH);
		int playoffStartMonth = calendar.get(Calendar.MONTH) + 1;
		calendar.setTime(selectedSeason.getPlayoffEndDate());
		int playoffEndDay = calendar.get(Calendar.DAY_OF_MONTH);
		int playoffEndMonth = calendar.get(Calendar.MONTH) + 1;
		removeDays(startMonth, 0, startDay - 1);
		if(endMonth == playoffStartMonth) {
			removeDays(endMonth, endDay + 1, playoffStartDay - 1);
		} else {
			removeDays(endMonth, endDay + 1, 31);
			removeDays(playoffStartMonth, 0, playoffStartDay - 1);
		}
		removeDays(playoffEndMonth, playoffEndDay + 1, 31);
	}
	
	private int getMonthNumber(String month) {
		if(month == null) {
			return 0;
		}
		switch(month) {
			case "January":
				return 1;
			case "February":
				return 2;
			case "March":
				return 3;
			case "April":
				return 4;
			case "May":
				return 5;
			case "June":
				return 6;
			case "July":
				return 7;
			case "August":
				return 8;
			case "September":
				return 9;
			case "October":
				return 10;
			case "November":
				return 11;
			case "December":
				return 12;
			default:
				return 0;
		}
	}
	
	private void initButtons() {
		buttons = new ArrayList<>();
		
		addGameBtn = new JButton("Add game");
		addGameBtn.setBounds(10, 430, 180, 40);
		addGameBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addGameBtn.setBackground(Color.WHITE);
		contentPane.add(addGameBtn);
		
		editGameBtn = new JButton("Edit game");
		editGameBtn.setBounds(220, 430, 180, 40);
		editGameBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		editGameBtn.setBackground(Color.WHITE);
		editGameBtn.setEnabled(false);
		contentPane.add(editGameBtn);
		
		gameDetailsBtn = new JButton("Details");
		gameDetailsBtn.setBounds(430, 430, 180, 40);
		gameDetailsBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		gameDetailsBtn.setBackground(Color.WHITE);
		gameDetailsBtn.setEnabled(false);
		contentPane.add(gameDetailsBtn);
		
		buttonPane = new JPanel();
		buttonPane.setBounds(610, 10, 300, 630);
		buttonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		buttonPane.setBackground(new Color(0, 102, 204));
		contentPane.add(buttonPane);
		
		addPlayerBtn = new JButton("Add player");
		addPlayerBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addPlayerBtn.setBackground(Color.WHITE);
		buttonPane.add(addPlayerBtn);
		buttons.add(addPlayerBtn);
		
		editPlayerBtn = new JButton("Edit player");
		editPlayerBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		editPlayerBtn.setBackground(Color.WHITE);
		buttonPane.add(editPlayerBtn);
		buttons.add(editPlayerBtn);
		
		addSeasonBtn = new JButton("Add season");
		addSeasonBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addSeasonBtn.setBackground(Color.WHITE);
		buttonPane.add(addSeasonBtn);
		buttons.add(addSeasonBtn);
		
		addSeasonAwardsBtn = new JButton("Add season awards");
		addSeasonAwardsBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addSeasonAwardsBtn.setBackground(Color.WHITE);
		buttonPane.add(addSeasonAwardsBtn);
		buttons.add(addSeasonAwardsBtn);
		
		addArenaBtn = new JButton("Add arena");
		addArenaBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addArenaBtn.setBackground(Color.WHITE);
		buttonPane.add(addArenaBtn);
		buttons.add(addArenaBtn);
		
		addRosterBtn = new JButton("Add roster");
		addRosterBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addRosterBtn.setBackground(Color.WHITE);
		buttonPane.add(addRosterBtn);
		buttons.add(addRosterBtn);
		
		standingsBtn = new JButton("Standings");
		standingsBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		standingsBtn.setBackground(Color.WHITE);
		buttonPane.add(standingsBtn);
		buttons.add(standingsBtn);
		
		addPlayoffGamesBtn = new JButton("Add playoff games");
		addPlayoffGamesBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addPlayoffGamesBtn.setBackground(Color.WHITE);
		buttonPane.add(addPlayoffGamesBtn);
		buttons.add(addPlayoffGamesBtn);
		
		playoffPictureBtn = new JButton("Playoff picture");
		playoffPictureBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		playoffPictureBtn.setBackground(Color.WHITE);
		buttonPane.add(playoffPictureBtn);
		buttons.add(playoffPictureBtn);
		
		setButtonsSize();
		setButtonsListeners();
	}
	
	private void setButtonsSize() {
		for(JButton button : buttons) {
			button.setPreferredSize(new Dimension(208, 30));
		}
	}
	
	private void setButtonsListeners() {
		
		addPlayerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFormController.createAddPlayerForm();
			}
		});
		
		editPlayerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFormController.createChoosePlayerForm();
			}
		});
		
		addSeasonBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFormController.createAddSeasonForm();
			}
		});
		
		addSeasonAwardsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFormController.createAddSeasonAwardsForm();
			}
		});
		
		addArenaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFormController.createAddArenaForm();
			}
		});
		
		addRosterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFormController.createAddRosterForm();
			}
		});
		
		standingsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFormController.createStandingsForm();
			}
		});
		
		addPlayoffGamesBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFormController.createPlayoffForm();
			}
		});
		
		playoffPictureBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFormController.createPlayoffPictureForm();
			}
		});
		
		addGameBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFormController.createAddGameForm();
			}
		});
		
		editGameBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFormController.createEditGameForm();
			}
		});
		
		gameDetailsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFormController.createGameDetailsForm();
			}
		});
		
	}
	
	private void setTableListener() {
		
		gamesTbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = gamesTbl.convertRowIndexToModel(gamesTbl.getSelectedRow());
				Object rowData = gamesTbl.getModel().getValueAt(selectedRow, 2);
				if("".equals(String.valueOf(rowData))) {
					editGameBtn.setEnabled(true);
					gameDetailsBtn.setEnabled(false);
				} else {
					editGameBtn.setEnabled(false);
					gameDetailsBtn.setEnabled(true);
				}
			}
		});
		
	}
	
	public SeasonDTO getSelectedSeason() {
		return seasonMap.get(seasonCB.getSelectedItem());
	}
	
	public Date getSelectedDate() {
		int day = (Integer) dayCB.getSelectedItem();
		String month = (String) monthCB.getSelectedItem();
		int m = 0;
		switch(month) {
			case "January":
				m = 1;
				break;
			case "February":
				m = 2;
				break;
			case "March":
				m = 3;
				break;
			case "April":
				m = 4;
				break;
			case "May":
				m = 5;
				break;
			case "June":
				m = 6;
				break;
			case "July":
				m = 7;
				break;
			case "August":
				m = 8;
				break;
			case "September":
				m = 9;
				break;
			case "October":
				m = 10;
				break;
			case "November":
				m = 11;
				break;
			case "December":
				m = 12;
				break;
		}
		SeasonDTO season = getSelectedSeason();
		Calendar calendar = new GregorianCalendar();
		if(m == 8 || m == 9 || m == 10 || m ==1 || m == 12) {
			calendar.setTime(season.getStartDate());
			int year = calendar.get(Calendar.YEAR);
			calendar.set(year, m - 1, day);
		} else {
			calendar.setTime(season.getEndDate());
			int year = calendar.get(Calendar.YEAR);
			calendar.set(year, m - 1, day);
		}
		return calendar.getTime();
	}
	
	public GameDTO getSelectedGame() {
		return (gamesTbl.getSelectedRow() == -1) ? null : games.get(gamesTbl.convertRowIndexToModel(gamesTbl.getSelectedRow()));
	}

}
