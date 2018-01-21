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

	@Override
	public FranchiseDTO getFranchise(int franchiseId) {
		FranchiseDTO retVal = null;
		
		String query = "SELECT FranchiseId, FranchiseAbrv, DivisionId, ConferenceId FROM ((franchise INNER JOIN team_name USING(FranchiseId)) INNER JOIN team_name_in_season USING(TeamNameId)) INNER JOIN division USING(DivisionID) WHERE FranchiseId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchiseId);
			rs = ps.executeQuery();

			if(rs.next()) {
				retVal = new FranchiseDTO(rs.getInt(1), rs.getString(2), null, rs.getInt(3), rs.getInt(4));
				retVal.setTeamNames(getAllTeamNamesInFranchiseHistory(retVal));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}
	
	@Override
	public FranchiseDTO getFranchise(String franchiseAbrv) {
		FranchiseDTO retVal = null;
		
		String query = "SELECT FranchiseId, FranchiseAbrv, DivisionId, ConferenceId FROM ((franchise INNER JOIN team_name USING(FranchiseId)) INNER JOIN team_name_in_season USING(TeamNameId)) INNER JOIN division USING(DivisionID) WHERE FranchiseAbrv = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setString(1, franchiseAbrv);
			rs = ps.executeQuery();

			if(rs.next()) {
				retVal = new FranchiseDTO(rs.getInt(1), rs.getString(2), null, rs.getInt(3), rs.getInt(4));
				retVal.setTeamNames(getAllTeamNamesInFranchiseHistory(retVal));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public HashMap<SeasonDTO, String> getAllTeamNamesInFranchiseHistory(FranchiseDTO franchise) {
		HashMap<SeasonDTO, String> retVal = new HashMap<>();
		
		String query = "SELECT SeasonId, Name FROM team_name INNER JOIN team_name_in_season USING(TeamNameId) WHERE FranchiseId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, franchise.getFranchiseId());
			rs = ps.executeQuery();
			
			DAOFactory factory = new MySQLDAOFactory();
			SeasonDAO seasonDAO = factory.getSeasonDAO();

			while(rs.next()) {
				retVal.put(seasonDAO.getSeason(rs.getInt(1)), rs.getString(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<FranchiseDTO> getAllFranchisesFromConference(String conference) {
		ArrayList<FranchiseDTO> retVal = new ArrayList<>();
		
		String query = "SELECT DISTINCT FranchiseId, FranchiseAbrv, Name, DivisionId, ConferenceId FROM ((franchise INNER JOIN team_name USING(FranchiseId)) INNER JOIN team_name_in_season USING(TeamNameId)) INNER JOIN division USING(DivisionID) INNER JOIN conference USING(ConferenceId) WHERE ConferenceName = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setString(1, conference);
			rs = ps.executeQuery();

			while(rs.next()) {
				FranchiseDTO franchise = new FranchiseDTO(rs.getInt(1), rs.getString(2), null, rs.getInt(4), rs.getInt(5));
				franchise.setTeamNames(getAllTeamNamesInFranchiseHistory(franchise));
				retVal.add(franchise);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<FranchiseDTO> getAllFranchisesFromDivision(String division) {
		ArrayList<FranchiseDTO> retVal = new ArrayList<>();
		
		String query = "SELECT DISTINCT FranchiseId, FranchiseAbrv, Name, DivisionId, ConferenceId FROM ((franchise INNER JOIN team_name USING(FranchiseId)) INNER JOIN team_name_in_season USING(TeamNameId)) INNER JOIN division USING(DivisionID) WHERE DivisionName = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setString(1, division);
			rs = ps.executeQuery();

			while(rs.next()) {
				FranchiseDTO franchise = new FranchiseDTO(rs.getInt(1), rs.getString(2), null, rs.getInt(4), rs.getInt(5));
				franchise.setTeamNames(getAllTeamNamesInFranchiseHistory(franchise));
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
