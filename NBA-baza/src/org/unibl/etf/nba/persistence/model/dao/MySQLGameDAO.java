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
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;
import org.unibl.etf.nba.persistence.model.dto.PlayerForTradeDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public class MySQLGameDAO implements GameDAO {
	
	@Override
	public int getNumberOfGamesForTeamInSeason(FranchiseDTO franchise, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT count(*) FROM game WHERE (HomeTeamId = ? OR AwayTeamId = ?) AND SeasonId = ? AND PlayoffGame = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, franchise.getFranchiseId());
			ps.setInt(3, season.getSeasonId());
			ps.setString(4, String.valueOf("N"));
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
	public int getNumberOfHomeGamesForTeamInSeason(FranchiseDTO franchise, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT count(*) FROM game WHERE HomeTeamId = ? AND SeasonId = ? AND PlayoffGame = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, season.getSeasonId());
			ps.setString(3, String.valueOf("N"));
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
	public int getNumberOfWinsForTeamInSeason(FranchiseDTO franchise, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT count(*) FROM game WHERE ((HomeTeamId = ? AND HomeTeamScore > AwayTeamScore) OR (AwayTeamId = ? AND AwayTeamScore > HomeTeamScore)) AND SeasonId = ? AND PlayoffGame = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, franchise.getFranchiseId());
			ps.setInt(3, season.getSeasonId());
			ps.setString(4, String.valueOf("N"));
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
	public int getNumberOfHomePointsScoredForTeamInSeason(FranchiseDTO franchise, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT HomeTeamScore FROM game WHERE HomeTeamId = ? AND SeasonId = ? AND PlayoffGame = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, season.getSeasonId());
			ps.setString(3, String.valueOf("N"));
			rs = ps.executeQuery();

			while(rs.next()) {
				retVal += rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}
	
	@Override
	public int getNumberOfAwayPointsScoredForTeamInSeason(FranchiseDTO franchise, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT AwayTeamScore FROM game WHERE AwayTeamId = ? AND SeasonId = ? AND PlayoffGame = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, season.getSeasonId());
			ps.setString(3, String.valueOf("N"));
			rs = ps.executeQuery();

			while(rs.next()) {
				retVal += rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public int getNumberOfHomePointsConcededForTeamInSeason(FranchiseDTO franchise, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT AwayTeamScore FROM game WHERE HomeTeamId = ? AND SeasonId = ? AND PlayoffGame = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, season.getSeasonId());
			ps.setString(3, String.valueOf("N"));
			rs = ps.executeQuery();

			while(rs.next()) {
				retVal += rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}
	
	@Override
	public int getNumberOfAwayPointsConcededForTeamInSeason(FranchiseDTO franchise, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT HomeTeamScore FROM game WHERE AwayTeamId = ? AND SeasonId = ? AND PlayoffGame = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, season.getSeasonId());
			ps.setString(3, String.valueOf("N"));
			rs = ps.executeQuery();

			while(rs.next()) {
				retVal += rs.getInt(1);
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
		
		String query = "SELECT count(*) FROM game WHERE HomeTeamId = ? AND AwayTeamId = ? AND SeasonId = ? AND PlayoffGame = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, homeFranchise.getFranchiseId());
			ps.setInt(2, awayFranchise.getFranchiseId());
			ps.setInt(3, season.getSeasonId());
			ps.setString(4, String.valueOf("N"));
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
			ps.setTimestamp(3, t);
			
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			t= new Timestamp(calendar.getTime().getTime());
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

	@Override
	public boolean addGame(GameDTO game) {
		boolean retVal = false;
		
		String query = "INSERT INTO game VALUE (?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, game.getHomeTeam().getFranchiseId());
			ps.setInt(2, game.getAwayTeam().getFranchiseId());
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(game.getGameTime());
			Timestamp t = new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(3, t);
			
			ps.setInt(4, game.getSeason().getSeasonId());
			
			if(game.getHomeTeamScore() == -1 || game.getAwayTeamScore() == -1) {
				ps.setNull(5, java.sql.Types.INTEGER);
				ps.setNull(6, java.sql.Types.INTEGER);
			} else {
				ps.setInt(5, game.getHomeTeamScore());
				ps.setInt(6, game.getAwayTeamScore());
			}
			
			ps.setInt(7, game.getArena().getArenaId());
			
			if(game.isPlayoffGame()) {
				ps.setString(8, "Y");
			} else {
				ps.setString(8, "N");
			}
			
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
	public ArrayList<GameDTO> getAllGamesOnDate(Date date) {
		ArrayList<GameDTO> retVal = new ArrayList<>();
		
		String query = "SELECT * FROM game WHERE GameTime BETWEEN ? AND ?";
		
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
				FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
				ArenaDAO arenaDAO = factory.getArenaDAO();
				SeasonDAO seasonDAO = factory.getSeasonDAO();
				GameDTO game = new GameDTO(rs.getTimestamp(3), franchiseDAO.getFranchise(rs.getInt(1)), franchiseDAO.getFranchise(rs.getInt(2)), seasonDAO.getSeason(rs.getInt(4)), arenaDAO.getArena(rs.getInt(7)), rs.getInt(5), rs.getInt(6), (rs.getString(8) == "N") ? false : true);
				retVal.add(game);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<PlayerDTO> getPlayersForGame(FranchiseDTO franchise, SeasonDTO season, Date gameTime) {
		ArrayList<PlayerDTO> retVal = new ArrayList<>();
		
		String query = "SELECT PlayerId FROM plays_for WHERE FranchiseId = ? AND SeasonId = ?";
		
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
				PlayerDAO playerDAO = factory.getPlayerDAO();
				TradeDAO tradeDAO = factory.getTradeDAO();
				ArrayList<PlayerForTradeDTO> trades = tradeDAO.getAllTradesWithPlayerAndTeam(rs.getInt(1), franchise.getFranchiseId());
				for(int i = 0; i < trades.size(); i++) {
					PlayerForTradeDTO trade = trades.get(i);
					if(trade.getToTeam() == franchise && trade.getTradeDate().before(gameTime)) {
						trades.remove(trade);
						i--;
					} else if(trade.getToTeam() != franchise && trade.getTradeDate().after(gameTime)) {
						trades.remove(trade);
						i--;
					}
				}
				if(trades.isEmpty()) {
					retVal.add(playerDAO.getPlayer(rs.getInt(1)));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public boolean deleteGame(GameDTO game) {
		boolean retVal = false;

		String query = "DELETE FROM game WHERE HomeTeamId = ? AND AwayTeamId = ? AND GameTime = ?";

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

			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public FranchiseDTO getWinnerOfPlayoffSeries(FranchiseDTO team1, FranchiseDTO team2, SeasonDTO season) {
		FranchiseDTO retVal = null;
		
		String query = "SELECT COUNT(*) FROM game WHERE ((HomeTeamId = ? AND AwayTeamId = ? AND HomeTeamScore > AwayTeamScore) OR (HomeTeamId = ? AND AwayTeamId = ? AND HomeTeamScore < AwayTeamScore)) AND PlayoffGame = ? AND SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, team1.getFranchiseId());
			ps.setInt(2, team2.getFranchiseId());
			ps.setInt(3, team2.getFranchiseId());
			ps.setInt(4, team1.getFranchiseId());
			ps.setString(5, String.valueOf('Y'));
			ps.setInt(6, season.getSeasonId());
			rs = ps.executeQuery();

			if(rs.next()) {
				if(rs.getInt(1) == 4) {
					retVal = team1;
				} else if((getNumberOfFinishedGamesInPlayoffSeries(team1, team2, season) - rs.getInt(1)) == 4) {
					retVal = team2;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}
	
	@Override
	public FranchiseDTO getLoserOfPlayoffSeries(FranchiseDTO team1, FranchiseDTO team2, SeasonDTO season) {
		FranchiseDTO retVal = null;
		
		String query = "SELECT COUNT(*) FROM game WHERE ((HomeTeamId = ? AND AwayTeamId = ? AND HomeTeamScore > AwayTeamScore) OR (HomeTeamId = ? AND AwayTeamId = ? AND HomeTeamScore < AwayTeamScore)) AND PlayoffGame = ? AND SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, team1.getFranchiseId());
			ps.setInt(2, team2.getFranchiseId());
			ps.setInt(3, team2.getFranchiseId());
			ps.setInt(4, team1.getFranchiseId());
			ps.setString(5, String.valueOf('Y'));
			ps.setInt(6, season.getSeasonId());
			rs = ps.executeQuery();

			if(rs.next()) {
				if(rs.getInt(1) == 4) {
					retVal = team2;
				} else if((getNumberOfFinishedGamesInPlayoffSeries(team1, team2, season) - rs.getInt(1)) == 4) {
					retVal = team1;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public int getNumberOfWinsForTeamInPlayoff(FranchiseDTO franchise, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT COUNT(*) FROM game WHERE ((HomeTeamId = ? AND HomeTeamScore > AwayTeamScore) OR (AwayTeamId = ? AND HomeTeamScore < AwayTeamScore)) AND PlayoffGame = ? AND SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			ps.setInt(2, franchise.getFranchiseId());
			ps.setString(3, String.valueOf('Y'));
			ps.setInt(4, season.getSeasonId());
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
	public int getNumberOfFinishedGamesInPlayoffSeries(FranchiseDTO team1, FranchiseDTO team2, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT COUNT(*) FROM game WHERE ((HomeTeamId = ? AND AwayTeamId = ? AND HomeTeamScore IS NOT NULL) OR (HomeTeamId = ? AND AwayTeamId = ? AND HomeTeamScore IS NOT NULL)) AND PlayoffGame = ? AND SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, team1.getFranchiseId());
			ps.setInt(2, team2.getFranchiseId());
			ps.setInt(3, team2.getFranchiseId());
			ps.setInt(4, team1.getFranchiseId());
			ps.setString(5, String.valueOf('Y'));
			ps.setInt(6, season.getSeasonId());
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
	public int getNumberOfWinsForTeamInPlayoffSeries(FranchiseDTO team1, FranchiseDTO team2, SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT COUNT(*) FROM game WHERE ((HomeTeamId = ? AND AwayTeamId = ? AND HomeTeamScore > AwayTeamScore) OR (HomeTeamId = ? AND AwayTeamId = ? AND HomeTeamScore < AwayTeamScore)) AND PlayoffGame = ? AND SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, team1.getFranchiseId());
			ps.setInt(2, team2.getFranchiseId());
			ps.setInt(3, team2.getFranchiseId());
			ps.setInt(4, team1.getFranchiseId());
			ps.setString(5, String.valueOf('Y'));
			ps.setInt(6, season.getSeasonId());
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
	public boolean checkForGame(FranchiseDTO homeTeam, FranchiseDTO awayTeam, Date date) {
		boolean retVal = false;
		
		String query = "SELECT * FROM game WHERE (HomeTeamId = ? AND AwayTeamId = ?) AND GameTime BETWEEN ? AND ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, homeTeam.getFranchiseId());
			ps.setInt(2, awayTeam.getFranchiseId());
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			Timestamp t = new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(3, t);
			
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			t= new Timestamp(calendar.getTime().getTime());
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

	@Override
	public int[] getGameResult(FranchiseDTO homeTeam, FranchiseDTO awayTeam, Date date) {
		int[] retVal = new int[2];
		
		String query = "SELECT HomeTeamScore, AwayTeamScore FROM game WHERE (HomeTeamId = ? AND AwayTeamId = ?) AND GameTime BETWEEN ? AND ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, homeTeam.getFranchiseId());
			ps.setInt(2, awayTeam.getFranchiseId());
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			Timestamp t = new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(3, t);
			
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			t= new Timestamp(calendar.getTime().getTime());
			ps.setTimestamp(4, t);
			
			rs = ps.executeQuery();

			if(rs.next()) {
				retVal[0] = rs.getInt(1);
				retVal[1] = rs.getInt(2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public int getNumberOfFinishedGamesInRegularSeason(SeasonDTO season) {
		int retVal = 0;
		
		String query = "SELECT COUNT(*) FROM game WHERE SeasonId = ? AND PlayoffGame = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, season.getSeasonId());
			ps.setString(2, String.valueOf("N"));
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

}
