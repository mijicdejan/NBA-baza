package org.unibl.etf.nba.persistence.model.dto;

import java.util.HashMap;

public class FranchiseDTO {
	
	private int franchiseId;
	private String franchiseAbrv;
	private HashMap<SeasonDTO, String> teamNames;
	private int divisionId;
	private int conferenceId;
	
	public FranchiseDTO() {
		super();
		franchiseId = 0;
		franchiseAbrv = null;
		teamNames = new HashMap<>();
		divisionId = 0;
		conferenceId = 0;
	}

	public FranchiseDTO(int franchiseId, String franchiseAbrv, HashMap<SeasonDTO, String> teamNames, int divisionId, int conferenceId) {
		super();
		this.franchiseId = franchiseId;
		this.franchiseAbrv = franchiseAbrv;
		this.teamNames = teamNames;
		this.divisionId = divisionId;
		this.conferenceId = conferenceId;
	}

	public FranchiseDTO(String franchiseAbrv, HashMap<SeasonDTO, String> teamNames, int divisionId, int conferenceId) {
		super();
		this.franchiseAbrv = franchiseAbrv;
		this.teamNames = teamNames;
		this.divisionId = divisionId;
		this.conferenceId = conferenceId;
	}

	public int getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(int franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getFranchiseAbrv() {
		return franchiseAbrv;
	}

	public void setFranchiseAbrv(String franchiseAbrv) {
		this.franchiseAbrv = franchiseAbrv;
	}

	public HashMap<SeasonDTO, String> getTeamNames() {
		return teamNames;
	}

	public void setTeamNames(HashMap<SeasonDTO, String> teamNames) {
		this.teamNames = teamNames;
	}

	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public int getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(int conferenceId) {
		this.conferenceId = conferenceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + franchiseId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FranchiseDTO other = (FranchiseDTO) obj;
		if (franchiseId != other.franchiseId)
			return false;
		return true;
	}

}
