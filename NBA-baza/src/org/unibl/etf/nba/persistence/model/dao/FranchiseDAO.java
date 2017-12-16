package org.unibl.etf.nba.persistence.model.dao;

import java.util.ArrayList;

import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public interface FranchiseDAO {
	
	public ArrayList<FranchiseDTO> getAllFranchisesInSeason(SeasonDTO season);

}
