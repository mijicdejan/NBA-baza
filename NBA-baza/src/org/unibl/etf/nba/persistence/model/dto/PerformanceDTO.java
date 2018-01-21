package org.unibl.etf.nba.persistence.model.dto;

import java.util.Date;

public class PerformanceDTO {
	
	private int playerId;
	private int homeTeamId;
	private int awayTeamId;
	private Date gameTime;
	private int points;
	private int assists;
	private int offensiveRebounds;
	private int defensiveRebounds;
	private int steals;
	private int blocks;
	private int fouls;
	private int seconds;
	private int fga;
	private int fgm;
	private int threepa;
	private int threepm;
	private int fta;
	private int ftm;
	
	public PerformanceDTO() {
		super();
	}

	public PerformanceDTO(int playerId, int homeTeamId, int awayTeamId, Date gameTime, int points, int assists, int offensiveRebounds, int defensiveRebounds, int steals, int blocks, int fouls, int seconds, int fga, int fgm, int threepa, int threepm, int fta, int ftm) {
		super();
		this.playerId = playerId;
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
		this.gameTime = gameTime;
		this.points = points;
		this.assists = assists;
		this.offensiveRebounds = offensiveRebounds;
		this.defensiveRebounds = defensiveRebounds;
		this.steals = steals;
		this.blocks = blocks;
		this.fouls = fouls;
		this.seconds = seconds;
		this.fga = fga;
		this.fgm = fgm;
		this.threepa = threepa;
		this.threepm = threepm;
		this.fta = fta;
		this.ftm = ftm;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(int homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public int getAwayTeamId() {
		return awayTeamId;
	}

	public void setAwayTeamId(int awayTeamId) {
		this.awayTeamId = awayTeamId;
	}

	public Date getGameTime() {
		return gameTime;
	}

	public void setGameTime(Date gameTime) {
		this.gameTime = gameTime;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public int getOffensiveRebounds() {
		return offensiveRebounds;
	}

	public void setOffensiveRebounds(int offensiveRebounds) {
		this.offensiveRebounds = offensiveRebounds;
	}

	public int getDefensiveRebounds() {
		return defensiveRebounds;
	}

	public void setDefensiveRebounds(int defensiveRebounds) {
		this.defensiveRebounds = defensiveRebounds;
	}

	public int getSteals() {
		return steals;
	}

	public void setSteals(int steals) {
		this.steals = steals;
	}

	public int getBlocks() {
		return blocks;
	}

	public void setBlocks(int blocks) {
		this.blocks = blocks;
	}

	public int getFouls() {
		return fouls;
	}

	public void setFouls(int fouls) {
		this.fouls = fouls;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getFga() {
		return fga;
	}

	public void setFga(int fga) {
		this.fga = fga;
	}

	public int getFgm() {
		return fgm;
	}

	public void setFgm(int fgm) {
		this.fgm = fgm;
	}

	public int getThreepa() {
		return threepa;
	}

	public void setThreepa(int threepa) {
		this.threepa = threepa;
	}

	public int getThreepm() {
		return threepm;
	}

	public void setThreepm(int threepm) {
		this.threepm = threepm;
	}

	public int getFta() {
		return fta;
	}

	public void setFta(int fta) {
		this.fta = fta;
	}

	public int getFtm() {
		return ftm;
	}

	public void setFtm(int ftm) {
		this.ftm = ftm;
	}

}
