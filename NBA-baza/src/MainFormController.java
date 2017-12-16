
public class MainFormController {
	
	private static boolean addPlayerFormOpened = false;
	private static boolean choosePlayerFormOpened = false;
	private static boolean addSeasonFormOpened = false;
	private static boolean addSeasonAwardsFormOpened = false;
	private static boolean addArenaFormOpened = false;
	private static boolean addGameFormOpened = false;
	
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
	
	public void createAddGameForm() {
		if(addGameFormOpened)
			return;
		
		AddGameForm agf = new AddGameForm(mainForm.getSelectedDate(), mainForm.getSelectedSeason());
		agf.setVisible(true);
		addGameFormOpened = true;
	}
	
	public static void resetAddGameFormOpened() {
		addGameFormOpened = false;
	}

}
