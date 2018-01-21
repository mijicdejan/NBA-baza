import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlayoffFormController {
	
	private static boolean addGameFormOpened = false;
	
	private static String round = null;
	
	private PlayoffForm playoffForm;
	
	public PlayoffFormController(PlayoffForm playoffForm) {
		this.playoffForm = playoffForm;
	}
	
	public void addGame1() {
		if(addGameFormOpened)
			return;
		
		AddPlayoffGameForm apgf = new AddPlayoffGameForm(playoffForm.getSeason(), getGameDate(1, playoffForm.getSelectedRound()), playoffForm.getSeededTeam(), playoffForm.getUnseededTeam(), playoffForm);
		apgf.setVisible(true);
		addGameFormOpened = true;
	}
	
	public void addGame2() {
		if(addGameFormOpened)
			return;
		
		AddPlayoffGameForm apgf = new AddPlayoffGameForm(playoffForm.getSeason(), getGameDate(2, playoffForm.getSelectedRound()), playoffForm.getSeededTeam(), playoffForm.getUnseededTeam(), playoffForm);
		apgf.setVisible(true);
		addGameFormOpened = true;
	}
	
	public void addGame3() {
		if(addGameFormOpened)
			return;
		
		AddPlayoffGameForm apgf = new AddPlayoffGameForm(playoffForm.getSeason(), getGameDate(3, playoffForm.getSelectedRound()), playoffForm.getUnseededTeam(), playoffForm.getSeededTeam(), playoffForm);
		apgf.setVisible(true);
		addGameFormOpened = true;
	}
	
	public void addGame4() {
		if(addGameFormOpened)
			return;
		
		round = playoffForm.getSelectedRound();
		AddPlayoffGameForm apgf = new AddPlayoffGameForm(playoffForm.getSeason(), getGameDate(4, playoffForm.getSelectedRound()), playoffForm.getUnseededTeam(), playoffForm.getSeededTeam(), playoffForm);
		apgf.setVisible(true);
		addGameFormOpened = true;
	}
	
	public void addGame5() {
		if(addGameFormOpened)
			return;
		
		round = playoffForm.getSelectedRound();
		AddPlayoffGameForm apgf = new AddPlayoffGameForm(playoffForm.getSeason(), getGameDate(5, playoffForm.getSelectedRound()), playoffForm.getSeededTeam(), playoffForm.getUnseededTeam(), playoffForm);
		apgf.setVisible(true);
		addGameFormOpened = true;
	}
	
	public void addGame6() {
		if(addGameFormOpened)
			return;
		
		round = playoffForm.getSelectedRound();
		AddPlayoffGameForm apgf = new AddPlayoffGameForm(playoffForm.getSeason(), getGameDate(6, playoffForm.getSelectedRound()), playoffForm.getUnseededTeam(), playoffForm.getSeededTeam(), playoffForm);
		apgf.setVisible(true);
		addGameFormOpened = true;
	}
	
	public void addGame7() {
		if(addGameFormOpened)
			return;
		
		round = playoffForm.getSelectedRound();
		AddPlayoffGameForm apgf = new AddPlayoffGameForm(playoffForm.getSeason(), getGameDate(7, playoffForm.getSelectedRound()), playoffForm.getSeededTeam(), playoffForm.getUnseededTeam(), playoffForm);
		apgf.setVisible(true);
		addGameFormOpened = true;
	}
	
	public static void resetAddGameFormOpened() {
		addGameFormOpened = false;
	}
	
	public Date getGameDate(int gameNumber, String round) {
		Date date = playoffForm.getPlayoffStartDate();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		LocalDate localDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
		if("First round".equals(playoffForm.getSelectedRound())) {
			LocalDate temp = localDate.plusDays(2 * (gameNumber - 1));
			date = Date.from(temp.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		} else if("Conference semi-finals".equals(playoffForm.getSelectedRound())) {
			LocalDate temp = localDate.plusDays(14 + 2 * (gameNumber - 1));
			date = Date.from(temp.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		} else if("Conference finals".equals(playoffForm.getSelectedRound())) {
			LocalDate temp = localDate.plusDays(28 + 2 * (gameNumber - 1));
			date = Date.from(temp.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		} else {
			LocalDate temp = localDate.plusDays(42 + 2 * (gameNumber - 1));
			date = Date.from(temp.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		}
		return date;
	}
	
	public PlayoffForm getPlayoffForm() {
		return playoffForm;
	}
	
	public static String getRound() {
		return round;
	}

}
