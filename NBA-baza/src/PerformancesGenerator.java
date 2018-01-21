import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;

import org.unibl.etf.nba.persistence.model.dao.DAOFactory;
import org.unibl.etf.nba.persistence.model.dao.GameDAO;
import org.unibl.etf.nba.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.nba.persistence.model.dto.FranchiseDTO;
import org.unibl.etf.nba.persistence.model.dto.PerformanceDTO;
import org.unibl.etf.nba.persistence.model.dto.PlayerDTO;
import org.unibl.etf.nba.persistence.model.dto.SeasonDTO;

public class PerformancesGenerator {
	
	public static ArrayList<PerformanceDTO> generatePerformances(FranchiseDTO homeTeam, FranchiseDTO awayTeam, Date gameTime, int homeTeamScore, int awayTeamScore, SeasonDTO season) {
		ArrayList<PerformanceDTO> retVal = new ArrayList<>();
		
		DAOFactory factory = new MySQLDAOFactory();
		GameDAO gameDAO = factory.getGameDAO();
		
		ArrayList<PlayerDTO> homePlayers = gameDAO.getPlayersForGame(homeTeam, season, gameTime);
		ArrayList<PlayerDTO> awayPlayers = gameDAO.getPlayersForGame(awayTeam, season, gameTime);
		ArrayList<PerformanceDTO> homePerformances = new ArrayList<>();
		ArrayList<PerformanceDTO> awayPerformances = new ArrayList<>();

		for(int i = 0; i < homePlayers.size(); i++) {
			PlayerDTO player = homePlayers.get(i);
			PerformanceDTO performance = new PerformanceDTO();
			performance.setPlayerId(player.getId());
			performance.setHomeTeamId(homeTeam.getFranchiseId());
			performance.setAwayTeamId(awayTeam.getFranchiseId());
			performance.setGameTime(gameTime);
			homePerformances.add(performance);
		}
		
		for(int i = 0; i < awayPlayers.size(); i++) {
			PlayerDTO player = awayPlayers.get(i);
			PerformanceDTO performance = new PerformanceDTO();
			performance.setPlayerId(player.getId());
			performance.setHomeTeamId(homeTeam.getFranchiseId());
			performance.setAwayTeamId(awayTeam.getFranchiseId());
			performance.setGameTime(gameTime);
			awayPerformances.add(performance);
		}
		
		ArrayList<PlayerDTO> backCourt = new ArrayList<>();
		ArrayList<PlayerDTO> frontCourt = new ArrayList<>();
		ArrayList<PerformanceDTO> backCourtPerformances = new ArrayList<>();
		ArrayList<PerformanceDTO> frontCourtPerformances = new ArrayList<>();
		
		for(int i = 0; i < homePlayers.size(); i++) {
			PlayerDTO player = homePlayers.get(i);
			if(player.getPositions().contains("PF") || player.getPositions().contains("C")) {
				frontCourt.add(player);
				frontCourtPerformances.add(homePerformances.get(i));
			} else {
				backCourt.add(player);
				backCourtPerformances.add(homePerformances.get(i));
			}
		}
		
		int[] secondsPerBackCourtPlayer = splitSeconds(144 * 60, backCourt.size());
		Collections.shuffle(backCourtPerformances);
		for(int i = 0; i < backCourtPerformances.size(); i++) {
			backCourtPerformances.get(i).setSeconds(secondsPerBackCourtPlayer[i]);
		}
		
		int[] secondsPerFrontCourtPlayer = splitSeconds(96 * 60, frontCourt.size());
		Collections.shuffle(frontCourtPerformances);
		for(int i = 0; i < frontCourtPerformances.size(); i++) {
			frontCourtPerformances.get(i).setSeconds(secondsPerFrontCourtPlayer[i]);
		}
		
		backCourt = new ArrayList<>();
		frontCourt = new ArrayList<>();
		backCourtPerformances = new ArrayList<>();
		frontCourtPerformances = new ArrayList<>();
		
		for(int i = 0; i < awayPlayers.size(); i++) {
			PlayerDTO player = awayPlayers.get(i);
			for(int j = 0; j < player.getPositions().size(); j++) {
			}
			if(player.getPositions().contains("PF") || player.getPositions().contains("C")) {
				frontCourt.add(player);
				frontCourtPerformances.add(awayPerformances.get(i));
			} else {
				backCourt.add(player);
				backCourtPerformances.add(awayPerformances.get(i));
			}
		}
		
		secondsPerBackCourtPlayer = splitSeconds(144 * 60, backCourt.size());
		Collections.shuffle(backCourtPerformances);
		for(int i = 0; i < backCourtPerformances.size(); i++) {
			backCourtPerformances.get(i).setSeconds(secondsPerBackCourtPlayer[i]);
		}
		
		secondsPerFrontCourtPlayer = splitSeconds(96 * 60, frontCourt.size());
		Collections.shuffle(frontCourtPerformances);
		for(int i = 0; i < frontCourtPerformances.size(); i++) {
			frontCourtPerformances.get(i).setSeconds(secondsPerFrontCourtPlayer[i]);
		}
		
		Random rand = new Random();
		
		int pointsFor3 = 1;
		while(pointsFor3 % 3 != 0 || pointsFor3 == 0) {
			pointsFor3 = rand.nextInt(homeTeamScore/2);
		}
		int rest = homeTeamScore - pointsFor3;
		int pointsFor2 = 1;
		int pointsFor1 = 0;
		while(pointsFor2 % 2 != 0 || pointsFor2 == 0) {
			pointsFor2 = rand.nextInt((9 * rest) / 10);
			pointsFor1 = rest - pointsFor2;
			if(pointsFor1 > 30) {
				pointsFor2 = 0;
			}
		}
		int[] pointsFor3PerPlayer = split(pointsFor3 / 3, homePlayers.size());
		int[] pointsFor2PerPlayer = split(pointsFor2 / 2, homePlayers.size());
		int[] pointsFor1PerPlayer = split(pointsFor1, homePlayers.size());
		
		for(int i = 0; i < homePlayers.size(); i++) {
			pointsFor2PerPlayer[i] *= 2;
			pointsFor3PerPlayer[i] *= 3;
		}
		
		int[] shotsFor3PerPlayer = new int[homePlayers.size()];
		for(int i = 0; i < homePlayers.size(); i++) {
			shotsFor3PerPlayer[i] = (pointsFor3PerPlayer[i] / 3) + rand.nextInt(10);
		}
		
		int[] shotsFor2PerPlayer = new int[homePlayers.size()];
		for(int i = 0; i < homePlayers.size(); i++) {
			shotsFor2PerPlayer[i] = (pointsFor2PerPlayer[i] / 3) + rand.nextInt(10);
		}
		
		int[] shotsFor1PerPlayer = new int[homePlayers.size()];
		for(int i = 0; i < homePlayers.size(); i++) {
			shotsFor1PerPlayer[i] = (pointsFor1PerPlayer[i] / 3) + rand.nextInt(5);
		}
		
		Collections.sort(homePerformances, new Comparator<PerformanceDTO>() {
			@Override
			public int compare(PerformanceDTO o1, PerformanceDTO o2) {
				if(o1.getSeconds() < o2.getSeconds()) {
					return 1;
				} else if(o1.getSeconds() > o2.getSeconds()) {
					return -1;
				}
				return 0;
			}
		});
		
		for(int i = 0; i < homePerformances.size(); i++) {
			homePerformances.get(i).setThreepa(shotsFor3PerPlayer[i]);
			homePerformances.get(i).setThreepm(pointsFor3PerPlayer[i] / 3);
			homePerformances.get(i).setFga(shotsFor2PerPlayer[i] + shotsFor3PerPlayer[i]);
			homePerformances.get(i).setFgm(pointsFor2PerPlayer[i] / 2 + pointsFor3PerPlayer[i] / 3);
			homePerformances.get(i).setFta(shotsFor1PerPlayer[i]);
			homePerformances.get(i).setFtm(pointsFor1PerPlayer[i]);
			homePerformances.get(i).setPoints(homePerformances.get(i).getFtm() + homePerformances.get(i).getThreepm() * 3 + (homePerformances.get(i).getFgm() - homePerformances.get(i).getThreepm()) * 2);

		}
		
		int fgaOverall = 0;
		int fgmOverall = 0;
		for(int i = 0; i < homePlayers.size(); i++) {
			fgaOverall += shotsFor2PerPlayer[i];
			fgaOverall += shotsFor3PerPlayer[i];
			fgmOverall += pointsFor2PerPlayer[i] / 2;
			fgmOverall += pointsFor3PerPlayer[i] / 3;
		}
		
		int assists = rand.nextInt(fgmOverall);
		int[] assistsPerPlayer = split(assists, homePlayers.size());
		Collections.shuffle(homePerformances);
		for(int i = 0; i < homePerformances.size(); i++) {
			homePerformances.get(i).setAssists(assistsPerPlayer[i]);
		}
		
		int offensiveRebounds = rand.nextInt(fgaOverall - fgmOverall);
		int[] offensiveReboundsPerPlayer = split(offensiveRebounds, homePlayers.size());
		Collections.shuffle(homePerformances);
		for(int i = 0; i < homePerformances.size(); i++) {
			homePerformances.get(i).setOffensiveRebounds(offensiveReboundsPerPlayer[i]);
		}
		
		int steals = rand.nextInt(16);
		int[] stealsPerPlayer = split(steals, homePlayers.size());
		Collections.shuffle(homePerformances);
		for(int i = 0; i < homePerformances.size(); i++) {
			homePerformances.get(i).setSteals(stealsPerPlayer[i]);
		}
		
		int blocks = rand.nextInt(16);
		int[] blocksPerPlayer = split(blocks, homePlayers.size());
		Collections.shuffle(homePerformances);
		for(int i = 0; i < homePerformances.size(); i++) {
			homePerformances.get(i).setBlocks(blocksPerPlayer[i]);
		}
		
		Collections.shuffle(homePerformances);
		for(int i = 0; i < homePerformances.size(); i++) {
			homePerformances.get(i).setFouls(rand.nextInt(6));
		}
		
		int defensiveRebounds = rand.nextInt(fgaOverall - fgmOverall - offensiveRebounds);
		int[] defensiveReboundsPerPlayer = split(defensiveRebounds, awayPlayers.size());
		Collections.shuffle(awayPerformances);
		for(int i = 0; i < awayPerformances.size(); i++) {
			awayPerformances.get(i).setDefensiveRebounds(defensiveReboundsPerPlayer[i]);
		}
		
		// minute i defanzivni skokovi
		
		// i sve za goste
		
		pointsFor3 = 1;
		while(pointsFor3 % 3 != 0 || pointsFor3 == 0) {
			pointsFor3 = rand.nextInt(awayTeamScore/2);
		}
		rest = awayTeamScore - pointsFor3;
		pointsFor2 = 1;
		pointsFor1 = 0;
		while(pointsFor2 % 2 != 0 || pointsFor2 == 0) {
			pointsFor2 = rand.nextInt((9 * rest) / 10);
			pointsFor1 = rest - pointsFor2;
			if(pointsFor1 > 30) {
				pointsFor2 = 0;
			}
		}
		pointsFor3PerPlayer = split(pointsFor3 / 3, awayPlayers.size());
		pointsFor2PerPlayer = split(pointsFor2 / 2, awayPlayers.size());
		pointsFor1PerPlayer = split(pointsFor1, awayPlayers.size());
		
		for(int i = 0; i < awayPlayers.size(); i++) {
			pointsFor2PerPlayer[i] *= 2;
			pointsFor3PerPlayer[i] *= 3;
		}
		
		shotsFor3PerPlayer = new int[awayPlayers.size()];
		for(int i = 0; i < awayPlayers.size(); i++) {
			shotsFor3PerPlayer[i] = (pointsFor3PerPlayer[i] / 3) + rand.nextInt(10);
		}
		
		shotsFor2PerPlayer = new int[awayPlayers.size()];
		for(int i = 0; i < awayPlayers.size(); i++) {
			shotsFor2PerPlayer[i] = (pointsFor2PerPlayer[i] / 3) + rand.nextInt(10);
		}
		
		shotsFor1PerPlayer = new int[awayPlayers.size()];
		for(int i = 0; i < awayPlayers.size(); i++) {
			shotsFor1PerPlayer[i] = (pointsFor1PerPlayer[i] / 3) + rand.nextInt(5);
		}
		
		Collections.sort(awayPerformances, new Comparator<PerformanceDTO>() {
			@Override
			public int compare(PerformanceDTO o1, PerformanceDTO o2) {
				if(o1.getSeconds() < o2.getSeconds()) {
					return 1;
				} else if(o1.getSeconds() > o2.getSeconds()) {
					return -1;
				}
				return 0;
			}
		});
		
		for(int i = 0; i < awayPerformances.size(); i++) {
			awayPerformances.get(i).setThreepa(shotsFor3PerPlayer[i]);
			awayPerformances.get(i).setThreepm(pointsFor3PerPlayer[i] / 3);
			awayPerformances.get(i).setFga(shotsFor2PerPlayer[i] + shotsFor3PerPlayer[i]);
			awayPerformances.get(i).setFgm(pointsFor2PerPlayer[i] / 2 + pointsFor3PerPlayer[i] / 3);
			awayPerformances.get(i).setFta(shotsFor1PerPlayer[i]);
			awayPerformances.get(i).setFtm(pointsFor1PerPlayer[i]);
			awayPerformances.get(i).setPoints(awayPerformances.get(i).getFtm() + awayPerformances.get(i).getThreepm() * 3 + (awayPerformances.get(i).getFgm() - awayPerformances.get(i).getThreepm()) * 2);

		}
		
		fgaOverall = 0;
		fgmOverall = 0;
		for(int i = 0; i < awayPlayers.size(); i++) {
			fgaOverall += shotsFor2PerPlayer[i];
			fgaOverall += shotsFor3PerPlayer[i];
			fgmOverall += pointsFor2PerPlayer[i] / 2;
			fgmOverall += pointsFor3PerPlayer[i] / 3;
		}
		
		assists = rand.nextInt(fgmOverall);
		assistsPerPlayer = split(assists, awayPlayers.size());
		Collections.shuffle(awayPerformances);
		for(int i = 0; i < awayPerformances.size(); i++) {
			awayPerformances.get(i).setAssists(assistsPerPlayer[i]);
		}
		
		offensiveRebounds = rand.nextInt(fgaOverall - fgmOverall);
		offensiveReboundsPerPlayer = split(offensiveRebounds, awayPlayers.size());
		Collections.shuffle(awayPerformances);
		for(int i = 0; i < awayPerformances.size(); i++) {
			awayPerformances.get(i).setOffensiveRebounds(offensiveReboundsPerPlayer[i]);
		}
		
		steals = rand.nextInt(16);
		stealsPerPlayer = split(steals, awayPlayers.size());
		Collections.shuffle(awayPerformances);
		for(int i = 0; i < awayPerformances.size(); i++) {
			awayPerformances.get(i).setSteals(stealsPerPlayer[i]);
		}
		
		blocks = rand.nextInt(16);
		blocksPerPlayer = split(blocks, awayPlayers.size());
		Collections.shuffle(awayPerformances);
		for(int i = 0; i < awayPerformances.size(); i++) {
			awayPerformances.get(i).setBlocks(blocksPerPlayer[i]);
		}
		
		Collections.shuffle(awayPerformances);
		for(int i = 0; i < awayPerformances.size(); i++) {
			awayPerformances.get(i).setFouls(rand.nextInt(6));
		}
		
		defensiveRebounds = rand.nextInt(fgaOverall - fgmOverall - offensiveRebounds);
		defensiveReboundsPerPlayer = split(defensiveRebounds, homePlayers.size());
		Collections.shuffle(homePerformances);
		for(int i = 0; i < homePerformances.size(); i++) {
			homePerformances.get(i).setDefensiveRebounds(defensiveReboundsPerPlayer[i]);
		}
		
		retVal.addAll(homePerformances);
		retVal.addAll(awayPerformances);
		
		return retVal;
	}
	
