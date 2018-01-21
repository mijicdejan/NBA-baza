package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public class MySQLSeasonDAO implements SeasonDAO {

	@Override
	public boolean addSeason(SeasonDTO season) {
		boolean retVal = false;
		
		String query = "INSERT INTO season VALUE (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setDate(1, new java.sql.Date(season.getStartDate().getTime()));
			ps.setDate(2, new java.sql.Date(season.getEndDate().getTime()));
			DAOFactory factory = new MySQLDAOFactory();
			PlayerDAO playerDAO = factory.getPlayerDAO();
			if(season.getMvp() == null) {
				ps.setNull(3, java.sql.Types.INTEGER);
			} else {
				ps.setInt(3, playerDAO.getPlayerId(season.getMvp().getFirstName(), season.getMvp().getLastName()));
			}
			if(season.getMvp() == null) {
				ps.setNull(4, java.sql.Types.INTEGER);
			} else {
				ps.setInt(4, playerDAO.getPlayerId(season.getDefensivePlayer().getFirstName(), season.getDefensivePlayer().getLastName()));
			}
			if(season.getMvp() == null) {
				ps.setNull(5, java.sql.Types.INTEGER);
			} else {
				ps.setInt(5, playerDAO.getPlayerId(season.getSixthMan().getFirstName(), season.getSixthMan().getLastName()));
			}
			if(season.getMvp() == null) {
				ps.setNull(6, java.sql.Types.INTEGER);
			} else {
				ps.setInt(6, playerDAO.getPlayerId(season.getRoty().getFirstName(), season.getRoty().getLastName()));
			}
			if(season.getMvp() == null) {
				ps.setNull(7, java.sql.Types.INTEGER);
			} else {
				ps.setInt(7, playerDAO.getPlayerId(season.getMip().getFirstName(), season.getMip().getLastName()));
			}
			ps.setInt(8, season.getNumberOfGames());
			ps.setDate(9, new java.sql.Date(season.getPlayoffStartDate().getTime()));
			ps.setDate(10, new java.sql.Date(season.getPlayoffEndDate().getTime()));
			
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
	public ArrayList<SeasonDTO> getAllSeasons() {
		ArrayList<SeasonDTO> retVal = new ArrayList<>();
		
		String query = "SELECT * FROM season";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			DAOFactory factory = new MySQLDAOFactory();
			PlayerDAO player = factory.getPlayerDAO();

			while(rs.next()) {
				SeasonDTO season = new SeasonDTO(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getDate(10), rs.getDate(11), rs.getInt(9), player.getPlayer(rs.getInt(4)), player.getPlayer(rs.getInt(5)), player.getPlayer(rs.getInt(6)), player.getPlayer(rs.getInt(7)), player.getPlayer(rs.getInt(8)));
				retVal.add(season);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public boolean updateSeason(SeasonDTO season) {
		boolean retVal = false;

		String query = "UPDATE season SET StartDate = ?, EndDate = ?, MVP = ?, DefensivePlayer = ?, SixthMan = ?, RookieOfTheYear = ?, MIP = ?, NumberOfGames = ?, PlayoffStartDate = ?, PlayoffEndDate = ? WHERE SeasonId = ?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			ps.setDate(1, new java.sql.Date(season.getStartDate().getTime()));
			ps.setDate(2, new java.sql.Date(season.getEndDate().getTime()));
			ps.setInt(3, season.getMvp().getId());
			ps.setInt(4, season.getDefensivePlayer().getId());
			ps.setInt(5, season.getSixthMan().getId());
			ps.setInt(6, season.getRoty().getId());
			ps.setInt(7, season.getMip().getId());
			ps.setInt(8, season.getNumberOfGames());
			ps.setDate(9, new java.sql.Date(season.getPlayoffStartDate().getTime()));
			ps.setDate(10, new java.sql.Date(season.getPlayoffEndDate().getTime()));
			ps.setInt(11, season.getSeasonId());

			retVal = ps.executeUpdate() == 1;

			if (retVal) {
				conn.commit();
			} else {
				throw new SQLException("Rollback needed!");
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
			DBUtility.close(conn, ps);
		}
		
		return retVal;
	}

	@Override
	public SeasonDTO getSeason(int seasonId) {
		SeasonDTO retVal = null;
		
		String query = "SELECT * FROM season WHERE SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, seasonId);
			rs = ps.executeQuery();

			DAOFactory factory = new MySQLDAOFactory();
			PlayerDAO player = factory.getPlayerDAO();

			if(rs.next()) {
				retVal = new SeasonDTO(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getDate(10), rs.getDate(11), rs.getInt(9), player.getPlayer(rs.getInt(4)), player.getPlayer(rs.getInt(5)), player.getPlayer(rs.getInt(6)), player.getPlayer(rs.getInt(7)), player.getPlayer(rs.getInt(8)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public boolean addTeamInPlayoff(FranchiseDTO team, SeasonDTO season, String result) {
		boolean retVal = false;
		
		String query = "CALL addTeamInPlayoff(?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, team.getFranchiseId());
			ps.setInt(2, season.getSeasonId());
			ps.setString(3, result);
			
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
