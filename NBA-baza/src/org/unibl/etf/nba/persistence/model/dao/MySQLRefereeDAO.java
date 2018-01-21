package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.GameDTO;
import org.unibl.etf.nba.persistence.model.dto.RefereeDTO;

public class MySQLRefereeDAO implements RefereeDAO {

	@Override
	public ArrayList<RefereeDTO> getAllReferees() {
		ArrayList<RefereeDTO> retVal = new ArrayList<>();
		
		String query = "SELECT * FROM referee";
		
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
				RefereeDTO referee = new RefereeDTO(rs.getInt(1), rs.getString(2), rs.getString(3), cityDAO.getCity(rs.getInt(4)));
				retVal.add(referee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<RefereeDTO> getAllUnavailableReferees(Date date) {
		ArrayList<RefereeDTO> retVal = new ArrayList<>();
		
		String query = "SELECT RefereeId, FirstName, LastName, CityId FROM referee INNER JOIN referees USING(RefereeId) WHERE GameTime BETWEEN ? AND ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			Timestamp t = new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(1, t);
			
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			t= new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(2, t);
			
			rs = ps.executeQuery();

			while(rs.next()) {
				DAOFactory factory = new MySQLDAOFactory();
				CityDAO cityDAO = factory.getCityDAO();
				RefereeDTO referee = new RefereeDTO(rs.getInt(1), rs.getString(2), rs.getString(3), cityDAO.getCity(rs.getInt(4)));
				retVal.add(referee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public boolean addRefereeForGame(FranchiseDTO homeTeam, FranchiseDTO awayTeam, Date gameTime, RefereeDTO referee) {
		boolean retVal = false;
		
		String query = "INSERT INTO referees VALUE (?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, referee.getRefereeId());
			ps.setInt(2, homeTeam.getFranchiseId());
			ps.setInt(3, awayTeam.getFranchiseId());
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(gameTime);
			Timestamp t = new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(4, t);
			
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
	public ArrayList<RefereeDTO> getRefereesForGame(GameDTO game) {
		ArrayList<RefereeDTO> retVal = new ArrayList<>();
		
		String query = "SELECT RefereeId, FirstName, LastName, CityId FROM referee INNER JOIN referees USING(RefereeId) WHERE GameTime = ? AND HomeTeamId = ? AND AwayTeamId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(game.getGameTime());
			Timestamp t = new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(1, t);
			
			ps.setInt(2, game.getHomeTeam().getFranchiseId());
			ps.setInt(3, game.getAwayTeam().getFranchiseId());
			
			rs = ps.executeQuery();

			while(rs.next()) {
				DAOFactory factory = new MySQLDAOFactory();
				CityDAO cityDAO = factory.getCityDAO();
				RefereeDTO referee = new RefereeDTO(rs.getInt(1), rs.getString(2), rs.getString(3), cityDAO.getCity(rs.getInt(4)));
				retVal.add(referee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public boolean deleteRefereesForGame(GameDTO game) {
		boolean retVal = false;

		String query = "DELETE FROM referees WHERE HomeTeamId = ? AND AwayTeamId = ? AND GameTime = ?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);

			ps.setInt(1, game.getHomeTeam().getFranchiseId());
			ps.setInt(2, game.getAwayTeam().getFranchiseId());
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(game.getGameTime());
			Timestamp t = new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(3, t);

			retVal = ps.executeUpdate() == 3;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

}
