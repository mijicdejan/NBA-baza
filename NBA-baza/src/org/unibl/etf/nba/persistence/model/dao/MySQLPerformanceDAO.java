package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.nba.persistence.model.dto.GameDTO;
import org.unibl.etf.nba.persistence.model.dto.PerformanceDTO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;

public class MySQLPerformanceDAO implements PerformanceDAO {

	@Override
	public boolean addPerformance(PerformanceDTO performance) {
		boolean retVal = false;
		
		String query = "INSERT INTO performance VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, performance.getPlayerId());
			ps.setInt(2, performance.getHomeTeamId());
			ps.setInt(3, performance.getAwayTeamId());
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(performance.getGameTime());
			Timestamp t = new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(4, t);
			
			ps.setInt(5, performance.getPoints());
			ps.setInt(6, performance.getAssists());
			ps.setInt(7, performance.getOffensiveRebounds());
			ps.setInt(8, performance.getDefensiveRebounds());
			ps.setInt(9, performance.getSteals());
			ps.setInt(10, performance.getBlocks());
			ps.setInt(11, performance.getFouls());
			ps.setInt(12, performance.getSeconds());
			
			ps.setInt(13, performance.getFga());
			ps.setInt(14, performance.getFgm());
			ps.setInt(15, performance.getThreepa());
			ps.setInt(16, performance.getThreepm());
			ps.setInt(17, performance.getFta());
			ps.setInt(18, performance.getFtm());
			
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
	public PerformanceDTO getPerformance(PlayerDTO player, GameDTO game) {
		PerformanceDTO retVal = null;
		
		String query = "SELECT * FROM performance WHERE HomeTeamId = ? AND AwayTeamId = ? AND GameTime = ? AND PlayerId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, game.getHomeTeam().getFranchiseId());
			ps.setInt(2, game.getAwayTeam().getFranchiseId());
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(game.getGameTime());
			Timestamp t = new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(3, t);
			
			ps.setInt(4, player.getId());
			
			rs = ps.executeQuery();

			if(rs.next()) {
				retVal = new PerformanceDTO(player.getId(), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getInt(14), rs.getInt(15), rs.getInt(16), rs.getInt(17), rs.getInt(18));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

}
