package org.unibl.etf.nba.persistence.model.dao;

import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;

public interface PlayerDAO {
	
	public boolean addPlayerWithPosition(PlayerDTO player);
	public boolean addPlayerWithPositions(PlayerDTO player);
	public int getPlayerId(String firstName, String lastName);
	public PlayerDTO getPlayer(int playerId);

}
