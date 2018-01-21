package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.nba.persistence.model.dto.PlayerForTradeDTO;

public class MySQLTradeDAO implements TradeDAO {

	@Override
	public ArrayList<PlayerForTradeDTO> getAllTradesWithPlayerAndTeam(int playerId, int franchiseId) {
		ArrayList<PlayerForTradeDTO> retVal = new ArrayList<>();
		
		String query = "SELECT * FROM player_for_trade WHERE PlayerId = ? AND (Team1Id = ? OR Team2Id = ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, playerId);
			ps.setInt(2, franchiseId);
			ps.setInt(3, franchiseId);
			rs = ps.executeQuery();

			while(rs.next()) {
				DAOFactory factory = new MySQLDAOFactory();
				PlayerDAO playerDAO = factory.getPlayerDAO();
				FranchiseDAO franchiseDAO = factory.getFranchiseDAO();
				PlayerForTradeDTO playerForTrade = new PlayerForTradeDTO(playerDAO.getPlayer(playerId), franchiseDAO.getFranchise(rs.getInt(2)), franchiseDAO.getFranchise(rs.getInt(3)), rs.getDate(4), franchiseDAO.getFranchise(rs.getInt(5)));
				retVal.add(playerForTrade);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

}
