import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.SeasonDAO;
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
	
	private JPanel buttonPane;
	private ArrayList<JButton> buttons;
	private JButton addPlayerBtn;
	private JButton editPlayerBtn;
	private JButton addSeasonBtn;
	
	private HashMap<Integer, Integer[]> mapCalendar;
	private HashMap<String, SeasonDTO> mapSeason;
	
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
		mapSeason = new HashMap<>();
		
		for(int i = 0; i < seasons.size(); i++) {
			calendar.setTime(seasons.get(i).getStartDate());
			int year = calendar.get(Calendar.YEAR);
			String s = String.valueOf(year);
			s += "/";
			calendar.setTime(seasons.get(i).getEndDate());
			year = calendar.get(Calendar.YEAR);
			s += year;
			seasonCB.addItem(s);
			mapSeason.put(s, seasons.get(i));
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
	
	private void initTable() {

		dtm = new DefaultTableModel();
		dtm.addColumn("Gametime");
		dtm.addColumn("Home team");
		dtm.addColumn("Home team score");
		dtm.addColumn("Away team score");
		dtm.addColumn("Away team");
		
		Object[] rowData = { "1", "2", "3", "4", "5" };
		for(int i = 0; i < 7; i++) {
			dtm.addRow(rowData);
		}
		
		gamesTbl = new JTable(dtm);
		gamesTbl.setAutoCreateRowSorter(true);
		gamesTbl.setFont(new Font("Century Gothic", Font.BOLD, 12));
		gamesTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gamesTbl.setForeground(new Color(0, 0, 139));
//		gamesTbl.setBackground(new Color(173, 216, 230));
		gamesTbl.setBackground(Color.white);
		gamesTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 12));
		gamesTbl.getTableHeader().setBackground(Color.white);
		
		scroll = new JScrollPane(gamesTbl);
		scroll.setBounds(10, 150, 600, 263);
		scroll.setBackground(new Color(173, 216, 230));
//		scroll.getViewport().setBackground(new Color(173, 216, 230));
		scroll.getViewport().setBackground(Color.white);
		contentPane.add(scroll);
	}
	
	private void initMap(boolean leapYear) {
		mapCalendar = new HashMap<>();
		Integer[] jan = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };
		Integer[] feb1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28 };
		Integer[] feb2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 };
		Integer[] apr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 };
		mapCalendar.put(1, jan);
		if(leapYear) {
			mapCalendar.put(2, feb2);
		} else {
			mapCalendar.put(2, feb1);
		}
		mapCalendar.put(3, jan);
		mapCalendar.put(4, apr);
		mapCalendar.put(5, jan);
		mapCalendar.put(6, apr);
		mapCalendar.put(7, jan);
		mapCalendar.put(8, jan);
		mapCalendar.put(9, apr);
		mapCalendar.put(10, jan);
		mapCalendar.put(11, apr);
		mapCalendar.put(12, jan);
	}
	
	private void removeDays(int month, int startDay, int endDay) {
		Integer[] days = mapCalendar.get(month);
		Integer[] replacement = new Integer[days.length - (endDay - startDay)];
		int i = 0;
		for(Integer day : days) {
			if(day < startDay || day > endDay) {
				replacement[i++] = day;
			}
		}
		mapCalendar.remove(month);
		mapCalendar.put(month, replacement);
	}
	
	private void initDayCB(int month) {
		if(month != 0) {
			dayCB.removeAllItems();
			Integer[] days = mapCalendar.get(month);
			for(Integer day : days) {
				dayCB.addItem(day);
			}
		}
	}
	
	private void initMonthCB() {
		if(monthCB.getItemCount() > 0) {
			monthCB.removeAllItems();
		}
		Calendar calendar = new GregorianCalendar();
		String season = (String) seasonCB.getSelectedItem();
		SeasonDTO selectedSeason = mapSeason.get(season);
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
		
	}
	
	private void setMap() {
		Calendar calendar = new GregorianCalendar();
		String season = (String) seasonCB.getSelectedItem();
		SeasonDTO selectedSeason = mapSeason.get(season);
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
		removeDays(startMonth, 1, startDay - 1);
		if(endMonth == playoffStartMonth) {
			removeDays(endMonth, endDay + 1, playoffStartDay - 1);
		} else {
			removeDays(endMonth, endDay + 1, 31);
			removeDays(playoffStartMonth, 1, playoffStartDay - 1);
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
		addGameBtn.setBounds(80, 430, 200, 40);
		addGameBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addGameBtn.setBackground(Color.WHITE);
		contentPane.add(addGameBtn);
		
		editGameBtn = new JButton("Edit game");
		editGameBtn.setBounds(340, 430, 200, 40);
		editGameBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		editGameBtn.setBackground(Color.WHITE);
		contentPane.add(editGameBtn);
		
		buttonPane = new JPanel();
		buttonPane.setBounds(630, 10, 250, 630);
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
		
		setButtonsSize();
		setButtonsListeners();
	}
	
	private void setButtonsSize() {
		for(JButton button : buttons) {
			button.setPreferredSize(new Dimension(139, 33));
		}
	}
	
	private void setButtonsListeners() {
		
		addPlayerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFormController.createAddPlayerForm();
			}
		});
		
	}

}
