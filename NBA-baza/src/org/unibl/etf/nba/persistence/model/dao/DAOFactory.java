package org.unibl.etf.nba.persistence.model.dao;

public interface DAOFactory {
	
	public SeasonDAO getSeasonDAO();
	public PlayerDAO getPlayerDAO();
	public CityDAO getCityDAO();

}
