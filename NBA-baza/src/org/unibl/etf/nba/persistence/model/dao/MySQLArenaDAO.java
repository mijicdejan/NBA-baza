package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.nba.persistence.model.dto.ArenaDTO;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public class MySQLArenaDAO implements ArenaDAO {

	@Override
	public boolean addArena(ArenaDTO arena) {
		boolean retVal = false;
		
		String query = "INSERT INTO arena VALUE (null, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setString(1, arena.getName());
			ps.setInt(2, arena.getCapacity());
			ps.setInt(3, arena.getCity().getCityId());
			
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

	@Override
	public ArenaDTO getArena(int arenaId) {
		ArenaDTO retVal = null;
		
		String query = "SELECT * FROM arena WHERE ArenaId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, arenaId);
			rs = ps.executeQuery();

			if(rs.next()) {
				DAOFactory factory = new MySQLDAOFactory();
				CityDAO cityDAO = factory.getCityDAO();
				retVal = new ArenaDTO(arenaId, rs.getString(2), rs.getInt(3), cityDAO.getCity(rs.getInt(4)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<ArenaDTO> getAllArenas() {
		ArrayList<ArenaDTO> retVal = new ArrayList<>();
		
		String query = "SELECT * FROM arena";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while(rs.next()) {
				DAOFactory factory = new MySQLDAOFactory();
				CityDAO cityDAO = factory.getCityDAO();
				ArenaDTO arena = new ArenaDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), cityDAO.getCity(rs.getInt(4)));
				retVal.add(arena);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArenaDTO getArenaForFranchiseInSeason(FranchiseDTO franchise, SeasonDTO season) {
		ArenaDTO retVal = null;
		
		String query = "SELECT ArenaId, Name, Capacity, CityId FROM arena INNER JOIN plays_in USING(ArenaId) WHERE FranchiseId = ? AND SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, season.getSeasonId());
			rs = ps.executeQuery();

			if(rs.next()) {
				DAOFactory factory = new MySQLDAOFactory();
				CityDAO cityDAO = factory.getCityDAO();
				retVal = new ArenaDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), cityDAO.getCity(rs.getInt(4)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

}
