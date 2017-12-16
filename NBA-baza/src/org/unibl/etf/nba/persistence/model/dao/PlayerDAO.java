package org.unibl.etf.nba.persistence.model.dao;

import java.util.ArrayList;

import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public interface PlayerDAO {
	
	public boolean addPlayerWithPosition(PlayerDTO player);
	public boolean addPlayerWithPositions(PlayerDTO player);
	public int getPlayerId(String firstName, String lastName);
	public PlayerDTO getPlayer(int playerId);
	public ArrayList<PlayerDTO> getAllPlayers();
	public ArrayList<String> getPlayersPositions(PlayerDTO player);
	public boolean updatePlayerWithPosition(PlayerDTO player);
	public boolean updatePlayerWithPositions(PlayerDTO player);
	public boolean deletePlayersPosition(PlayerDTO player);
	public boolean deletePlayersPositions(PlayerDTO player);
	public ArrayList<PlayerDTO> getPlayersInSeason(SeasonDTO season);
	public ArrayList<PlayerDTO> getRookies(SeasonDTO season);

}
