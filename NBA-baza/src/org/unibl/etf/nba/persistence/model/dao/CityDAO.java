package org.unibl.etf.nba.persistence.model.dao;

import java.util.ArrayList;

import org.unibl.etf.nba.persistence.model.dto.CityDTO;

public interface CityDAO {
	
	public ArrayList<CityDTO> getAllCities();

}
