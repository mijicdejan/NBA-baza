package org.unibl.etf.nba.logic.control;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.unibl.etf.nba.gui.view.EditGameForm;
import org.unibl.etf.nba.logic.utility.PerformancesGenerator;
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

public class EditGameFormController {
	
	private EditGameForm editGameForm;
	
	public EditGameFormController(EditGameForm editGameForm) {
		this.editGameForm = editGameForm;
	}
	
	public void save() {
		GameDTO game = editGameForm.getGame();
		DAOFactory factory = new MySQLDAOFactory();
		GameDAO gameDAO = factory.getGameDAO();
		RefereeDAO refereeDAO = factory.getRefereeDAO();
		boolean b = false;
		if(refereeDAO.deleteRefereesForGame(game) && gameDAO.deleteGame(game)) {
			b = true;
		}
		if(b) {
			try {
				FranchiseDTO homeTeam = game.getHomeTeam();
				FranchiseDTO awayTeam = game.getAwayTeam();
				Date gameTime = editGameForm.getGameTime();
				ArenaDTO arena = null;
				ArrayList<RefereeDTO> referees = editGameForm.getChosenReferees();
				if(editGameForm.getNeutralArenaBox().isSelected()) {
					arena = editGameForm.getArena();
				} else {
					ArenaDAO arenaDAO = factory.getArenaDAO();
					arena = arenaDAO.getArenaForFranchiseInSeason(homeTeam, game.getSeason());
				}
				if(referees.size() != 3) {
					JOptionPane.showMessageDialog(editGameForm, "You have to choose 3 referees!", "Warning!", JOptionPane.WARNING_MESSAGE);
				} else {
					game.setGameTime(gameTime);
					game.setArena(arena);
					if(editGameForm.getAddScoreBox().isSelected()) {
						int homeTeamScore = editGameForm.getHomeTeamScore();
						int awayTeamScore = editGameForm.getAwayTeamScore();
						if(homeTeamScore == awayTeamScore) {
							throw new NumberFormatException();
						}
						game.setHomeTeamScore(homeTeamScore);
						game.setAwayTeamScore(awayTeamScore);
					}
					PerformanceDAO performanceDAO = factory.getPerformanceDAO();
					ArrayList<PerformanceDTO> performances = new ArrayList<>();
					if(game.getHomeTeamScore() > 0 && game.getAwayTeamScore() > 0) {
						performances = PerformancesGenerator.generatePerformances(homeTeam, awayTeam, gameTime, game.getHomeTeamScore(), game.getAwayTeamScore(), game.getSeason());
					}
					b = gameDAO.addGame(game);
					for(int i = 0; i < referees.size() && b; i++) {
						b = refereeDAO.addRefereeForGame(homeTeam, awayTeam, gameTime, referees.get(i));
					}
					if(game.getHomeTeamScore() > 0 && game.getAwayTeamScore() > 0) {
						for(int i = 0; i < performances.size() && b; i++) {
							b = performanceDAO.addPerformance(performances.get(i));
						}
					}
					if(b) {
						JOptionPane.showMessageDialog(editGameForm, "Game successfully edited.", "Message", JOptionPane.INFORMATION_MESSAGE);
						editGameForm.getMainForm().initTable();
						editGameForm.dispose();
						MainFormController.resetEditGameFormOpened();
					}
				}
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(editGameForm, "Invalid score input.", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
