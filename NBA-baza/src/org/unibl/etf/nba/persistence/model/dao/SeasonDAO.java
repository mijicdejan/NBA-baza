package org.unibl.etf.nba.persistence.model.dao;

import java.util.ArrayList;

import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public interface SeasonDAO {
	
	public boolean addSeason(SeasonDTO season);
	public int getSeasonId(int seasonStart);
	public ArrayList<SeasonDTO> getAllSeasons();
	public boolean updateSeason(SeasonDTO season);

}
