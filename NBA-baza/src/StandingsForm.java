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
import org.unibl.etf.nba.persistence.model.dao.FranchiseDAO;
import org.unibl.etf.nba.persistence.model.dao.GameDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

@SuppressWarnings("serial")
public class StandingsForm extends JFrame {

	private JPanel contentPane;
	
	private JComboBox<String> conferenceCB;
	private JComboBox<String> easternDivisionCB;
	private JComboBox<String> westernDivisionCB;
	
	private JPanel easternPane;
	private JPanel westernPane;
	private JTable easternTbl;
	private JTable westernTbl;
	private JScrollPane easternScroll;
	private JScrollPane westernScroll;
	private DefaultTableModel easternDTM;
	private DefaultTableModel westernDTM;
	
	private MainForm mainForm;

	/**
	 * Create the frame.
	 */
	public StandingsForm(MainForm mainForm) {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetStandingsFormOpened();
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
		setTitle("Standings");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		this.mainForm = mainForm;
		
		initComponents();
	}
	
	private void initComponents() {
		String[] items = { "Eastern", "Western" };
		conferenceCB = new JComboBox<>(items);
		conferenceCB.setBounds(10, 10, 300, 30);
		conferenceCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(conferenceCB);
		
		initConferenceCBListener();
		initEasternPane();
	}
	
	private void initEasternPane() {
		easternPane = new JPanel();
		easternPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		easternPane.setLayout(null);
		easternPane.setBackground(new Color(0, 102, 204));
		easternPane.setBounds(0, 45, 900, 400);
		contentPane.add(easternPane);
		
		String[] items = { "All", "Atlantic", "Central", "Southeast" };
		easternDivisionCB = new JComboBox<>(items);
		easternDivisionCB.setBounds(10, 10, 300, 30);
		easternDivisionCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		easternPane.add(easternDivisionCB);
		
		initEasternCBListener();
		initEasternTblAll();
	}
	
	private void initEasternTblAll() {
		try {
			easternPane.remove(easternScroll);
		} catch (Exception e) {
			
		}
		
		easternDTM = new DefaultTableModel() {
			@Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 1:
                        return String.class;
                    case 5:
                        return String.class;
                    case 6:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }
		};
		easternDTM.addColumn("#");
		easternDTM.addColumn("Team name");
		easternDTM.addColumn("GP");
		easternDTM.addColumn("W");
		easternDTM.addColumn("L");
		easternDTM.addColumn("TP");
		easternDTM.addColumn("PCT");
		
		DAOFactory factory = new MySQLDAOFactory();
		FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
		GameDAO gameDAO = factory.getGameDAO();
		ArrayList<FranchiseDTO> franchises = franchiseDAO.getAllFranchisesFromConference("Eastern");
		SeasonDTO season = mainForm.getSelectedSeason();
		
		for(int i = 0; i < franchises.size(); i++) {
			FranchiseDTO franchise = franchises.get(i);
			int gp = gameDAO.getNumberOfGamesForTeamInSeason(franchise, season);
			int w = gameDAO.getNumberOfWinsForTeamInSeason(franchise, season);
			int l = gp - w;
			int ps = gameDAO.getNumberOfHomePointsScoredForTeamInSeason(franchise, season) + gameDAO.getNumberOfAwayPointsScoredForTeamInSeason(franchise, season);
			int pc = gameDAO.getNumberOfHomePointsConcededForTeamInSeason(franchise, season) + gameDAO.getNumberOfAwayPointsConcededForTeamInSeason(franchise, season);
			String tp = ps + ":" + pc;
			double pct = (gp == 0) ? 0.0 : (double) w / gp;
			Object[] rowData = { i + 1, franchise.getTeamNames().get(season), gp, w, l, tp, String.format("%.3f", pct) };
			easternDTM.addRow(rowData);
		}
		
