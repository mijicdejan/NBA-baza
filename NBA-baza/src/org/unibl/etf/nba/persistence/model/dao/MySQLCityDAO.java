package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.nba.persistence.model.dto.CityDTO;

public class MySQLCityDAO implements CityDAO {

	@Override
	public ArrayList<CityDTO> getAllCities() {
		ArrayList<CityDTO> retVal = new ArrayList<>();
		
		String query = "SELECT * FROM city";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while(rs.next()) {
				CityDTO city = new CityDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				retVal.add(city);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

}
