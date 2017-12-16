package org.unibl.etf.nba.persistence.model.dao;

import java.util.ArrayList;

import org.unibl.etf.nba.persistence.model.dto.ArenaDTO;

public interface ArenaDAO {
	
	public boolean addArena(ArenaDTO arena);
	public ArenaDTO getArena(int arenaId);
	public ArrayList<ArenaDTO> getAllArenas();

}
