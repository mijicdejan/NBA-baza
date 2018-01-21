package org.unibl.etf.nba.persistence.model.dao;

import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.GameDTO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public interface GameDAO {
	
	public int getNumberOfGamesForTeamInSeason(FranchiseDTO franchise, SeasonDTO season);
	public int getNumberOfHomeGamesForTeamInSeason(FranchiseDTO franchise, SeasonDTO season);
	public int getNumberOfWinsForTeamInSeason(FranchiseDTO franchise, SeasonDTO season);
	public int getNumberOfHomePointsScoredForTeamInSeason(FranchiseDTO franchise, SeasonDTO season);
	public int getNumberOfAwayPointsScoredForTeamInSeason(FranchiseDTO franchise, SeasonDTO season);
	public int getNumberOfHomePointsConcededForTeamInSeason(FranchiseDTO franchise, SeasonDTO season);
	public int getNumberOfAwayPointsConcededForTeamInSeason(FranchiseDTO franchise, SeasonDTO season);
	public int getNumberOfGamesBetweenTeamsInSeason(FranchiseDTO homeFranchise, FranchiseDTO awayFranchise, SeasonDTO season);
	public boolean doesTeamHaveMatchOnDate(FranchiseDTO franchise, Date date);
	public boolean addGame(GameDTO game);
	public ArrayList<GameDTO> getAllGamesOnDate(Date date);
	public ArrayList<PlayerDTO> getPlayersForGame(FranchiseDTO franchise, SeasonDTO season, Date gameTime);
	public boolean deleteGame(GameDTO game);
	public FranchiseDTO getWinnerOfPlayoffSeries(FranchiseDTO team1, FranchiseDTO team2, SeasonDTO season);
	public FranchiseDTO getLoserOfPlayoffSeries(FranchiseDTO team1, FranchiseDTO team2, SeasonDTO season);
	public int getNumberOfWinsForTeamInPlayoff(FranchiseDTO franchise, SeasonDTO season);
	public int getNumberOfFinishedGamesInPlayoffSeries(FranchiseDTO team1, FranchiseDTO team2, SeasonDTO season);
	public int getNumberOfWinsForTeamInPlayoffSeries(FranchiseDTO team1, FranchiseDTO team2, SeasonDTO season);
	public boolean checkForGame(FranchiseDTO homeTeam, FranchiseDTO awayTeam, Date date);
	public int[] getGameResult(FranchiseDTO homeTeam, FranchiseDTO awayTeam, Date date);
	public int getNumberOfFinishedGamesInRegularSeason(SeasonDTO season);

}
