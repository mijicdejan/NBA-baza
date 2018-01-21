package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public class MySQLPlayerDAO implements PlayerDAO {

	@Override
	public boolean addPlayerWithPosition(PlayerDTO player) {
		boolean retVal = false;
		
		String query = "CALL addPlayerWithPosition(?, ?, ?, ?, ?, ?, ?)";
		
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
			if(player.getBirthplace() == null) {
				ps.setNull(6, java.sql.Types.INTEGER);
			} else {
				ps.setInt(6, player.getBirthplace().getCityId());
			}
			ps.setString(7, player.getPositions().get(0));
			
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
		
		String query = "CALL addPlayerWithPositions(?, ?, ?, ?, ?, ?, ?, ?)";
		
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
			if(player.getBirthplace() == null) {
				ps.setNull(6, java.sql.Types.INTEGER);
			} else {
				ps.setInt(6, player.getBirthplace().getCityId());
			}
			ps.setString(7, player.getPositions().get(0));
			ps.setString(8, player.getPositions().get(1));
			
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
				DAOFactory factory = new MySQLDAOFactory();
				CityDAO cityDAO = factory.getCityDAO();
				retVal = new PlayerDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5), rs.getInt(6), cityDAO.getCity(rs.getInt(7)));
				retVal.setPositions(getPlayersPositions(retVal));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<PlayerDTO> getAllPlayers() {
		ArrayList<PlayerDTO> retVal = new ArrayList<>();
		
		String query = "SELECT * FROM player";
		
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
				PlayerDTO player = null;
				player = new PlayerDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5), rs.getInt(6), cityDAO.getCity(rs.getInt(7)));
				player.setPositions(getPlayersPositions(player));
				retVal.add(player);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<String> getPlayersPositions(PlayerDTO player) {
		ArrayList<String> retVal = new ArrayList<>();
		
		String query = "SELECT Position FROM player_position INNER JOIN position USING(PositionId) WHERE PlayerId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, player.getId());
			rs = ps.executeQuery();

			while(rs.next()) {
				retVal.add(rs.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public boolean updatePlayerWithPosition(PlayerDTO player) {
		boolean retVal = false;

		String query = "CALL updatePlayerWithPosition(?, ?, ?, ?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			ps.setInt(1, player.getId());
			ps.setString(2, player.getFirstName());
			ps.setString(3, player.getLastName());
			ps.setDate(4, new java.sql.Date(player.getBirthdate().getTime()));
			ps.setInt(5, player.getHeight());
			ps.setInt(6, player.getWeight());
			if(player.getBirthplace() == null) {
				ps.setNull(7, java.sql.Types.INTEGER);
			} else {
				ps.setInt(7, player.getBirthplace().getCityId());
			}
			ps.setString(8, player.getPositions().get(0));

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
	public boolean updatePlayerWithPositions(PlayerDTO player) {
		boolean retVal = false;

		String query = "CALL updatePlayerWithPositions(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			ps.setInt(1, player.getId());
			ps.setString(2, player.getFirstName());
			ps.setString(3, player.getLastName());
			ps.setDate(4, new java.sql.Date(player.getBirthdate().getTime()));
			ps.setInt(5, player.getHeight());
			ps.setInt(6, player.getWeight());
			if(player.getBirthplace() == null) {
				ps.setNull(7, java.sql.Types.INTEGER);
			} else {
				ps.setInt(7, player.getBirthplace().getCityId());
			}
			ps.setString(8, player.getPositions().get(0));
			ps.setString(9, player.getPositions().get(1));

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
	public boolean deletePlayersPosition(PlayerDTO player) {
		boolean retVal = false;

		String query = "DELETE FROM player_position WHERE PlayerId = ?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);

			ps.setInt(1, player.getId());

			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public boolean deletePlayersPositions(PlayerDTO player) {
		boolean retVal = false;

		String query = "DELETE FROM player_position WHERE PlayerId = ?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);

			ps.setInt(1, player.getId());

			retVal = ps.executeUpdate() == 2;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public ArrayList<PlayerDTO> getPlayersInSeason(SeasonDTO season) {
		ArrayList<PlayerDTO> retVal = new ArrayList<>();
		
		String query = "SELECT DISTINCT PlayerId, FirstName, LastName, BirthDate, Height, Weight, CityId FROM player INNER JOIN plays_for USING(PlayerId) WHERE SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, season.getSeasonId());
			rs = ps.executeQuery();

			while(rs.next()) {
				DAOFactory factory = new MySQLDAOFactory();
				CityDAO cityDAO = factory.getCityDAO();
				PlayerDTO player = null;
				player = new PlayerDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5), rs.getInt(6), cityDAO.getCity(rs.getInt(7)));
				player.setPositions(getPlayersPositions(player));
				retVal.add(player);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<PlayerDTO> getRookies(SeasonDTO season) {
		ArrayList<PlayerDTO> retVal = new ArrayList<>();
		
		DAOFactory factory = new MySQLDAOFactory();
		SeasonDAO seasonDAO = factory.getSeasonDAO();
		ArrayList<SeasonDTO> seasons = seasonDAO.getAllSeasons();
		retVal = getPlayersInSeason(season);
		seasons.remove(season);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(season.getStartDate());
		int yearOfSeason = calendar.get(Calendar.YEAR);
		for(int i = 0; i < seasons.size(); i++) {
			calendar.setTime(seasons.get(i).getStartDate());
			int year = calendar.get(Calendar.YEAR);
			if(year > yearOfSeason) {
				seasons.remove(seasons.get(i));
				i--;
			}
		}
		
		for(int i = 0; i < seasons.size(); i++) {
			ArrayList<PlayerDTO> players = getPlayersInSeason(seasons.get(i));
			for(int j = 0; j < players.size(); j++) {
				if(retVal.contains(players.get(j))) {
					retVal.remove(players.get(j));
				}
			}
		}
		
		return retVal;
	}

	@Override
	public ArrayList<PlayerDTO> getPlayersWithTeam(SeasonDTO season) {
		ArrayList<PlayerDTO> retVal = new ArrayList<>();
		
		String query = "SELECT * FROM player INNER JOIN plays_for USING(PlayerId) WHERE SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, season.getSeasonId());
			rs = ps.executeQuery();

			while(rs.next()) {
				DAOFactory factory = new MySQLDAOFactory();
				CityDAO cityDAO = factory.getCityDAO();
				PlayerDTO player = null;
				player = new PlayerDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5), rs.getInt(6), cityDAO.getCity(rs.getInt(7)));
				player.setPositions(getPlayersPositions(player));
				retVal.add(player);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<PlayerDTO> getRoster(FranchiseDTO franchise, SeasonDTO season) {
		ArrayList<PlayerDTO> retVal = new ArrayList<>();
		
		String query = "SELECT * FROM player INNER JOIN plays_for USING(PlayerId) WHERE FranchiseId = ? AND SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, season.getSeasonId());
			rs = ps.executeQuery();

			while(rs.next()) {
				DAOFactory factory = new MySQLDAOFactory();
				CityDAO cityDAO = factory.getCityDAO();
				PlayerDTO player = null;
				player = new PlayerDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5), rs.getInt(6), cityDAO.getCity(rs.getInt(7)));
				player.setPositions(getPlayersPositions(player));
				retVal.add(player);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public boolean isPlayerOnRoster(PlayerDTO player, FranchiseDTO franchise, SeasonDTO season) {
		boolean retVal = false;
		
		String query = "SELECT * FROM plays_for WHERE FranchiseId = ? AND SeasonId = ? AND PlayerId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, season.getSeasonId());
			ps.setInt(3, player.getId());
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

	@Override
	public boolean addPlayerToRoster(PlayerDTO player, FranchiseDTO franchise, SeasonDTO season) {
		boolean retVal = false;
		
		String query = "INSERT INTO plays_for VALUE(?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, player.getId());
			ps.setInt(2, franchise.getFranchiseId());
			ps.setInt(3, season.getSeasonId());			
			
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
	public boolean removePlayerFromRoster(PlayerDTO player, FranchiseDTO franchise, SeasonDTO season) {
		boolean retVal = false;

		String query = "DELETE FROM plays_for WHERE FranchiseId = ? AND SeasonId = ? AND PlayerId = ?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, season.getSeasonId());
			ps.setInt(3, player.getId());

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

}