	private static int[] split(int value, int size) {
		int[] array = new int[size];
		
		if(value == 0) {
			for(int i = 0; i < size; i++) {
				array[i] = 0;
			}
		} else {
		
			Random rand = new Random();
			int v = value;
			int n = 0;
		
			for(int i = 0; i < size; i++) {
				if(i != size - 1) {
					if(v > value / 2) {
						if(v == 1) {
							n = 1;
						} else {
							n = rand.nextInt(v / 2);
						}
					} else {
						if(v == 0) {
							n = 0;
						} else {
							n = rand.nextInt(v);
						}
					}
					v -= n;
				} else {
					n = v;
				}
				array[i] = n;
			}
		}
		
		return array;
	}
	
	private static int[] splitSeconds(int value, int size) {
		int[] array = new int[size];
		
		if(value == 0) {
			for(int i = 0; i < size; i++) {
				array[i] = 0;
			}
		} else {
		
			Random rand = new Random();
			int v = value;
			int n = 0;
		
			for(int i = 0; i < size; i++) {
				if(i != size - 1) {
					if(v > value / 2) {
						if(v == 1) {
							n = 1;
						} else {
							n = 2880;
							while(n >= 2880) {
								n = rand.nextInt(v / 2);
							}
						}
					} else {
						if(v == 0) {
							n = 0;
						} else {
							n = 2880;
							while(n >= 2880) {
								n = rand.nextInt(v);
							}
						}
					}
					v -= n;
				} else {
					n = v;
				}
				array[i] = n;
			}
		}
		
		return array;
	}

}
