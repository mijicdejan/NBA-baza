package org.unibl.etf.nba.gui.view;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
//import java.util.HashMap;

//import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.unibl.etf.nba.logic.control.ChoosePlayerFormController;
import org.unibl.etf.nba.logic.control.MainFormController;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.PlayerDAO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;

@SuppressWarnings("serial")
public class ChoosePlayerForm extends JFrame {

	private JPanel contentPane;
	
//	private JComboBox<String> playersCB;
//	private JButton editBtn;
	
	private PlayerDTO player;
	
	private JTable playersTbl;
	private JScrollPane scroll;
	private DefaultTableModel dtm;
	
	private ChoosePlayerFormController choosePlayerFormController;
//	private HashMap<String, PlayerDTO> playersMap;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChoosePlayerForm frame = new ChoosePlayerForm();
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
	public ChoosePlayerForm() {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetChoosePlayerFormOpened();
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
		setBounds(100, 50, 630, 330);
		setBackground(new Color(255, 255, 255));
		setTitle("Choose player");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		player = null;
		choosePlayerFormController = new ChoosePlayerFormController(this);
//		playersMap = new HashMap<>();
		
//		initComponents();
		initTable();
//		initButtonListener();
	}
	
//	private void initComponents() {
//		playersCB = new JComboBox<>();
//		playersCB.setBounds(10, 10, 300, 30);
//		playersCB.setFont(new Font("Century Gothic", Font.BOLD, 18));
//		contentPane.add(playersCB);
//		
//		editBtn = new JButton("Edit");
//		editBtn.setBounds(85, 60, 150, 30);
//		editBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
//		editBtn.setBackground(Color.WHITE);
//		contentPane.add(editBtn);
//		
//		DAOFactory factory = new MySQLDAOFactory();
//		PlayerDAO playerDAO = factory.getPlayerDAO();
//		ArrayList<PlayerDTO> players = playerDAO.getAllPlayers();
//		
//		for(PlayerDTO player : players) {
//			playersCB.addItem(player.getFirstName() + " " + player.getLastName());
//			playersMap.put(player.getFirstName() + " " + player.getLastName(), player);
//		}
//	}
	
//	private void initButtonListener() {
//		
//		editBtn.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				choosePlayerFormController.edit();
//			}
//		});
//		
//	}
	
	public PlayerDTO getSelectedPlayer() {
		return player;
	}
	
	private void initTable() {
		dtm = new DefaultTableModel();
		dtm.addColumn("#");
		dtm.addColumn("First name");
		dtm.addColumn("Last name");
		dtm.addColumn("Birthdate");
		dtm.addColumn("Height");
		dtm.addColumn("Weight");
		dtm.addColumn("Birthplace");
		
		DAOFactory factory = new MySQLDAOFactory();
		PlayerDAO playerDAO = factory.getPlayerDAO();
		ArrayList<PlayerDTO> players = playerDAO.getAllPlayers();
		
		for(PlayerDTO player : players) {
			Object[] rowData = new Object[7];
			rowData[0] = player.getId();
			rowData[1] = player.getFirstName();
			rowData[2] = player.getLastName();
			rowData[3] = player.getBirthdate();
			rowData[4] = player.getHeight();
			rowData[5] = player.getWeight();
			if(player.getBirthplace() == null) {
				rowData[6] = "";
			} else {
				rowData[6] = player.getBirthplace().toString();
			}
			dtm.addRow(rowData);
		}
		
		playersTbl = new JTable(dtm) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		playersTbl.setAutoCreateRowSorter(true);
		playersTbl.setFont(new Font("Century Gothic", Font.BOLD, 12));
		playersTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playersTbl.setForeground(new Color(0, 0, 139));
		playersTbl.setBackground(Color.white);
		playersTbl.getTableHeader().setFont(new Font("Cetury Gothic", Font.BOLD, 12));
		playersTbl.getTableHeader().setBackground(Color.white);
		
		scroll = new JScrollPane(playersTbl);
		scroll.setBounds(10, 10, 600, 270);
		scroll.setBackground(new Color(173, 216, 230));
		scroll.getViewport().setBackground(Color.white);
		contentPane.add(scroll);
		
		playersTbl.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = table.rowAtPoint(point);
				if(e.getClickCount() == 2 && row != -1) {
					player = players.get(row);
					choosePlayerFormController.edit();
				}
			}
			
		});
	}

}
