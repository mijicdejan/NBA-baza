package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;

public class MySQLSeasonDAO implements SeasonDAO {

	@Override
	public boolean addSeason(Date start, Date end, int n, Date playoffStart) {
		boolean retVal = false;
		
		String query = "INSERT INTO season VALUE (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setDate(1, new java.sql.Date(start.getTime()));
			ps.setDate(2, new java.sql.Date(end.getTime()));
			ps.setNull(3, java.sql.Types.INTEGER);
			ps.setNull(4, java.sql.Types.INTEGER);
			ps.setNull(5, java.sql.Types.INTEGER);
			ps.setNull(6, java.sql.Types.INTEGER);
			ps.setNull(7, java.sql.Types.INTEGER);
			ps.setInt(8, n);
			ps.setDate(9, new java.sql.Date(playoffStart.getTime()));
			ps.setNull(10, java.sql.Types.DATE);
			
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
	
	public boolean addCompleteSeason(Date start, Date end, int mvp, int dp, int smoty, int roty, int mip, int nog, Date playoffStart, Date playoffEnd) {
		boolean retVal = false;
		
		String query = "INSERT INTO season VALUE (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setDate(1, new java.sql.Date(start.getTime()));
			ps.setDate(2, new java.sql.Date(end.getTime()));
			ps.setInt(3, mvp);
			ps.setInt(4, dp);
			ps.setInt(5, smoty);
			ps.setInt(6, roty);
			ps.setInt(7, mip);
			ps.setInt(8, nog);
			ps.setDate(9, new java.sql.Date(playoffStart.getTime()));
			ps.setDate(10, new java.sql.Date(playoffEnd.getTime()));
			
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
	public int getSeasonId(int seasonStart) {
		int retVal = 0;
		
		String query = "SELECT SeasonId FROM season WHERE YEAR(StartDate) = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, seasonStart);
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
	public ArrayList<String> getAllSeasons() {
		ArrayList<String> retVal = new ArrayList<>();
		
		String query = "SELECT StartDate, EndDate FROM season";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			Calendar calendar = new GregorianCalendar();
			

			while(rs.next()) {
				String s = null;
				calendar.setTime(rs.getDate(1));
				s = String.valueOf(calendar.get(Calendar.YEAR));
				s += "/";
				calendar.setTime(rs.getDate(2));
				s += String.valueOf(calendar.get(Calendar.YEAR));
				retVal.add(s);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

}
