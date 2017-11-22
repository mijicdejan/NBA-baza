package org.unibl.etf.nba.persistence.model.dao;

public class MySQLDAOFactory implements DAOFactory {

	@Override
	public SeasonDAO getSeasonDAO() {
		return new MySQLSeasonDAO();
	}

}
