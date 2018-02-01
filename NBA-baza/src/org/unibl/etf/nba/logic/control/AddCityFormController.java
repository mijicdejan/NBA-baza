package org.unibl.etf.nba.logic.control;
import javax.swing.JOptionPane;

import org.unibl.etf.nba.gui.view.AddCityForm;
import org.unibl.etf.nba.persistence.model.dao.CityDAO;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dto.CityDTO;

public class AddCityFormController {
	
	private AddCityForm addCityForm;
	
	public AddCityFormController(AddCityForm addCityForm) {
		this.addCityForm = addCityForm;
	}
	
	public void save() {
		String name = addCityForm.getName();
		String state = addCityForm.getStateOfCity();
		String country = addCityForm.getCountry();
		
		if("".equals(name) && "".equals(country)) {
			JOptionPane.showMessageDialog(addCityForm, "You have to enter city name and country name.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else if("".equals(name)) {
			JOptionPane.showMessageDialog(addCityForm, "You have to enter city name.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else if("".equals(country)) {
			JOptionPane.showMessageDialog(addCityForm, "You have to enter country name.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else {
			DAOFactory factory = new MySQLDAOFactory();
			CityDAO cityDAO = factory.getCityDAO();
			if("".equals(state)) {
				CityDTO city = new CityDTO(name, null, country);
				if(cityDAO.addCity(city)) {
					JOptionPane.showMessageDialog(addCityForm, "City successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
					if(addCityForm.getAddPlayerForm() != null) {
						addCityForm.getAddPlayerForm().reloadBirthplaceCB(city.toString());
					} else {
						addCityForm.getAddArenaForm().reloadCityCB(city.toString());
					}
					addCityForm.dispose();
					AddPlayerFormController.resetAddCityFormOpened();
					AddArenaFormController.resetAddCityFormOpened();
				}
			} else {
				CityDTO city = new CityDTO(name, state, country);
				if(cityDAO.addCity(city)) {
					JOptionPane.showMessageDialog(addCityForm, "City successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
					if(addCityForm.getAddPlayerForm() != null) {
						addCityForm.getAddPlayerForm().reloadBirthplaceCB(city.toString());
					} else {
						addCityForm.getAddArenaForm().reloadCityCB(city.toString());
					}
					addCityForm.dispose();
					AddPlayerFormController.resetAddCityFormOpened();
					AddArenaFormController.resetAddCityFormOpened();
				}
			}
		}
	}

}
