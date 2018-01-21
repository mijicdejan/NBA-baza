import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JOptionPane;

import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.PlayerDAO;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public class AddRosterFormController {
	
	private AddRosterForm addRosterForm;
	
	public AddRosterFormController(AddRosterForm addRosterForm) {
		this.addRosterForm = addRosterForm;
	}
	
	public void addPlayer(JList<PlayerDTO> availablePlayersList, JList<PlayerDTO> chosenPlayersList) {
		PlayerDTO player = availablePlayersList.getSelectedValue();
		PlayerListModel availableModel = (PlayerListModel) availablePlayersList.getModel();
		PlayerListModel chosenModel = (PlayerListModel) chosenPlayersList.getModel();
		ArrayList<PlayerDTO> toAddList = chosenModel.getData();
		
		if(!toAddList.contains(player)) {
			toAddList.add(player);
		}
		
		chosenModel.setData(toAddList);
		availableModel.getData().removeAll(toAddList);
		chosenModel.fireContentsChanged(this, 0, toAddList.size() - 1);
		availableModel.fireContentsChanged(this, 0, availableModel.getSize() - 1);
	}
	
	public void removePlayer(JList<PlayerDTO> availablePlayersList, JList<PlayerDTO> chosenPlayersList) {
		PlayerDTO player = chosenPlayersList.getSelectedValue();
		PlayerListModel availableModel = (PlayerListModel) availablePlayersList.getModel();
		PlayerListModel chosenModel = (PlayerListModel) chosenPlayersList.getModel();
		ArrayList<PlayerDTO> toAddList = availableModel.getData();
		
		if(!toAddList.contains(player)) {
			toAddList.add(player);
		}
		
		availableModel.setData(toAddList);
		chosenModel.getData().removeAll(toAddList);
		availableModel.fireContentsChanged(this, 0, toAddList.size() - 1);
		chosenModel.fireContentsChanged(this, 0, chosenModel.getSize() - 1);
	}
	
	public void save() {
		SeasonDTO season = addRosterForm.getSelectedSeason();
		FranchiseDTO franchise = addRosterForm.getSelectedFranchise();
		ArrayList<PlayerDTO> chosenPlayers = addRosterForm.getChosenPlayers();
		
		DAOFactory factory = new MySQLDAOFactory();
		PlayerDAO playerDAO = factory.getPlayerDAO();
		ArrayList<PlayerDTO> removedPlayers = playerDAO.getRoster(franchise, season);
		removedPlayers.removeAll(chosenPlayers);
		
		boolean b = true;
		for(int i = 0; i < removedPlayers.size() && b; i++) {
			b = playerDAO.removePlayerFromRoster(removedPlayers.get(i), franchise, season);
		}
		
		if(b) {
			for(int i = 0; i < chosenPlayers.size() && b; i++) {
				if(!playerDAO.isPlayerOnRoster(chosenPlayers.get(i), franchise, season)) {
					b = playerDAO.addPlayerToRoster(chosenPlayers.get(i), franchise, season);
				}
			}
		}
		
		if(b) {
			JOptionPane.showMessageDialog(addRosterForm, "Roster successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
			addRosterForm.dispose();
			MainFormController.resetAddRosterFormOpened();
		}
	}

}
