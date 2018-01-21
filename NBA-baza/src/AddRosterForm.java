import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.FranchiseDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.PlayerDAO;
import org.unibl.etf.nba.persistence.model.dao.SeasonDAO;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

@SuppressWarnings("serial")
public class AddRosterForm extends JFrame {

	private JPanel contentPane;
	
	private JLabel seasonLbl;
	private JComboBox<SeasonDTO> seasonCB;
	private JLabel franchiseLbl;
	private JComboBox<String> franchiseCB;
	
	private JLabel availablePlayersLbl;
	private JLabel chosenPlayersLbl;
	private JList<PlayerDTO> availablePlayersList;
	private JList<PlayerDTO> chosenPlayersList;
	private JScrollPane availableScroll;
	private JScrollPane chosenScroll;
	private JButton addPlayerBtn;
	private JButton removePlayerBtn;
	private JButton saveBtn;
	
	private ArrayList<FranchiseDTO> franchises;
	private AddRosterFormController addRosterFormController;

	/**
	 * Create the frame.
	 */
	public AddRosterForm() {
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainFormController.resetAddRosterFormOpened();
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
		setBounds(100, 50, 530, 580);
		setBackground(new Color(255, 255, 255));
		setTitle("Add roster");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 102, 204));
		setContentPane(contentPane);
		
		addRosterFormController = new AddRosterFormController(this);
		
		initComponents();
		initCBListener();
		initButtonsListeners();
	}
	
	private void initComponents() {
		seasonLbl = new JLabel("Season: ");
		seasonLbl.setBounds(10, 10, 300, 25);
		seasonLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(seasonLbl);
		
		DAOFactory factory = new MySQLDAOFactory();
		SeasonDAO seasonDAO = factory.getSeasonDAO();
		ArrayList<SeasonDTO> seasons = seasonDAO.getAllSeasons();
		
		seasonCB = new JComboBox<>();
		seasonCB.setBounds(10, 35, 300, 30);
		seasonCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(seasonCB);
		
		for(SeasonDTO season : seasons) {
			seasonCB.addItem(season);
		}
		
		franchiseLbl = new JLabel("Franchise: ");
		franchiseLbl.setBounds(10, 75, 300, 25);
		franchiseLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(franchiseLbl);
		
		franchiseCB = new JComboBox<>();
		franchiseCB.setBounds(10, 100, 300, 30);
		franchiseCB.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		contentPane.add(franchiseCB);
		
		FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
		franchises = franchiseDAO.getAllFranchisesInSeason((SeasonDTO) seasonCB.getSelectedItem());
		for(FranchiseDTO franchise : franchises) {
			franchiseCB.addItem(franchise.getTeamNames().get((SeasonDTO) seasonCB.getSelectedItem()));
		}
		
		initPlayersList((SeasonDTO) seasonCB.getSelectedItem());
		
		addPlayerBtn = new JButton(">");
		addPlayerBtn.setBounds(230, 225, 60, 60);
		addPlayerBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		addPlayerBtn.setBackground(Color.WHITE);
		contentPane.add(addPlayerBtn);
		
		removePlayerBtn = new JButton("<");
		removePlayerBtn.setBounds(230, 345, 60, 60);
		removePlayerBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		removePlayerBtn.setBackground(Color.WHITE);
		contentPane.add(removePlayerBtn);
		
		saveBtn = new JButton("Save");
		saveBtn.setBounds(160, 485, 200, 40);
		saveBtn.setFont(new Font("Century Gothic", Font.BOLD, 18));
		saveBtn.setBackground(Color.WHITE);
		contentPane.add(saveBtn);
	}
	
	private void initPlayersList(SeasonDTO seasonDTO) {
		SeasonDTO season = seasonDTO;
		DAOFactory factory = new MySQLDAOFactory();
		PlayerDAO playerDAO = factory.getPlayerDAO();
		ArrayList<PlayerDTO> available = playerDAO.getAllPlayers();
		ArrayList<PlayerDTO> unavailable = playerDAO.getPlayersWithTeam(season);
		available.removeAll(unavailable);
		
		try {
			contentPane.remove(availableScroll);
			contentPane.revalidate();
			contentPane.repaint();
		} catch (Exception e) {
			
		}
		
		availablePlayersLbl = new JLabel("Available players: ");
		availablePlayersLbl.setBounds(10, 140, 200, 25);
		availablePlayersLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(availablePlayersLbl);
		
		availablePlayersList = new JList<>(new PlayerListModel(available));
		availablePlayersList.setFont(new Font("Century Gothic", Font.BOLD, 15));
		availablePlayersList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		availableScroll = new JScrollPane();
		availableScroll.setViewportView(availablePlayersList);
		availableScroll.setBounds(10, 165, 200, 300);
		contentPane.add(availableScroll);
		
		chosenPlayersLbl = new JLabel("Chosen players: ");
		chosenPlayersLbl.setBounds(310, 140, 200, 25);
		chosenPlayersLbl.setFont(new Font("Century Gothic", Font.BOLD, 18));
		contentPane.add(chosenPlayersLbl);
		
		initChosenPlayersList(franchises.get(franchiseCB.getSelectedIndex()), season);
	}
	
	private void initChosenPlayersList(FranchiseDTO franchise, SeasonDTO season) {
		DAOFactory factory = new MySQLDAOFactory();
		PlayerDAO playerDAO = factory.getPlayerDAO();
		ArrayList<PlayerDTO> chosenPlayers = playerDAO.getRoster(franchise, season);
		if(chosenPlayers.size() == 0) {
			removePlayerBtn.setEnabled(false);
		}
		
		try {
			contentPane.remove(chosenScroll);
			contentPane.revalidate();
			contentPane.repaint();
		} catch (Exception e) {
			
		}
		
		chosenPlayersList = new JList<>(new PlayerListModel(chosenPlayers));
		chosenPlayersList.setFont(new Font("Century Gothic", Font.BOLD, 15));
		chosenPlayersList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		chosenScroll = new JScrollPane();
		chosenScroll.setViewportView(chosenPlayersList);
		chosenScroll.setBounds(310, 165, 200, 300);
		contentPane.add(chosenScroll);
	}
	
	private void initCBListener() {
		
		seasonCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initPlayersList((SeasonDTO) seasonCB.getSelectedItem());
			}
		});
		
		franchiseCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initChosenPlayersList(franchises.get(franchiseCB.getSelectedIndex()), (SeasonDTO) seasonCB.getSelectedItem());
			}
		});
		
	}
	
	private void initButtonsListeners() {
		
		addPlayerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!availablePlayersList.isSelectionEmpty()) {
					addRosterFormController.addPlayer(availablePlayersList, chosenPlayersList);
					availablePlayersList.clearSelection();
					if(availablePlayersList.getModel().getSize() == 0) {
						addPlayerBtn.setEnabled(false);
					}
					removePlayerBtn.setEnabled(true);
				}
			}
		});
		
		removePlayerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!chosenPlayersList.isSelectionEmpty()) {
					addRosterFormController.removePlayer(availablePlayersList, chosenPlayersList);
					chosenPlayersList.clearSelection();
					if(chosenPlayersList.getModel().getSize() == 0) {
						removePlayerBtn.setEnabled(false);
					}
					addPlayerBtn.setEnabled(true);
				}
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRosterFormController.save();
			}
		});
		
	}
	
	public SeasonDTO getSelectedSeason() {
		return (SeasonDTO) seasonCB.getSelectedItem();
	}
	
	public FranchiseDTO getSelectedFranchise() {
		return franchises.get(franchiseCB.getSelectedIndex());
	}
	
	public ArrayList<PlayerDTO> getChosenPlayers() {
		return ((PlayerListModel) chosenPlayersList.getModel()).getData();
	}

}
