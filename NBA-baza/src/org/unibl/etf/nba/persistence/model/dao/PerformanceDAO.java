package org.unibl.etf.nba.persistence.model.dao;

import org.unibl.etf.nba.persistence.model.dto.GameDTO;
import org.unibl.etf.nba.persistence.model.dto.PerformanceDTO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;

public interface PerformanceDAO {
	
	public boolean addPerformance(PerformanceDTO performance);
	public PerformanceDTO getPerformance(PlayerDTO player, GameDTO game);

}
