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

	@Override
	public CityDTO getCity(int cityId) {
		CityDTO retVal = null;
		
		String query = "SELECT * FROM city WHERE CityId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, cityId);
			rs = ps.executeQuery();

			if(rs.next()) {
				retVal = new CityDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public boolean addCity(CityDTO city) {
		boolean retVal = false;
		
		String query = "INSERT INTO city VALUE (null, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setString(1, city.getName());
			if(city.getState() == null) {
				ps.setNull(2, java.sql.Types.VARCHAR);
			} else {
				ps.setString(2, city.getState());
			}
			ps.setString(3, city.getCountry());
			
			retVal = ps.executeUpdate() == 1;
			
			if(retVal) {
				conn.commit();
			} else {
				throw new SQLException("Rollback needed!");
			}
			
		} catch(SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch(SQLException e) {
				
			}
			DBUtility.close(conn, ps);
		}
		
		return retVal;
	}

}
