package org.unibl.etf.nba.logic.control;
import javax.swing.JOptionPane;

import org.unibl.etf.nba.gui.view.AddArenaForm;
import org.unibl.etf.nba.gui.view.AddCityForm;
import org.unibl.etf.nba.persistence.model.dao.ArenaDAO;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dto.ArenaDTO;
import org.unibl.etf.nba.persistence.model.dto.CityDTO;

public class AddArenaFormController {
	
	private static boolean addCityFormOpened;
	
	private AddArenaForm addArenaForm;
	
	public AddArenaFormController(AddArenaForm addArenaForm) {
		this.addArenaForm = addArenaForm;
	}
	
	public void createAddCityForm() {
		if(addCityFormOpened)
			return;
		
		AddCityForm acf = new AddCityForm(addArenaForm);
		acf.setVisible(true);
		addCityFormOpened = true;
	}
	
	public static void resetAddCityFormOpened() {
		addCityFormOpened = true;
	}
	
	public void save() {
		String name = addArenaForm.getName();
		int capacity = addArenaForm.getCapacity();
		CityDTO city = addArenaForm.getCity();
		
		if("".equals(name)) {
			JOptionPane.showMessageDialog(addArenaForm, "You have to enter name.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else {
			ArenaDTO arena = new ArenaDTO(name, capacity, city);
			DAOFactory factory = new MySQLDAOFactory();
			ArenaDAO arenaDAO = factory.getArenaDAO();
			if(arenaDAO.addArena(arena)) {
				JOptionPane.showMessageDialog(addArenaForm, "Arena successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
				addArenaForm.dispose();
				MainFormController.resetAddArenaFormOpened();
			}
		}
	}

}
