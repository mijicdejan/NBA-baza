package org.unibl.etf.nba.persistence.model.dto;

import java.util.Date;

public class SeasonDTO {
	
	private int seasonId;
	private Date startDate;
	private Date endDate;
	private Date playoffStartDate;
	private Date playoffEndDate;
	private int numberOfGames;
	private PlayerDTO mvp;
	private PlayerDTO defensivePlayer;
	private PlayerDTO sixthMan;
	private PlayerDTO roty;
	private PlayerDTO mip;
	
	public SeasonDTO(Date startDate, Date endDate, Date playoffStartDate, Date playoffEndDate, int numberOfGames, PlayerDTO mvp, PlayerDTO defensivePlayer, PlayerDTO sixthMan, PlayerDTO roty, PlayerDTO mip) {
		this.seasonId = 0;
		this.startDate = startDate;
		this.endDate = endDate;
		this.playoffStartDate = playoffStartDate;
		this.playoffEndDate = playoffEndDate;
		this.numberOfGames = numberOfGames;
		this.mvp = mvp;
		this.defensivePlayer = defensivePlayer;
		this.sixthMan = sixthMan;
		this.roty = roty;
		this.mip = mip;
	}

	public SeasonDTO(int seasonId, Date startDate, Date endDate, Date playoffStartDate, Date playoffEndDate, int numberOfGames, PlayerDTO mvp, PlayerDTO defensivePlayer, PlayerDTO sixthMan, PlayerDTO roty, PlayerDTO mip) {
		this.seasonId = seasonId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.playoffStartDate = playoffStartDate;
		this.playoffEndDate = playoffEndDate;
		this.numberOfGames = numberOfGames;
		this.mvp = mvp;
		this.defensivePlayer = defensivePlayer;
		this.sixthMan = sixthMan;
		this.roty = roty;
		this.mip = mip;
	}

	public int getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getPlayoffStartDate() {
		return playoffStartDate;
	}

	public void setPlayoffStartDate(Date playoffStartDate) {
		this.playoffStartDate = playoffStartDate;
	}

	public Date getPlayoffEndDate() {
		return playoffEndDate;
	}

	public void setPlayoffEndDate(Date playoffEndDate) {
		this.playoffEndDate = playoffEndDate;
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	public PlayerDTO getMvp() {
		return mvp;
	}

	public void setMvp(PlayerDTO mvp) {
		this.mvp = mvp;
	}

	public PlayerDTO getDefensivePlayer() {
		return defensivePlayer;
	}

	public void setDefensivePlayer(PlayerDTO defensivePlayer) {
		this.defensivePlayer = defensivePlayer;
	}

	public PlayerDTO getSixthMan() {
		return sixthMan;
	}

	public void setSixthMan(PlayerDTO sixthMan) {
		this.sixthMan = sixthMan;
	}

	public PlayerDTO getRoty() {
		return roty;
	}

	public void setRoty(PlayerDTO roty) {
		this.roty = roty;
	}

	public PlayerDTO getMip() {
		return mip;
	}

	public void setMip(PlayerDTO mip) {
		this.mip = mip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + seasonId;
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
		SeasonDTO other = (SeasonDTO) obj;
		if (seasonId != other.seasonId)
			return false;
		return true;
	}

}
