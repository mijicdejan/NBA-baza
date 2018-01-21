package org.unibl.etf.nba.persistence.model.dao;

import java.util.ArrayList;

import org.unibl.etf.nba.persistence.model.dto.PlayerForTradeDTO;

public interface TradeDAO {
	
	public ArrayList<PlayerForTradeDTO> getAllTradesWithPlayerAndTeam(int playerId, int franchiseId);

}
