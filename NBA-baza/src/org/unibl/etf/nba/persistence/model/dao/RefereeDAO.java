package org.unibl.etf.nba.persistence.model.dao;

import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.GameDTO;
import org.unibl.etf.nba.persistence.model.dto.RefereeDTO;

public interface RefereeDAO {
	
	public ArrayList<RefereeDTO> getAllReferees();
	public ArrayList<RefereeDTO> getAllUnavailableReferees(Date date);
	public boolean addRefereeForGame(FranchiseDTO homeTeam, FranchiseDTO awayTeam, Date gameTime, RefereeDTO referee);
	public ArrayList<RefereeDTO> getRefereesForGame(GameDTO game);
	public boolean deleteRefereesForGame(GameDTO game);

}
