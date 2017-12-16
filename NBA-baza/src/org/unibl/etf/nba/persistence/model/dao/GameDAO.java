package org.unibl.etf.nba.persistence.model.dao;

import java.util.Date;

import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public interface GameDAO {
	
	public int getNumberOfHomeGamesForTeamInSeason(FranchiseDTO franchise, SeasonDTO season);
	public int getNumberOfGamesBetweenTeamsInSeason(FranchiseDTO homeFranchise, FranchiseDTO awayFranchise, SeasonDTO season);
	public boolean doesTeamHaveMatchOnDate(FranchiseDTO franchise, Date date);

}
