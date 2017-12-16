package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public class MySQLGameDAO implements GameDAO {

	@Override
	public int getNumberOfHomeGamesForTeamInSeason(FranchiseDTO franchise, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT count(*) FROM game WHERE HomeTeamId = ? AND SeasonId = ?";
		
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
	public int getNumberOfGamesBetweenTeamsInSeason(FranchiseDTO homeFranchise, FranchiseDTO awayFranchise, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT count(*) FROM game WHERE HomeTeamId = ? AND AwayTeamId = ? AND SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, homeFranchise.getFranchiseId());
			ps.setInt(2, awayFranchise.getFranchiseId());
			ps.setInt(3, season.getSeasonId());
			rs = ps.executeQuery();

			if(rs.next()) {
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
	public boolean doesTeamHaveMatchOnDate(FranchiseDTO franchise, Date date) {
		boolean retVal = false;
		
		String query = "SELECT * FROM game WHERE (HomeTeamId = ? OR AwayTeamId = ?) AND GameTime BETWEEN ? AND ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, franchise.getFranchiseId());
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			Timestamp t = new Timestamp(calendar.getTime().getTime());
//			ps.setDate(3, new java.sql.Date(calendar.getTime().getTime()));
			ps.setTimestamp(3, t);
			
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			t= new Timestamp(calendar.getTime().getTime());
//			ps.setDate(4, new java.sql.Date(calendar.getTime().getTime()));
			ps.setTimestamp(4, t);
			
			rs = ps.executeQuery();

			if(rs.next()) {
				retVal = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

}
