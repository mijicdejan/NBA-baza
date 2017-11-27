package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;

public class MySQLPlayerDAO implements PlayerDAO {

	@Override
	public boolean addPlayerWithPosition(PlayerDTO player) {
		boolean retVal = false;
		
		String query = "CALL addPlayerWithPosition(?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setString(1, player.getFirstName());
			ps.setString(2, player.getLastName());
			ps.setDate(3, new java.sql.Date(player.getBirthdate().getTime()));
			ps.setInt(4, player.getHeight());
			ps.setInt(5, player.getWeight());
			ps.setString(6, player.getPositions().get(0));
			
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
	
	public boolean addPlayerWithPositions(PlayerDTO player) {
		boolean retVal = false;
		
		String query = "CALL addPlayerWithPositions(?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setString(1, player.getFirstName());
			ps.setString(2, player.getLastName());
			ps.setDate(3, new java.sql.Date(player.getBirthdate().getTime()));
			ps.setInt(4, player.getHeight());
			ps.setInt(5, player.getWeight());
			ps.setString(6, player.getPositions().get(0));
			ps.setString(7, player.getPositions().get(1));
			
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
	public int getPlayerId(String firstName, String lastName) {
		int retVal = 0;
		
		String query = "SELECT PlayerId FROM player WHERE FirstName = ? AND LastName = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			rs = ps.executeQuery();

			if (rs.next()) {
				retVal = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public PlayerDTO getPlayer(int playerId) {
		PlayerDTO retVal = null;
		
		String query = "SELECT * FROM player WHERE PlayerId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, playerId);
			rs = ps.executeQuery();

			if(rs.next()) {
				retVal = new PlayerDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5), rs.getInt(6));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

}
