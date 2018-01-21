package org.unibl.etf.nba.persistence.model.dao;

public interface DAOFactory {
	
	public SeasonDAO getSeasonDAO();
	public PlayerDAO getPlayerDAO();
	public CityDAO getCityDAO();
	public ArenaDAO getArenaDAO();
	public FranchiseDAO getFranchiseDAO();
	public GameDAO getGameDAO();
	public RefereeDAO getRefereeDAO();
	public TradeDAO getTradeDAO();
	public PerformanceDAO getPerformanceDAO();

}
