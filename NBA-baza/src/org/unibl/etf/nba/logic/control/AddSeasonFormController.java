package org.unibl.etf.nba.logic.control;
import java.util.Date;

import javax.swing.JOptionPane;

import org.unibl.etf.nba.gui.view.AddSeasonForm;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.SeasonDAO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public class AddSeasonFormController {
	
	private AddSeasonForm addSeasonForm;
	
	public AddSeasonFormController(AddSeasonForm addSeasonForm) {
		this.addSeasonForm = addSeasonForm;
	}
	
	public void save() {
		Date startDate = addSeasonForm.getStartDate();
		Date endDate = addSeasonForm.getEndDate();
		Date playoffStartDate = addSeasonForm.getPlayoffStartDate();
		Date playoffEndDate = addSeasonForm.getPlayoffEndDate();
		
		if(endDate.getTime() < startDate.getTime() || playoffStartDate.getTime() < endDate.getTime() || playoffEndDate.getTime() < playoffStartDate.getTime()) {
			JOptionPane.showMessageDialog(addSeasonForm, "Incorrect data input", "Warning!", JOptionPane.WARNING_MESSAGE);
		} else {
			DAOFactory factory = new MySQLDAOFactory();
			SeasonDAO seasonDAO = factory.getSeasonDAO();
			SeasonDTO season = new SeasonDTO(startDate, endDate, playoffStartDate, playoffEndDate, addSeasonForm.getNumberOfGames(), null, null, null, null, null);
			if(seasonDAO.addSeason(season)) {
				JOptionPane.showMessageDialog(addSeasonForm, "Season successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
				addSeasonForm.dispose();
				MainFormController.resetAddSeasonFormOpened();
			}
		}
	}

}
