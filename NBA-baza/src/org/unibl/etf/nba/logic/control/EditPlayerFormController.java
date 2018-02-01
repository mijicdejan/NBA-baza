package org.unibl.etf.nba.logic.control;
import javax.swing.JOptionPane;

import org.unibl.etf.nba.gui.view.EditPlayerForm;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.PlayerDAO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;

public class EditPlayerFormController {
	
	private EditPlayerForm editPlayerForm;
	
	public EditPlayerFormController(EditPlayerForm editPlayerForm) {
		this.editPlayerForm = editPlayerForm;
	}
	
	public void save() {
		DAOFactory factory = new MySQLDAOFactory();
		PlayerDAO playerDAO = factory.getPlayerDAO();
		PlayerDTO player = editPlayerForm.getPlayer();
		if(player.getPositions().size() == 1) {
			if(playerDAO.deletePlayersPosition(player) && playerDAO.updatePlayerWithPosition(player)) {
				JOptionPane.showMessageDialog(editPlayerForm, "Player successfully edited.", "Message", JOptionPane.INFORMATION_MESSAGE);
				editPlayerForm.dispose();
				MainFormController.resetChoosePlayerFormOpened();
			}
		} else {
			if(playerDAO.deletePlayersPositions(player) && playerDAO.updatePlayerWithPositions(player)) {
				JOptionPane.showMessageDialog(editPlayerForm, "Player successfully edited.", "Message", JOptionPane.INFORMATION_MESSAGE);
				editPlayerForm.dispose();
				MainFormController.resetChoosePlayerFormOpened();
			}
		}
	}

}
