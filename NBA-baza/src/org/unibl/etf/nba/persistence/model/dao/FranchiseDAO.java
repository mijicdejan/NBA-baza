package org.unibl.etf.nba.persistence.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public interface FranchiseDAO {
	
	public ArrayList<FranchiseDTO> getAllFranchisesInSeason(SeasonDTO season);
	public FranchiseDTO getFranchise(int franchiseId);
	public FranchiseDTO getFranchise(String franchiseAbrv);
	public HashMap<SeasonDTO, String> getAllTeamNamesInFranchiseHistory(FranchiseDTO franchise);
	public ArrayList<FranchiseDTO> getAllFranchisesFromConference(String conference);
	public ArrayList<FranchiseDTO> getAllFranchisesFromDivision(String division);

}