		easternTbl = new JTable(easternDTM) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		easternTbl.setAutoCreateRowSorter(true);
		easternTbl.setFont(new Font("Century Gothic", Font.BOLD, 14));
		easternTbl.setRowSelectionAllowed(false);
		easternTbl.setForeground(new Color(0, 0, 139));
		easternTbl.setBackground(Color.white);
		easternTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 14));
		easternTbl.getTableHeader().setBackground(Color.white);
		easternTbl.setRowHeight(20);
		((DefaultTableModel) easternTbl.getModel()).fireTableDataChanged();
		
		easternTbl.getColumnModel().getColumn(1).setPreferredWidth(230);
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(easternDTM);
		easternTbl.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(6, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		for(int i = 0; i < franchises.size(); i++) {
			easternTbl.getModel().setValueAt(i + 1, easternTbl.convertRowIndexToModel(i), 0);
		}
		
		easternScroll = new JScrollPane();
		easternScroll.setViewportView(easternTbl);
		easternScroll.setBounds(10, 50, 800, 326);
		easternScroll.setBackground(new Color(173, 216, 230));
		easternScroll.getViewport().setBackground(Color.white);
		easternPane.add(easternScroll);
	}
	
	private void initEasternTblDivision(String division) {
		try {
			easternPane.remove(easternScroll);
		} catch (Exception e) {
			
		}
		
		easternDTM = new DefaultTableModel() {
			@Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 1:
                        return String.class;
                    case 5:
                        return String.class;
                    case 6:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }
		};
		easternDTM.addColumn("#");
		easternDTM.addColumn("Team name");
		easternDTM.addColumn("GP");
		easternDTM.addColumn("W");
		easternDTM.addColumn("L");
		easternDTM.addColumn("TP");
		easternDTM.addColumn("PCT");
		
		DAOFactory factory = new MySQLDAOFactory();
		FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
		GameDAO gameDAO = factory.getGameDAO();
		ArrayList<FranchiseDTO> franchises = franchiseDAO.getAllFranchisesFromDivision(division);
		SeasonDTO season = mainForm.getSelectedSeason();
		
		for(int i = 0; i < franchises.size(); i++) {
			FranchiseDTO franchise = franchises.get(i);
			int gp = gameDAO.getNumberOfGamesForTeamInSeason(franchise, season);
			int w = gameDAO.getNumberOfWinsForTeamInSeason(franchise, season);
			int l = gp - w;
			int ps = gameDAO.getNumberOfHomePointsScoredForTeamInSeason(franchise, season) + gameDAO.getNumberOfAwayPointsScoredForTeamInSeason(franchise, season);
			int pc = gameDAO.getNumberOfHomePointsConcededForTeamInSeason(franchise, season) + gameDAO.getNumberOfAwayPointsConcededForTeamInSeason(franchise, season);
			String tp = ps + ":" + pc;
			double pct = (gp == 0) ? 0.0 : (double) w / gp;
			Object[] rowData = { i + 1, franchise.getTeamNames().get(season), gp, w, l, tp, String.format("%.3f", pct) };
			easternDTM.addRow(rowData);
		}
		
		easternTbl = new JTable(easternDTM) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		easternTbl.setAutoCreateRowSorter(true);
		easternTbl.setFont(new Font("Century Gothic", Font.BOLD, 14));
		easternTbl.setRowSelectionAllowed(false);
		easternTbl.setForeground(new Color(0, 0, 139));
		easternTbl.setBackground(Color.white);
		easternTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 14));
		easternTbl.getTableHeader().setBackground(Color.white);
		easternTbl.setRowHeight(20);
		((DefaultTableModel) easternTbl.getModel()).fireTableDataChanged();
		
		easternTbl.getColumnModel().getColumn(1).setPreferredWidth(230);
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(easternDTM);
		easternTbl.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(6, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		for(int i = 0; i < franchises.size(); i++) {
			easternTbl.getModel().setValueAt(i + 1, easternTbl.convertRowIndexToModel(i), 0);
		}
		
		easternScroll = new JScrollPane();
		easternScroll.setViewportView(easternTbl);
		easternScroll.setBounds(10, 50, 800, 326);
		easternScroll.setBackground(new Color(173, 216, 230));
		easternScroll.getViewport().setBackground(Color.white);
		easternPane.add(easternScroll);
	}
	
	private void initWesternPane() {
		westernPane = new JPanel();
		westernPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		westernPane.setLayout(null);
		westernPane.setBackground(new Color(0, 102, 204));
		westernPane.setBounds(0, 45, 900, 400);
		contentPane.add(westernPane);
		
		String[] items = { "All", "Northwest", "Pacific", "Southwest" };
		westernDivisionCB = new JComboBox<>(items);
		westernDivisionCB.setBounds(10, 10, 300, 30);
		westernDivisionCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		westernPane.add(westernDivisionCB);
		
		initWesternCBListener();
		initWesternTblAll();
	}
	
	private void initWesternTblAll() {
		try {
			westernPane.remove(westernScroll);
		} catch (Exception e) {
			
		}
		
		westernDTM = new DefaultTableModel() {
			@Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 1:
                        return String.class;
                    case 5:
                        return String.class;
                    case 6:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }
		};
		westernDTM.addColumn("#");
		westernDTM.addColumn("Team name");
		westernDTM.addColumn("GP");
		westernDTM.addColumn("W");
		westernDTM.addColumn("L");
		westernDTM.addColumn("TP");
		westernDTM.addColumn("PCT");
		
		DAOFactory factory = new MySQLDAOFactory();
		FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
		GameDAO gameDAO = factory.getGameDAO();
		ArrayList<FranchiseDTO> franchises = franchiseDAO.getAllFranchisesFromConference("Western");
		SeasonDTO season = mainForm.getSelectedSeason();
		
		for(int i = 0; i < franchises.size(); i++) {
			FranchiseDTO franchise = franchises.get(i);
			int gp = gameDAO.getNumberOfGamesForTeamInSeason(franchise, season);
			int w = gameDAO.getNumberOfWinsForTeamInSeason(franchise, season);
			int l = gp - w;
			int ps = gameDAO.getNumberOfHomePointsScoredForTeamInSeason(franchise, season) + gameDAO.getNumberOfAwayPointsScoredForTeamInSeason(franchise, season);
			int pc = gameDAO.getNumberOfHomePointsConcededForTeamInSeason(franchise, season) + gameDAO.getNumberOfAwayPointsConcededForTeamInSeason(franchise, season);
			String tp = ps + ":" + pc;
			double pct = (gp == 0) ? 0.0 : (double) w / gp;
			Object[] rowData = { i + 1, franchise.getTeamNames().get(season), gp, w, l, tp, String.format("%.3f", pct) };
			westernDTM.addRow(rowData);
		}
		
		westernTbl = new JTable(westernDTM) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		westernTbl.setAutoCreateRowSorter(true);
		westernTbl.setFont(new Font("Century Gothic", Font.BOLD, 14));
		westernTbl.setRowSelectionAllowed(false);
		westernTbl.setForeground(new Color(0, 0, 139));
		westernTbl.setBackground(Color.white);
		westernTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 14));
		westernTbl.getTableHeader().setBackground(Color.white);
		westernTbl.setRowHeight(20);
		((DefaultTableModel) westernTbl.getModel()).fireTableDataChanged();
		
		westernTbl.getColumnModel().getColumn(1).setPreferredWidth(230);
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(westernDTM);
		westernTbl.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(6, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		for(int i = 0; i < franchises.size(); i++) {
			westernTbl.getModel().setValueAt(i + 1, westernTbl.convertRowIndexToModel(i), 0);
		}
		
		westernScroll = new JScrollPane();
		westernScroll.setViewportView(westernTbl);
		westernScroll.setBounds(10, 50, 800, 326);
		westernScroll.setBackground(new Color(173, 216, 230));
		westernScroll.getViewport().setBackground(Color.white);
		westernPane.add(westernScroll);
	}
	
	private void initWesternTblDivision(String division) {
		try {
			westernPane.remove(westernScroll);
		} catch (Exception e) {
			
		}
		
		westernDTM = new DefaultTableModel() {
			@Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 1:
                        return String.class;
                    case 5:
                        return String.class;
                    case 6:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }
		};
		westernDTM.addColumn("#");
		westernDTM.addColumn("Team name");
		westernDTM.addColumn("GP");
		westernDTM.addColumn("W");
		westernDTM.addColumn("L");
		westernDTM.addColumn("TP");
		westernDTM.addColumn("PCT");
		
		DAOFactory factory = new MySQLDAOFactory();
		FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
		GameDAO gameDAO = factory.getGameDAO();
		ArrayList<FranchiseDTO> franchises = franchiseDAO.getAllFranchisesFromDivision(division);
		SeasonDTO season = mainForm.getSelectedSeason();
		
		for(int i = 0; i < franchises.size(); i++) {
			FranchiseDTO franchise = franchises.get(i);
			int gp = gameDAO.getNumberOfGamesForTeamInSeason(franchise, season);
			int w = gameDAO.getNumberOfWinsForTeamInSeason(franchise, season);
			int l = gp - w;
			int ps = gameDAO.getNumberOfHomePointsScoredForTeamInSeason(franchise, season) + gameDAO.getNumberOfAwayPointsScoredForTeamInSeason(franchise, season);
			int pc = gameDAO.getNumberOfHomePointsConcededForTeamInSeason(franchise, season) + gameDAO.getNumberOfAwayPointsConcededForTeamInSeason(franchise, season);
			String tp = ps + ":" + pc;
			double pct = (gp == 0) ? 0.0 : (double) w / gp;
			Object[] rowData = { i + 1, franchise.getTeamNames().get(season), gp, w, l, tp, String.format("%.3f", pct) };
			westernDTM.addRow(rowData);
		}
		
		westernTbl = new JTable(westernDTM) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		westernTbl.setAutoCreateRowSorter(true);
		westernTbl.setFont(new Font("Century Gothic", Font.BOLD, 14));
		westernTbl.setRowSelectionAllowed(false);
		westernTbl.setForeground(new Color(0, 0, 139));
		westernTbl.setBackground(Color.white);
		westernTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 14));
		westernTbl.getTableHeader().setBackground(Color.white);
		westernTbl.setRowHeight(20);
		((DefaultTableModel) westernTbl.getModel()).fireTableDataChanged();
		
		westernTbl.getColumnModel().getColumn(1).setPreferredWidth(230);
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(westernDTM);
		westernTbl.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(6, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		for(int i = 0; i < franchises.size(); i++) {
			westernTbl.getModel().setValueAt(i + 1, westernTbl.convertRowIndexToModel(i), 0);
		}
		
		westernScroll = new JScrollPane();
		westernScroll.setViewportView(westernTbl);
		westernScroll.setBounds(10, 50, 800, 326);
		westernScroll.setBackground(new Color(173, 216, 230));
		westernScroll.getViewport().setBackground(Color.white);
		westernPane.add(westernScroll);
	}
	
	private void initConferenceCBListener() {
		
		conferenceCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if("Eastern".equals((String) conferenceCB.getSelectedItem())) {
					try {
						contentPane.remove(westernPane);
					} catch (Exception ex) {
						
					}
					contentPane.revalidate();
					contentPane.repaint();
					initEasternPane();
				} else {
					try {
						contentPane.remove(easternPane);
					} catch (Exception ex) {
						
					}
					contentPane.revalidate();
					contentPane.repaint();
					initWesternPane();
				}
			}
		});
		
	}
	
	private void initEasternCBListener() {
		
		easternDivisionCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if("All".equals((String) easternDivisionCB.getSelectedItem())) {
					initEasternTblAll();
				} else if("Atlantic".equals((String) easternDivisionCB.getSelectedItem())) {
					initEasternTblDivision("Atlantic");
				} else if("Central".equals((String) easternDivisionCB.getSelectedItem())) {
					initEasternTblDivision("Central");
				} else {
					initEasternTblDivision("Southeast");
				}
			}
		});
		
	}
	
	private void initWesternCBListener() {
		
		westernDivisionCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if("All".equals((String) westernDivisionCB.getSelectedItem())) {
					initWesternTblAll();
				} else if("Northwest".equals((String) westernDivisionCB.getSelectedItem())) {
					initWesternTblDivision("Northwest");
				} else if("Pacific".equals((String) westernDivisionCB.getSelectedItem())) {
					initWesternTblDivision("Pacific");
				} else {
					initWesternTblDivision("Southwest");
				}
			}
		});
		
	}

}
