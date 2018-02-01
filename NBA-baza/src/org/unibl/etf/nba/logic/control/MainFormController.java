package org.unibl.etf.nba.logic.control;
import javax.swing.JOptionPane;

import org.unibl.etf.nba.gui.view.AddArenaForm;
import org.unibl.etf.nba.gui.view.AddGameForm;
import org.unibl.etf.nba.gui.view.AddPlayerForm;
import org.unibl.etf.nba.gui.view.AddRosterForm;
import org.unibl.etf.nba.gui.view.AddSeasonAwardsForm;
import org.unibl.etf.nba.gui.view.AddSeasonForm;
import org.unibl.etf.nba.gui.view.ChoosePlayerForm;
import org.unibl.etf.nba.gui.view.EditGameForm;
import org.unibl.etf.nba.gui.view.GameDetailsForm;
import org.unibl.etf.nba.gui.view.MainForm;
import org.unibl.etf.nba.gui.view.PlayoffForm;
import org.unibl.etf.nba.gui.view.PlayoffPictureForm;
import org.unibl.etf.nba.gui.view.StandingsForm;
import org.unibl.etf.nba.persistence.model.dto.GameDTO;

public class MainFormController {
	
	private static boolean addPlayerFormOpened = false;
	private static boolean choosePlayerFormOpened = false;
	private static boolean addSeasonFormOpened = false;
	private static boolean addSeasonAwardsFormOpened = false;
	private static boolean addArenaFormOpened = false;
	private static boolean addRosterFormOpened = false;
	private static boolean standingsFormOpened = false;
	private static boolean playoffFormOpened = false;
	private static boolean playoffPictureFormOpened = false;
	private static boolean addGameFormOpened = false;
	private static boolean editGameFormOpened = false;
	private static boolean gameDetailsFormOpened = false;
	
	private MainForm mainForm;
	
	public MainFormController(MainForm mainForm) {
		this.mainForm = mainForm;
	}
	
	public static void createAddPlayerForm() {
		if(addPlayerFormOpened)
			return;
		
		AddPlayerForm apf = new AddPlayerForm();
		apf.setVisible(true);
		addPlayerFormOpened = true;
	}
	
	public static void resetAddPlayerFormOpened() {
		addPlayerFormOpened = false;
	}
	
	public static void createChoosePlayerForm() {
		if(choosePlayerFormOpened)
			return;
		
		ChoosePlayerForm cpf = new ChoosePlayerForm();
		cpf.setVisible(true);
		choosePlayerFormOpened = true;
	}
	
	public static void resetChoosePlayerFormOpened() {
		choosePlayerFormOpened = false;
	}
	
	public static void createAddSeasonForm() {
		if(addSeasonFormOpened)
			return;
		
		AddSeasonForm asf = new AddSeasonForm();
		asf.setVisible(true);
		addSeasonFormOpened = true;
	}
	
	public static void resetAddSeasonFormOpened() {
		addSeasonFormOpened = false;
	}
	
	public static void createAddSeasonAwardsForm() {
		if(addSeasonAwardsFormOpened)
			return;
		
		AddSeasonAwardsForm asaf = new AddSeasonAwardsForm();
		asaf.setVisible(true);
		addSeasonAwardsFormOpened = true;
	}
	
	public static void resetAddSeasonAwardsFormOpened() {
		addSeasonAwardsFormOpened = false;
	}
	
	public static void createAddArenaForm() {
		if(addArenaFormOpened)
			return;
		
		AddArenaForm aaf = new AddArenaForm();
		aaf.setVisible(true);
		addArenaFormOpened = true;
	}
	
	public static void resetAddArenaFormOpened() {
		addArenaFormOpened = false;
	}
	
	public static void createAddRosterForm() {
		if(addRosterFormOpened)
			return;
		
		AddRosterForm arf = new AddRosterForm();
		arf.setVisible(true);
		addRosterFormOpened = true;
	}
	
	public static void resetAddRosterFormOpened() {
		addRosterFormOpened = false;
	}
	
	public void createStandingsForm() {
		if(standingsFormOpened)
			return;
		
		StandingsForm sf = new StandingsForm(mainForm);
		sf.setVisible(true);
		standingsFormOpened = true;
	}
	
	public static void resetStandingsFormOpened() {
		standingsFormOpened = false;
	}
	
	public void createPlayoffForm() {
		if(playoffFormOpened)
			return;
		
		PlayoffForm pf = new PlayoffForm(mainForm);
		pf.setVisible(true);
		playoffFormOpened = true;
	}
	
	public static void resetPlayoffFormOpened() {
		playoffFormOpened = false;
	}
	
	public void createPlayoffPictureForm() {
		if(playoffPictureFormOpened)
			return;
		
		PlayoffPictureForm ppf = new PlayoffPictureForm(mainForm.getSelectedSeason());
		ppf.setVisible(true);
		playoffPictureFormOpened = true;
	}
	
	public static void resetPlayoffPictureFormOpened() {
		playoffPictureFormOpened = false;
	}
	
	public void createAddGameForm() {
		if(addGameFormOpened)
			return;
		
		AddGameForm agf = new AddGameForm(mainForm);
		agf.setVisible(true);
		addGameFormOpened = true;
	}
	
	public static void resetAddGameFormOpened() {
		addGameFormOpened = false;
	}
	
	public void createEditGameForm() {
		if(editGameFormOpened)
			return;
		
		EditGameForm egf = new EditGameForm(mainForm);
		egf.setVisible(true);
		editGameFormOpened = true;
	}
	
	public static void resetEditGameFormOpened() {
		editGameFormOpened = false;
	}
	
	public void createGameDetailsForm() {
		if(gameDetailsFormOpened)
			return;
		
		GameDTO game = mainForm.getSelectedGame();
		if(game == null) {
			JOptionPane.showMessageDialog(mainForm, "You have to select a finished game.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} else {
			GameDetailsForm gdf = new GameDetailsForm(mainForm.getSelectedGame());
			gdf.setVisible(true);
			gameDetailsFormOpened = true;
		}
	}
	
	public static void resetGameDetailsFormOpened() {
		gameDetailsFormOpened = false;
	}

}
