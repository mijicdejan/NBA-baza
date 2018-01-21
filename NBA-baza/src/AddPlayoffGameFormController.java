import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.unibl.etf.nba.persistence.model.dao.ArenaDAO;
import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.GameDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dao.PerformanceDAO;
import org.unibl.etf.nba.persistence.model.dao.RefereeDAO;
import org.unibl.etf.nba.persistence.model.dao.SeasonDAO;
import org.unibl.etf.nba.persistence.model.dto.ArenaDTO;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.GameDTO;
import org.unibl.etf.nba.persistence.model.dto.PerformanceDTO;
import org.unibl.etf.nba.persistence.model.dto.RefereeDTO;

public class AddPlayoffGameFormController {
	
	private AddPlayoffGameForm addPlayoffGameForm;
	
	public AddPlayoffGameFormController(AddPlayoffGameForm addPlayoffGameForm) {
		this.addPlayoffGameForm = addPlayoffGameForm;
	}
	
	public void save() {
		try {
			FranchiseDTO homeTeam = addPlayoffGameForm.getHomeTeam();
			FranchiseDTO awayTeam = addPlayoffGameForm.getAwayTeam();
			Date gameTime = addPlayoffGameForm.getGameTime();
			ArenaDTO arena = null;
			ArrayList<RefereeDTO> referees = addPlayoffGameForm.getChosenReferees();
			if(addPlayoffGameForm.getNeutralArenaBox().isSelected()) {
				arena = addPlayoffGameForm.getArena();
			} else {
				DAOFactory factory = new MySQLDAOFactory();
				ArenaDAO arenaDAO = factory.getArenaDAO();
				arena = arenaDAO.getArenaForFranchiseInSeason(homeTeam, addPlayoffGameForm.getSeason());
			}
			if(referees.size() != 3) {
				JOptionPane.showMessageDialog(addPlayoffGameForm, "You have to choose 3 referees!", "Warning!", JOptionPane.WARNING_MESSAGE);
			} else {
				GameDTO game = new GameDTO(gameTime, homeTeam, awayTeam, addPlayoffGameForm.getSeason(), arena, true);
				if(addPlayoffGameForm.getAddScoreBox().isSelected()) {
					int homeTeamScore = addPlayoffGameForm.getHomeTeamScore();
					int awayTeamScore = addPlayoffGameForm.getAwayTeamScore();
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
					JOptionPane.showMessageDialog(addPlayoffGameForm, "Game successfully added.", "Message", JOptionPane.INFORMATION_MESSAGE);
					if(gameDAO.getNumberOfWinsForTeamInPlayoffSeries(homeTeam, awayTeam, addPlayoffGameForm.getSeason()) == 4 || gameDAO.getNumberOfWinsForTeamInPlayoffSeries(awayTeam, homeTeam, addPlayoffGameForm.getSeason()) == 4) {
						if("First round".equals((String) PlayoffFormController.getRound())) {
							SeasonDAO seasonDAO = factory.getSeasonDAO();
							seasonDAO.addTeamInPlayoff(gameDAO.getLoserOfPlayoffSeries(homeTeam, awayTeam, addPlayoffGameForm.getSeason()), addPlayoffGameForm.getSeason(), "First Round");
						} else if("Conference semi-finals".equals((String) PlayoffFormController.getRound())) {
							SeasonDAO seasonDAO = factory.getSeasonDAO();
							seasonDAO.addTeamInPlayoff(gameDAO.getLoserOfPlayoffSeries(homeTeam, awayTeam, addPlayoffGameForm.getSeason()), addPlayoffGameForm.getSeason(), "Conference Semifinals");
						} else if("Conference finals".equals((String) PlayoffFormController.getRound())) {
							SeasonDAO seasonDAO = factory.getSeasonDAO();
							seasonDAO.addTeamInPlayoff(gameDAO.getLoserOfPlayoffSeries(homeTeam, awayTeam, addPlayoffGameForm.getSeason()), addPlayoffGameForm.getSeason(), "Conference Finals");
						} else {
							SeasonDAO seasonDAO = factory.getSeasonDAO();
							seasonDAO.addTeamInPlayoff(gameDAO.getLoserOfPlayoffSeries(homeTeam, awayTeam, addPlayoffGameForm.getSeason()), addPlayoffGameForm.getSeason(), "Runner-up");
							seasonDAO.addTeamInPlayoff(gameDAO.getWinnerOfPlayoffSeries(homeTeam, awayTeam, addPlayoffGameForm.getSeason()), addPlayoffGameForm.getSeason(), "NBA Champions");
						}
					}
					addPlayoffGameForm.getPlayoffForm().setTFValues();
					addPlayoffGameForm.getPlayoffForm().setButtonsEnabled();
					addPlayoffGameForm.dispose();
					PlayoffForm.getMainForm().initTable();
					PlayoffFormController.resetAddGameFormOpened();
				}
			}
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(addPlayoffGameForm, "Invalid score input.", "Warning!", JOptionPane.WARNING_MESSAGE);
		}
	}

}
