package org.unibl.etf.nba.persistence.model.dto;

import java.util.Date;

public class GameDTO {
	
	private Date gameTime;
	private FranchiseDTO homeTeam;
	private FranchiseDTO awayTeam;
	private SeasonDTO season;
	private ArenaDTO arena;
	private int homeTeamScore;
	private int awayTeamScore;
	private boolean playoffGame;
	
	public GameDTO(Date gameTime, FranchiseDTO homeTeam, FranchiseDTO awayTeam, SeasonDTO season, ArenaDTO arena, int homeTeamScore, int awayTeamScore, boolean playoffGame) {
		super();
		this.gameTime = gameTime;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.season = season;
		this.arena = arena;
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
		this.playoffGame = playoffGame;
	}

	public GameDTO(Date gameTime, FranchiseDTO homeTeam, FranchiseDTO awayTeam, SeasonDTO season, ArenaDTO arena,
			boolean playoffGame) {
		super();
		this.gameTime = gameTime;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.season = season;
		this.arena = arena;
		this.homeTeamScore = -1;
		this.awayTeamScore = -1;
		this.playoffGame = playoffGame;
	}

	public Date getGameTime() {
		return gameTime;
	}

	public void setGameTime(Date gameTime) {
		this.gameTime = gameTime;
	}

	public FranchiseDTO getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(FranchiseDTO homeTeam) {
		this.homeTeam = homeTeam;
	}

	public FranchiseDTO getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(FranchiseDTO awayTeam) {
		this.awayTeam = awayTeam;
	}

	public SeasonDTO getSeason() {
		return season;
	}

	public void setSeason(SeasonDTO season) {
		this.season = season;
	}

	public ArenaDTO getArena() {
		return arena;
	}

	public void setArena(ArenaDTO arena) {
		this.arena = arena;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}

	public boolean isPlayoffGame() {
		return playoffGame;
	}

	public void setPlayoffGame(boolean playoffGame) {
		this.playoffGame = playoffGame;
	}

}
