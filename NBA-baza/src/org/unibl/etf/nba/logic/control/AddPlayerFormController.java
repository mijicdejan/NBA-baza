package org.unibl.etf.nba.logic.control;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import org.unibl.etf.nba.gui.view.AddCityForm;
import org.unibl.etf.nba.gui.view.AddPlayerForm;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.PlayerDAO;
import org.unibl.etf.nba.persistence.model.dto.CityDTO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;

public class AddPlayerFormController {
	
	private static boolean addCityFormOpened = false;
	
	private AddPlayerForm addPlayerForm;
	
	public AddPlayerFormController(AddPlayerForm addPlayerForm) {
		this.addPlayerForm = addPlayerForm;
	}
	
	public void createAddCityForm() {
		if(addCityFormOpened)
			return;
		
		AddCityForm acf = new AddCityForm(addPlayerForm);
		acf.setVisible(true);
		addCityFormOpened = true;
	}
	
	public static void resetAddCityFormOpened() {
		addCityFormOpened = true;
	}
	
	public void save() {
		String firstName = addPlayerForm.getFirstNameTF().getText();
		String lastName = addPlayerForm.getLastNameTF().getText();
		int height = addPlayerForm.getHeightCB();
		int weight = addPlayerForm.getWeightCB();
		ArrayList<String> positions = new ArrayList<>();
		ArrayList<JCheckBox> temp = addPlayerForm.getCheckedList();
		for(JCheckBox t : temp) {
			positions.add(t.getText());
		}
		Date birthdate = addPlayerForm.getBirthdate();
		CityDTO birthplace = addPlayerForm.getBirthplace();
		if("".equals(firstName) && "".equals(lastName)) {
			JOptionPane.showMessageDialog(addPlayerForm, "You have to enter both first and last name.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else if("".equals(firstName)) {
			JOptionPane.showMessageDialog(addPlayerForm, "You have to enter first name.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else if("".equals(lastName)) {
			JOptionPane.showMessageDialog(addPlayerForm, "You have to enter last name.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else if(positions.size() == 0) {
			JOptionPane.showMessageDialog(addPlayerForm, "You have to check at least one position.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else {
			DAOFactory factory = new MySQLDAOFactory();
			PlayerDAO playerDAO = factory.getPlayerDAO();
			PlayerDTO player = new PlayerDTO(firstName, lastName, birthdate, height, weight, positions, birthplace);
			if(positions.size() == 1) {
				if(playerDAO.addPlayerWithPosition(player)) {
					JOptionPane.showMessageDialog(addPlayerForm, "Player successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
					addPlayerForm.dispose();
					MainFormController.resetAddPlayerFormOpened();
				}
			} else {
				if(playerDAO.addPlayerWithPositions(player)) {
					JOptionPane.showMessageDialog(addPlayerForm, "Player successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
					addPlayerForm.dispose();
					MainFormController.resetAddPlayerFormOpened();
				}
			}
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		Object box = e.getItemSelectable();
		
		if(e.getStateChange() == ItemEvent.DESELECTED) {
			addPlayerForm.getCheckedList().remove(box);
		} else {
			if(addPlayerForm.getCheckedList().size() == 2) {
				JOptionPane.showMessageDialog(addPlayerForm, "You can choose max 2 positions!", "Warning!", JOptionPane.WARNING_MESSAGE);
				((JCheckBox)box).setSelected(false);
			} else {
				addPlayerForm.getCheckedList().add((JCheckBox)box);
			}
		}
	}

}
