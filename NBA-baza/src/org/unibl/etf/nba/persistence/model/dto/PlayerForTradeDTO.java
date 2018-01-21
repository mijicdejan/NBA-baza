package org.unibl.etf.nba.persistence.model.dto;

import java.util.Date;

public class PlayerForTradeDTO {
	
	private PlayerDTO player;
	private FranchiseDTO team1;
	private FranchiseDTO team2;
	private Date tradeDate;
	private FranchiseDTO toTeam;
	
	public PlayerForTradeDTO(PlayerDTO player, FranchiseDTO team1, FranchiseDTO team2, Date tradeDate, FranchiseDTO toTeam) {
		super();
		this.player = player;
		this.team1 = team1;
		this.team2 = team2;
		this.tradeDate = tradeDate;
		this.toTeam = toTeam;
	}

	public PlayerDTO getPlayer() {
		return player;
	}

	public void setPlayer(PlayerDTO player) {
		this.player = player;
	}

	public FranchiseDTO getTeam1() {
		return team1;
	}

	public void setTeam1(FranchiseDTO team1) {
		this.team1 = team1;
	}

	public FranchiseDTO getTeam2() {
		return team2;
	}

	public void setTeam2(FranchiseDTO team2) {
		this.team2 = team2;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public FranchiseDTO getToTeam() {
		return toTeam;
	}

	public void setToTeam(FranchiseDTO toTeam) {
		this.toTeam = toTeam;
	}

}
