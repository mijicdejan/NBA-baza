package org.unibl.etf.nba.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.unibl.etf.nba.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public class MySQLFranchiseDAO implements FranchiseDAO {

	@Override
	public ArrayList<FranchiseDTO> getAllFranchisesInSeason(SeasonDTO season) {
		ArrayList<FranchiseDTO> retVal = new ArrayList<>();
		
		String query = "SELECT FranchiseId, FranchiseAbrv, Name, DivisionId, ConferenceId FROM ((franchise INNER JOIN team_name USING(FranchiseId)) INNER JOIN team_name_in_season USING(TeamNameId)) INNER JOIN division USING(DivisionID) WHERE SeasonId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, season.getSeasonId());
			rs = ps.executeQuery();

			while(rs.next()) {
				HashMap<SeasonDTO, String> teamNameInSeason = new HashMap<>();
				teamNameInSeason.put(season, rs.getString(3));
				FranchiseDTO franchise = new FranchiseDTO(rs.getInt(1), rs.getString(2), teamNameInSeason, rs.getInt(4), rs.getInt(5));
				retVal.add(franchise);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

}
