package org.unibl.etf.nba.persistence.model.dao;

public class MySQLDAOFactory implements DAOFactory {

	@Override
	public SeasonDAO getSeasonDAO() {
		return new MySQLSeasonDAO();
	}

	@Override
	public PlayerDAO getPlayerDAO() {
		return new MySQLPlayerDAO();
	}

	@Override
	public CityDAO getCityDAO() {
		return new MySQLCityDAO();
	}

}
