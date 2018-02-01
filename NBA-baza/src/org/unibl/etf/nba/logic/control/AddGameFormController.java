package org.unibl.etf.nba.logic.control;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JList;
import javax.swing.JOptionPane;

import org.unibl.etf.nba.gui.view.AddArenaForm;
import org.unibl.etf.nba.gui.view.AddGameForm;
import org.unibl.etf.nba.logic.utility.PerformancesGenerator;
import org.unibl.etf.nba.logic.utility.RefereeListModel;
import org.unibl.etf.nba.persistence.model.dao.ArenaDAO;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.GameDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.PerformanceDAO;
import org.unibl.etf.nba.persistence.model.dao.RefereeDAO;
import org.unibl.etf.nba.persistence.model.dto.ArenaDTO;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.GameDTO;
import org.unibl.etf.nba.persistence.model.dto.PerformanceDTO;
import org.unibl.etf.nba.persistence.model.dto.RefereeDTO;

public class AddGameFormController {
	
	private static boolean addArenaFormOpened = false;
	
	private AddGameForm addGameForm;
	
	public AddGameFormController(AddGameForm addGameForm) {
		this.addGameForm = addGameForm;
	}
	
	public void scoreItemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.DESELECTED) {
			addGameForm.setEnabledScore(false);
		} else {
			addGameForm.setEnabledScore(true);
		}
	}
	
	public void arenaItemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.DESELECTED) {
			addGameForm.setEnabledArena(false);
		} else {
			addGameForm.setEnabledArena(true);
		}
	}
	
	public static void createAddArenaForm() {
		if(addArenaFormOpened)
			return;
		
		AddArenaForm aaf = new AddArenaForm();
		aaf.setVisible(true);
		addArenaFormOpened = true;
	}
	
	public static void resetAddArenaFormOpened() {
		addArenaFormOpened = false;
	}
	
	public void addReferee(JList<RefereeDTO> availableRefereesList, JList<RefereeDTO> chosenRefereesList) {
		RefereeDTO referee = availableRefereesList.getSelectedValue();
		RefereeListModel availableModel = (RefereeListModel) availableRefereesList.getModel();
		RefereeListModel chosenModel = (RefereeListModel) chosenRefereesList.getModel();
		ArrayList<RefereeDTO> toAddList = chosenModel.getData();
		
		if(!toAddList.contains(referee)) {
			toAddList.add(referee);
		}
		
		chosenModel.setData(toAddList);
		availableModel.getData().removeAll(toAddList);
		chosenModel.fireContentsChanged(this, 0, toAddList.size() - 1);
		availableModel.fireContentsChanged(this, 0, availableModel.getSize() - 1);
	}
	
	public void removeReferee(JList<RefereeDTO> availableRefereesList, JList<RefereeDTO> chosenRefereesList) {
		RefereeDTO referee = chosenRefereesList.getSelectedValue();
		RefereeListModel availableModel = (RefereeListModel) availableRefereesList.getModel();
		RefereeListModel chosenModel = (RefereeListModel) chosenRefereesList.getModel();
		ArrayList<RefereeDTO> toAddList = availableModel.getData();
		
		if(!toAddList.contains(referee)) {
			toAddList.add(referee);
		}
		
		availableModel.setData(toAddList);
		chosenModel.getData().removeAll(toAddList);
		availableModel.fireContentsChanged(this, 0, toAddList.size() - 1);
		chosenModel.fireContentsChanged(this, 0, chosenModel.getSize() - 1);
	}
	
	public void save() {
		try {
			FranchiseDTO homeTeam = addGameForm.getHomeTeam();
			FranchiseDTO awayTeam = addGameForm.getAwayTeam();
			Date gameTime = addGameForm.getGameTime();
			ArenaDTO arena = null;
			ArrayList<RefereeDTO> referees = addGameForm.getChosenReferees();
			if(addGameForm.getNeutralArenaBox().isSelected()) {
				arena = addGameForm.getArena();
			} else {
				DAOFactory factory = new MySQLDAOFactory();
				ArenaDAO arenaDAO = factory.getArenaDAO();
				arena = arenaDAO.getArenaForFranchiseInSeason(homeTeam, addGameForm.getSeason());
			}
			if(referees.size() != 3) {
				JOptionPane.showMessageDialog(addGameForm, "You have to choose 3 referees!", "Warning!", JOptionPane.WARNING_MESSAGE);
			} else {
				GameDTO game = new GameDTO(gameTime, homeTeam, awayTeam, addGameForm.getSeason(), arena, false);
				if(addGameForm.getAddScoreBox().isSelected()) {
					int homeTeamScore = addGameForm.getHomeTeamScore();
					int awayTeamScore = addGameForm.getAwayTeamScore();
					if(homeTeamScore == awayTeamScore) {
						throw new NumberFormatException();
					}
					game.setHomeTeamScore(homeTeamScore);
					game.setAwayTeamScore(awayTeamScore);
				}
				DAOFactory factory = new MySQLDAOFactory();
				GameDAO gameDAO = factory.getGameDAO();
				RefereeDAO refereeDAO = factory.getRefereeDAO();
				PerformanceDAO performanceDAO = factory.getPerformanceDAO();
				ArrayList<PerformanceDTO> performances = new ArrayList<>();
				if(game.getHomeTeamScore() > 0 && game.getAwayTeamScore() > 0) {
					performances = PerformancesGenerator.generatePerformances(homeTeam, awayTeam, gameTime, game.getHomeTeamScore(), game.getAwayTeamScore(), game.getSeason());
				}
				boolean b = gameDAO.addGame(game);
				for(int i = 0; i < referees.size() && b; i++) {
					b = refereeDAO.addRefereeForGame(homeTeam, awayTeam, gameTime, referees.get(i));
				}
				if(game.getHomeTeamScore() > 0 && game.getAwayTeamScore() > 0) {
					for(int i = 0; i < performances.size() && b; i++) {
						b = performanceDAO.addPerformance(performances.get(i));
					}
				}
				if(b) {
					JOptionPane.showMessageDialog(addGameForm, "Game successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
					addGameForm.getMainForm().initTable();
					addGameForm.dispose();
					MainFormController.resetAddGameFormOpened();
				}
			}
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(addGameForm, "Invalid score input.", "Warning!", JOptionPane.WARNING_MESSAGE);
		}
	}

}
